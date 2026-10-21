package com.careerguidance.controller;

import com.careerguidance.dto.ApiResponse;
import com.careerguidance.dto.StudentProfileDto;
import com.careerguidance.entity.User;
import com.careerguidance.service.AuthService;
import com.careerguidance.service.StudentProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
public class StudentProfileController {

    private final StudentProfileService profileService;
    private final AuthService authService;

    public StudentProfileController(StudentProfileService profileService, AuthService authService) {
        this.profileService = profileService;
        this.authService = authService;
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<StudentProfileDto>> getProfile() {
        User user = authService.getRequiredAuthenticatedUser();
        StudentProfileDto profile = profileService.getProfile(user);
        return ResponseEntity.ok(ApiResponse.ok(profile));
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<StudentProfileDto>> updateProfile(@RequestBody StudentProfileDto dto) {
        User user = authService.getRequiredAuthenticatedUser();
        StudentProfileDto updated = profileService.updateProfile(user, dto);
        return ResponseEntity.ok(ApiResponse.ok("Profile updated successfully!", updated));
    }
}
