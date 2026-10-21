package com.careerguidance.controller;

import com.careerguidance.dto.ApiResponse;
import com.careerguidance.dto.SaveResourceRequest;
import com.careerguidance.entity.Course;
import com.careerguidance.entity.LearningResource;
import com.careerguidance.entity.User;
import com.careerguidance.service.AuthService;
import com.careerguidance.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;
    private final AuthService authService;

    public CourseController(CourseService courseService, AuthService authService) {
        this.courseService = courseService;
        this.authService = authService;
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Course>>> getAllCourses(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String level,
            @RequestParam(required = false) String search) {
        List<Course> courses = courseService.getAllCourses(category, level, search);
        return ResponseEntity.ok(ApiResponse.ok(courses));
    }

    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<String>>> getCategories() {
        return ResponseEntity.ok(ApiResponse.ok(courseService.getCourseCategories()));
    }

    @GetMapping("/resources")
    public ResponseEntity<ApiResponse<List<LearningResource>>> getResources(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String type) {
        return ResponseEntity.ok(ApiResponse.ok(courseService.getLearningResources(category, type)));
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<Map<String, Object>>> toggleSaveResource(@Valid @RequestBody SaveResourceRequest req) {
        User user = authService.getRequiredAuthenticatedUser();
        boolean isSaved = courseService.toggleSaveResource(user, req);
        String msg = isSaved ? "Resource saved to your bookmarks" : "Resource removed from your bookmarks";
        return ResponseEntity.ok(ApiResponse.ok(msg, Map.of("saved", isSaved, "resourceId", req.getResourceId())));
    }

    @GetMapping("/saved")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getSaved() {
        User user = authService.getRequiredAuthenticatedUser();
        return ResponseEntity.ok(ApiResponse.ok(courseService.getSavedResources(user)));
    }

    @GetMapping("/saved-ids")
    public ResponseEntity<ApiResponse<Set<Long>>> getSavedCourseIds() {
        Optional<User> userOpt = authService.getCurrentAuthenticatedUser();
        if (userOpt.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.ok(Set.of()));
        }
        return ResponseEntity.ok(ApiResponse.ok(courseService.getSavedCourseIds(userOpt.get())));
    }
}
