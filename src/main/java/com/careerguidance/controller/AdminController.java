package com.careerguidance.controller;

import com.careerguidance.dto.AdminStatsDto;
import com.careerguidance.dto.ApiResponse;
import com.careerguidance.dto.CareerGuideProfileDto;
import com.careerguidance.dto.UserDto;
import com.careerguidance.entity.Career;
import com.careerguidance.entity.Course;
import com.careerguidance.repository.CareerRepository;
import com.careerguidance.repository.CourseRepository;
import com.careerguidance.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final CareerRepository careerRepository;
    private final CourseRepository courseRepository;

    public AdminController(AdminService adminService,
                           CareerRepository careerRepository,
                           CourseRepository courseRepository) {
        this.adminService = adminService;
        this.careerRepository = careerRepository;
        this.courseRepository = courseRepository;
    }

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<AdminStatsDto>> getStats() {
        return ResponseEntity.ok(ApiResponse.ok(adminService.getPlatformStats()));
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {
        return ResponseEntity.ok(ApiResponse.ok(adminService.getAllUsers()));
    }

    @PutMapping("/users/{id}/status")
    public ResponseEntity<ApiResponse<UserDto>> toggleUserStatus(
            @PathVariable Long id,
            @RequestBody Map<String, Boolean> payload) {
        boolean active = payload.getOrDefault("active", true);
        UserDto updated = adminService.toggleUserActiveStatus(id, active);
        String msg = active ? "User account activated successfully" : "User account deactivated successfully";
        return ResponseEntity.ok(ApiResponse.ok(msg, updated));
    }

    @GetMapping("/pending-guides")
    public ResponseEntity<ApiResponse<List<CareerGuideProfileDto>>> getPendingGuides() {
        return ResponseEntity.ok(ApiResponse.ok(adminService.getPendingGuideApplications()));
    }

    @PutMapping("/guides/{profileId}/review")
    public ResponseEntity<ApiResponse<CareerGuideProfileDto>> reviewGuide(
            @PathVariable Long profileId,
            @RequestBody Map<String, String> payload) {
        String status = payload.getOrDefault("status", "REJECTED");
        try {
            CareerGuideProfileDto updated = adminService.reviewGuideApplication(profileId, status);
            return ResponseEntity.ok(ApiResponse.ok("Career Guide application " + status.toLowerCase() + " successfully", updated));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ex.getMessage()));
        }
    }

    @PostMapping("/careers")
    public ResponseEntity<ApiResponse<Career>> createCareer(@RequestBody Career career) {
        Career saved = careerRepository.save(career);
        return ResponseEntity.ok(ApiResponse.ok("Career created successfully", saved));
    }

    @PutMapping("/careers/{id}")
    public ResponseEntity<ApiResponse<Career>> updateCareer(@PathVariable Long id, @RequestBody Career career) {
        return careerRepository.findById(id).map(existing -> {
            existing.setTitle(career.getTitle());
            existing.setCategory(career.getCategory());
            existing.setDescription(career.getDescription());
            existing.setRequiredEducation(career.getRequiredEducation());
            existing.setSalaryRange(career.getSalaryRange());
            existing.setJobOutlook(career.getJobOutlook());
            existing.setSampleRoles(career.getSampleRoles());
            existing.setPreparationSteps(career.getPreparationSteps());
            Career saved = careerRepository.save(existing);
            return ResponseEntity.ok(ApiResponse.ok("Career updated successfully", saved));
        }).orElseGet(() -> ResponseEntity.badRequest().body(ApiResponse.error("Career not found with ID: " + id)));
    }

    @DeleteMapping("/careers/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCareer(@PathVariable Long id) {
        if (!careerRepository.existsById(id)) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Career not found with ID: " + id));
        }
        careerRepository.deleteById(id);
        return ResponseEntity.ok(ApiResponse.ok("Career deleted successfully", null));
    }

    @PostMapping("/courses")
    public ResponseEntity<ApiResponse<Course>> createCourse(@RequestBody Course course) {
        Course saved = courseRepository.save(course);
        return ResponseEntity.ok(ApiResponse.ok("Course added successfully", saved));
    }

    @PutMapping("/courses/{id}")
    public ResponseEntity<ApiResponse<Course>> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        return courseRepository.findById(id).map(existing -> {
            existing.setTitle(course.getTitle());
            existing.setProvider(course.getProvider());
            existing.setCareerCategory(course.getCareerCategory());
            existing.setLevel(course.getLevel());
            existing.setRating(course.getRating());
            existing.setIsFree(course.getIsFree());
            existing.setCertificationAvailable(course.getCertificationAvailable());
            existing.setUrl(course.getUrl());
            existing.setDescription(course.getDescription());
            Course saved = courseRepository.save(existing);
            return ResponseEntity.ok(ApiResponse.ok("Course updated successfully", saved));
        }).orElseGet(() -> ResponseEntity.badRequest().body(ApiResponse.error("Course not found with ID: " + id)));
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCourse(@PathVariable Long id) {
        if (!courseRepository.existsById(id)) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Course not found with ID: " + id));
        }
        courseRepository.deleteById(id);
        return ResponseEntity.ok(ApiResponse.ok("Course deleted successfully", null));
    }
}
