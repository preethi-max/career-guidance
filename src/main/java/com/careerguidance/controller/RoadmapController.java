package com.careerguidance.controller;

import com.careerguidance.dto.ApiResponse;
import com.careerguidance.dto.RoadmapDetailDto;
import com.careerguidance.entity.User;
import com.careerguidance.service.AuthService;
import com.careerguidance.service.RoadmapService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/roadmaps")
public class RoadmapController {

    private final RoadmapService roadmapService;
    private final AuthService authService;

    public RoadmapController(RoadmapService roadmapService, AuthService authService) {
        this.roadmapService = roadmapService;
        this.authService = authService;
    }

    @GetMapping("/career/{careerId}")
    public ResponseEntity<ApiResponse<RoadmapDetailDto>> getRoadmapByCareer(@PathVariable Long careerId) {
        Optional<User> currentUser = authService.getCurrentAuthenticatedUser();
        try {
            RoadmapDetailDto dto = roadmapService.getRoadmapByCareerId(careerId, currentUser.orElse(null));
            return ResponseEntity.ok(ApiResponse.ok(dto));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ex.getMessage()));
        }
    }

    @GetMapping("/{roadmapId}")
    public ResponseEntity<ApiResponse<RoadmapDetailDto>> getRoadmapById(@PathVariable Long roadmapId) {
        Optional<User> currentUser = authService.getCurrentAuthenticatedUser();
        try {
            RoadmapDetailDto dto = roadmapService.getRoadmapById(roadmapId, currentUser.orElse(null));
            return ResponseEntity.ok(ApiResponse.ok(dto));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ex.getMessage()));
        }
    }

    @PostMapping("/steps/{stepId}/toggle")
    public ResponseEntity<ApiResponse<RoadmapDetailDto>> toggleStep(
            @PathVariable Long stepId,
            @RequestBody Map<String, Boolean> payload) {
        User user = authService.getRequiredAuthenticatedUser();
        boolean completed = payload.getOrDefault("completed", false);
        try {
            RoadmapDetailDto updated = roadmapService.toggleStepCompletion(stepId, completed, user);
            return ResponseEntity.ok(ApiResponse.ok("Step status updated", updated));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ex.getMessage()));
        }
    }

    @GetMapping("/my-progress")
    public ResponseEntity<ApiResponse<List<RoadmapDetailDto>>> getMyProgress() {
        User user = authService.getRequiredAuthenticatedUser();
        List<RoadmapDetailDto> activeRoadmaps = roadmapService.getActiveRoadmapsForUser(user);
        return ResponseEntity.ok(ApiResponse.ok(activeRoadmaps));
    }
}
