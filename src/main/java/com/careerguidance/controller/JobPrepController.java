package com.careerguidance.controller;

import com.careerguidance.dto.ApiResponse;
import com.careerguidance.dto.JobPrepUpdateDto;
import com.careerguidance.entity.JobPrepProgress;
import com.careerguidance.entity.User;
import com.careerguidance.service.AuthService;
import com.careerguidance.service.JobPrepService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/job-prep")
public class JobPrepController {

    private final JobPrepService jobPrepService;
    private final AuthService authService;

    public JobPrepController(JobPrepService jobPrepService, AuthService authService) {
        this.jobPrepService = jobPrepService;
        this.authService = authService;
    }

    @GetMapping("/questions")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getQuestions() {
        return ResponseEntity.ok(ApiResponse.ok(jobPrepService.getInterviewQuestions()));
    }

    @GetMapping("/projects")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getProjects() {
        return ResponseEntity.ok(ApiResponse.ok(jobPrepService.getPortfolioProjectSuggestions()));
    }

    @GetMapping("/resume-checklist")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getResumeChecklist() {
        return ResponseEntity.ok(ApiResponse.ok(jobPrepService.getResumeChecklist()));
    }

    @GetMapping("/internships")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getSampleInternships() {
        return ResponseEntity.ok(ApiResponse.ok(jobPrepService.getSampleInternships()));
    }

    @GetMapping("/progress")
    public ResponseEntity<ApiResponse<Map<String, Boolean>>> getProgress() {
        User user = authService.getRequiredAuthenticatedUser();
        return ResponseEntity.ok(ApiResponse.ok(jobPrepService.getUserProgressMap(user)));
    }

    @PostMapping("/progress")
    public ResponseEntity<ApiResponse<JobPrepProgress>> updateProgress(@Valid @RequestBody JobPrepUpdateDto dto) {
        User user = authService.getRequiredAuthenticatedUser();
        JobPrepProgress progress = jobPrepService.updateProgress(user, dto);
        return ResponseEntity.ok(ApiResponse.ok("Progress updated", progress));
    }
}
