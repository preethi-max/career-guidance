package com.careerguidance.controller;

import com.careerguidance.dto.ApiResponse;
import com.careerguidance.entity.User;
import com.careerguidance.service.AuthService;
import com.careerguidance.service.ProgressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/student")
public class ProgressController {

    private final ProgressService progressService;
    private final AuthService authService;

    public ProgressController(ProgressService progressService, AuthService authService) {
        this.progressService = progressService;
        this.authService = authService;
    }

    @GetMapping("/progress-overview")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getProgressOverview() {
        User user = authService.getRequiredAuthenticatedUser();
        Map<String, Object> overview = progressService.getStudentProgressOverview(user);
        return ResponseEntity.ok(ApiResponse.ok(overview));
    }
}
