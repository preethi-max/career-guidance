package com.careerguidance.service;

import com.careerguidance.dto.AdminStatsDto;
import com.careerguidance.dto.CareerGuideProfileDto;
import com.careerguidance.dto.UserDto;
import com.careerguidance.entity.*;
import com.careerguidance.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final CareerGuideProfileRepository guideProfileRepository;
    private final CareerRepository careerRepository;
    private final CourseRepository courseRepository;
    private final MentorConnectionRequestRepository requestRepository;

    public AdminService(UserRepository userRepository,
                        CareerGuideProfileRepository guideProfileRepository,
                        CareerRepository careerRepository,
                        CourseRepository courseRepository,
                        MentorConnectionRequestRepository requestRepository) {
        this.userRepository = userRepository;
        this.guideProfileRepository = guideProfileRepository;
        this.careerRepository = careerRepository;
        this.courseRepository = courseRepository;
        this.requestRepository = requestRepository;
    }

    @Transactional(readOnly = true)
    public AdminStatsDto getPlatformStats() {
        AdminStatsDto stats = new AdminStatsDto();
        stats.setTotalUsers(userRepository.count());
        stats.setTotalStudents(userRepository.countByRole(Role.STUDENT));
        stats.setTotalGuides(userRepository.countByRole(Role.CAREER_GUIDE));
        stats.setPendingGuides(guideProfileRepository.countByApprovalStatus("PENDING"));
        stats.setTotalCareers(careerRepository.count());
        stats.setTotalCourses(courseRepository.count());
        stats.setTotalConnections(requestRepository.countByStatus("ACCEPTED"));
        return stats;
    }

    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDto toggleUserActiveStatus(Long userId, boolean active) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        user.setActive(active);
        return UserDto.fromEntity(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public List<CareerGuideProfileDto> getPendingGuideApplications() {
        return guideProfileRepository.findByApprovalStatus("PENDING").stream()
                .map(CareerGuideProfileDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public CareerGuideProfileDto reviewGuideApplication(Long guideProfileId, String status) {
        CareerGuideProfile profile = guideProfileRepository.findById(guideProfileId)
                .orElseThrow(() -> new IllegalArgumentException("Career Guide Profile not found with ID: " + guideProfileId));

        if (!"APPROVED".equalsIgnoreCase(status) && !"REJECTED".equalsIgnoreCase(status)) {
            throw new IllegalArgumentException("Status must be APPROVED or REJECTED");
        }

        profile.setApprovalStatus(status.toUpperCase());
        if ("APPROVED".equalsIgnoreCase(status)) {
            profile.setApprovedAt(LocalDateTime.now());
        }
        profile.setUpdatedAt(LocalDateTime.now());
        return CareerGuideProfileDto.fromEntity(guideProfileRepository.save(profile));
    }
}
