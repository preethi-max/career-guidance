package com.careerguidance.controller;

import com.careerguidance.dto.ApiResponse;
import com.careerguidance.dto.CareerGuideProfileDto;
import com.careerguidance.dto.ConnectionRequestDto;
import com.careerguidance.entity.Role;
import com.careerguidance.entity.User;
import com.careerguidance.service.AuthService;
import com.careerguidance.service.MentorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mentors")
public class MentorController {

    private final MentorService mentorService;
    private final AuthService authService;

    public MentorController(MentorService mentorService, AuthService authService) {
        this.mentorService = mentorService;
        this.authService = authService;
    }

    @GetMapping("/directory")
    public ResponseEntity<ApiResponse<List<CareerGuideProfileDto>>> getDirectory(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String expertise) {
        List<CareerGuideProfileDto> mentors = mentorService.getApprovedMentors(search, expertise);
        return ResponseEntity.ok(ApiResponse.ok(mentors));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CareerGuideProfileDto>> getMentorProfile(@PathVariable Long id) {
        try {
            CareerGuideProfileDto profile = mentorService.getMentorProfile(id);
            return ResponseEntity.ok(ApiResponse.ok(profile));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ex.getMessage()));
        }
    }

    @PostMapping("/{id}/connect")
    public ResponseEntity<ApiResponse<ConnectionRequestDto>> sendRequest(
            @PathVariable Long id,
            @RequestBody(required = false) Map<String, String> payload) {
        User user = authService.getRequiredAuthenticatedUser();
        String message = (payload != null && payload.containsKey("message")) ? payload.get("message") : "I would like guidance on my career roadmap.";
        try {
            ConnectionRequestDto req = mentorService.sendConnectionRequest(user, id, message);
            return ResponseEntity.ok(ApiResponse.ok("Connection request sent successfully!", req));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ex.getMessage()));
        }
    }

    @GetMapping("/my-requests")
    public ResponseEntity<ApiResponse<List<ConnectionRequestDto>>> getMyRequests() {
        User user = authService.getRequiredAuthenticatedUser();
        List<ConnectionRequestDto> requests;
        if (user.getRole() == Role.CAREER_GUIDE) {
            requests = mentorService.getGuideRequests(user);
        } else {
            requests = mentorService.getStudentRequests(user);
        }
        return ResponseEntity.ok(ApiResponse.ok(requests));
    }

    @GetMapping("/requests/{requestId}")
    public ResponseEntity<ApiResponse<ConnectionRequestDto>> getRequestById(@PathVariable Long requestId) {
        User user = authService.getRequiredAuthenticatedUser();
        try {
            ConnectionRequestDto req = mentorService.getRequestById(requestId, user);
            return ResponseEntity.ok(ApiResponse.ok(req));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ex.getMessage()));
        }
    }

    @PutMapping("/requests/{requestId}/status")
    public ResponseEntity<ApiResponse<ConnectionRequestDto>> updateStatus(
            @PathVariable Long requestId,
            @RequestBody Map<String, String> payload) {
        User user = authService.getRequiredAuthenticatedUser();
        String status = payload != null ? payload.get("status") : "";
        try {
            ConnectionRequestDto updated = mentorService.updateRequestStatus(user, requestId, status);
            return ResponseEntity.ok(ApiResponse.ok("Connection request " + status.toLowerCase() + " successfully!", updated));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ex.getMessage()));
        }
    }
}
