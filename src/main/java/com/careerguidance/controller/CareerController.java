package com.careerguidance.controller;

import com.careerguidance.dto.ApiResponse;
import com.careerguidance.dto.CareerRecommendationDto;
import com.careerguidance.entity.Career;
import com.careerguidance.entity.User;
import com.careerguidance.service.AuthService;
import com.careerguidance.service.CareerRecommendationService;
import com.careerguidance.service.CareerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/careers")
public class CareerController {

    private final CareerService careerService;
    private final CareerRecommendationService recommendationService;
    private final AuthService authService;

    public CareerController(CareerService careerService,
                            CareerRecommendationService recommendationService,
                            AuthService authService) {
        this.careerService = careerService;
        this.recommendationService = recommendationService;
        this.authService = authService;
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Career>>> getAllCareers(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search) {
        List<Career> careers = careerService.getAllCareers(category, search);
        return ResponseEntity.ok(ApiResponse.ok(careers));
    }

    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<String>>> getCategories() {
        List<String> categories = careerService.getCategories();
        return ResponseEntity.ok(ApiResponse.ok(categories));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Career>> getCareerById(@PathVariable Long id) {
        return careerService.getCareerById(id)
                .map(c -> ResponseEntity.ok(ApiResponse.ok(c)))
                .orElseGet(() -> ResponseEntity.badRequest().body(ApiResponse.error("Career not found with ID: " + id)));
    }

    @GetMapping("/recommendations")
    public ResponseEntity<ApiResponse<List<CareerRecommendationDto>>> getRecommendations() {
        User user = authService.getRequiredAuthenticatedUser();
        List<CareerRecommendationDto> recommendations = recommendationService.getRecommendationsForStudent(user);
        return ResponseEntity.ok(ApiResponse.ok(recommendations));
    }
}
