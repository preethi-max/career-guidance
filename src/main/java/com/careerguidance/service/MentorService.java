package com.careerguidance.service;

import com.careerguidance.dto.CareerGuideProfileDto;
import com.careerguidance.dto.ConnectionRequestDto;
import com.careerguidance.entity.CareerGuideProfile;
import com.careerguidance.entity.MentorConnectionRequest;
import com.careerguidance.entity.Role;
import com.careerguidance.entity.User;
import com.careerguidance.repository.CareerGuideProfileRepository;
import com.careerguidance.repository.ChatMessageRepository;
import com.careerguidance.repository.MentorConnectionRequestRepository;
import com.careerguidance.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MentorService {

    private final CareerGuideProfileRepository guideProfileRepository;
    private final MentorConnectionRequestRepository requestRepository;
    private final UserRepository userRepository;
    private final ChatMessageRepository messageRepository;

    public MentorService(CareerGuideProfileRepository guideProfileRepository,
                         MentorConnectionRequestRepository requestRepository,
                         UserRepository userRepository,
                         ChatMessageRepository messageRepository) {
        this.guideProfileRepository = guideProfileRepository;
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    @Transactional(readOnly = true)
    public List<CareerGuideProfileDto> getApprovedMentors(String search, String expertise) {
        List<CareerGuideProfile> approvedGuides = guideProfileRepository.findByApprovalStatus("APPROVED");

        return approvedGuides.stream()
                .filter(g -> g.getUser() != null && g.getUser().isActive())
                .filter(g -> {
                    if (search == null || search.isBlank()) return true;
                    String s = search.toLowerCase().trim();
                    return (g.getUser().getFullName() != null && g.getUser().getFullName().toLowerCase().contains(s)) ||
                           (g.getOrganization() != null && g.getOrganization().toLowerCase().contains(s)) ||
                           (g.getCurrentDesignation() != null && g.getCurrentDesignation().toLowerCase().contains(s)) ||
                           (g.getBio() != null && g.getBio().toLowerCase().contains(s));
                })
                .filter(g -> {
                    if (expertise == null || expertise.isBlank() || "all".equalsIgnoreCase(expertise)) return true;
                    String exp = expertise.toLowerCase().trim();
                    return g.getExpertiseAreas() != null && g.getExpertiseAreas().toLowerCase().contains(exp);
                })
                .map(CareerGuideProfileDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CareerGuideProfileDto getMentorProfile(Long guideUserId) {
        CareerGuideProfile profile = guideProfileRepository.findByUserId(guideUserId)
                .orElseThrow(() -> new IllegalArgumentException("Career Guide not found with ID: " + guideUserId));
        return CareerGuideProfileDto.fromEntity(profile);
    }

    @Transactional
    public ConnectionRequestDto sendConnectionRequest(User student, Long guideUserId, String message) {
        if (student.getRole() != Role.STUDENT) {
            throw new IllegalArgumentException("Only students can send connection requests to mentors.");
        }

        User guideUser = userRepository.findById(guideUserId)
                .orElseThrow(() -> new IllegalArgumentException("Career Guide user not found"));

        if (guideUser.getRole() != Role.CAREER_GUIDE) {
            throw new IllegalArgumentException("Requested user is not a Career Guide.");
        }

        // Check if existing request
        Optional<MentorConnectionRequest> existing = requestRepository.findByStudentIdAndGuideId(student.getId(), guideUserId);
        if (existing.isPresent()) {
            MentorConnectionRequest req = existing.get();
            if ("PENDING".equals(req.getStatus())) {
                throw new IllegalStateException("You already have a pending connection request with this mentor.");
            } else if ("ACCEPTED".equals(req.getStatus())) {
                throw new IllegalStateException("You are already connected with this mentor.");
            } else {
                // If previously rejected, allow re-requesting
                req.setStatus("PENDING");
                req.setInitialMessage(message);
                req.setUpdatedAt(LocalDateTime.now());
                return ConnectionRequestDto.fromEntity(requestRepository.save(req));
            }
        }

        MentorConnectionRequest newReq = new MentorConnectionRequest(student, guideUser, message);
        MentorConnectionRequest saved = requestRepository.save(newReq);
        return ConnectionRequestDto.fromEntity(saved);
    }

    @Transactional(readOnly = true)
    public List<ConnectionRequestDto> getStudentRequests(User student) {
        List<MentorConnectionRequest> list = requestRepository.findByStudentOrderByCreatedAtDesc(student);
        return enrichRequests(list);
    }

    @Transactional(readOnly = true)
    public List<ConnectionRequestDto> getGuideRequests(User guide) {
        List<MentorConnectionRequest> list = requestRepository.findByGuideOrderByCreatedAtDesc(guide);
        return enrichRequests(list);
    }

    @Transactional
    public ConnectionRequestDto updateRequestStatus(User guide, Long requestId, String status) {
        MentorConnectionRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        if (!request.getGuide().getId().equals(guide.getId())) {
            throw new IllegalStateException("You are not authorized to respond to this request.");
        }

        if (!"ACCEPTED".equalsIgnoreCase(status) && !"REJECTED".equalsIgnoreCase(status)) {
            throw new IllegalArgumentException("Status must be ACCEPTED or REJECTED.");
        }

        request.setStatus(status.toUpperCase());
        request.setUpdatedAt(LocalDateTime.now());
        MentorConnectionRequest saved = requestRepository.save(request);
        return ConnectionRequestDto.fromEntity(saved);
    }

    @Transactional(readOnly = true)
    public Optional<ConnectionRequestDto> getRequestBetween(Long studentId, Long guideId) {
        return requestRepository.findByStudentIdAndGuideId(studentId, guideId)
                .map(ConnectionRequestDto::fromEntity);
    }

    @Transactional(readOnly = true)
    public ConnectionRequestDto getRequestById(Long requestId, User currentUser) {
        MentorConnectionRequest req = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Connection request not found"));

        // Verify user is either student or guide or admin
        boolean isParticipant = req.getStudent().getId().equals(currentUser.getId()) ||
                                req.getGuide().getId().equals(currentUser.getId()) ||
                                currentUser.getRole() == Role.ADMIN;
        if (!isParticipant) {
            throw new IllegalStateException("Unauthorized access to this connection request.");
        }

        List<MentorConnectionRequest> singleton = List.of(req);
        return enrichRequests(singleton).get(0);
    }

    private List<ConnectionRequestDto> enrichRequests(List<MentorConnectionRequest> requests) {
        List<ConnectionRequestDto> dtos = new ArrayList<>();
        for (MentorConnectionRequest req : requests) {
            ConnectionRequestDto dto = ConnectionRequestDto.fromEntity(req);
            if (req.getGuide() != null) {
                guideProfileRepository.findByUser(req.getGuide()).ifPresent(gp -> {
                    dto.setGuideDesignation(gp.getCurrentDesignation());
                    dto.setGuideOrganization(gp.getOrganization());
                });
            }
            dtos.add(dto);
        }
        return dtos;
    }
}
