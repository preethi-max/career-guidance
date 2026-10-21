package com.careerguidance.service;

import com.careerguidance.dto.SaveResourceRequest;
import com.careerguidance.entity.Course;
import com.careerguidance.entity.LearningResource;
import com.careerguidance.entity.SavedResource;
import com.careerguidance.entity.User;
import com.careerguidance.repository.CourseRepository;
import com.careerguidance.repository.LearningResourceRepository;
import com.careerguidance.repository.SavedResourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final LearningResourceRepository resourceRepository;
    private final SavedResourceRepository savedResourceRepository;

    public CourseService(CourseRepository courseRepository,
                         LearningResourceRepository resourceRepository,
                         SavedResourceRepository savedResourceRepository) {
        this.courseRepository = courseRepository;
        this.resourceRepository = resourceRepository;
        this.savedResourceRepository = savedResourceRepository;
    }

    @Transactional(readOnly = true)
    public List<Course> getAllCourses(String category, String level, String search) {
        if (search != null && !search.isBlank()) {
            return courseRepository.searchCourses(search.trim());
        }
        if (category != null && !category.isBlank() && !"all".equalsIgnoreCase(category)) {
            return courseRepository.findByCareerCategoryIgnoreCase(category.trim());
        }
        if (level != null && !level.isBlank() && !"all".equalsIgnoreCase(level)) {
            return courseRepository.findByLevelIgnoreCase(level.trim());
        }
        return courseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<String> getCourseCategories() {
        return courseRepository.findDistinctCategories();
    }

    @Transactional(readOnly = true)
    public List<LearningResource> getLearningResources(String category, String type) {
        if (category != null && !category.isBlank() && !"all".equalsIgnoreCase(category)) {
            return resourceRepository.findByCareerCategoryIgnoreCase(category.trim());
        }
        if (type != null && !type.isBlank() && !"all".equalsIgnoreCase(type)) {
            return resourceRepository.findByTypeIgnoreCase(type.trim());
        }
        return resourceRepository.findAll();
    }

    @Transactional
    public boolean toggleSaveResource(User user, SaveResourceRequest req) {
        Optional<SavedResource> existing = savedResourceRepository
                .findByUserIdAndResourceTypeAndResourceId(user.getId(), req.getResourceType().toUpperCase(), req.getResourceId());

        if (existing.isPresent()) {
            savedResourceRepository.delete(existing.get());
            return false; // Removed from saved
        } else {
            SavedResource saved = new SavedResource(user, req.getResourceType().toUpperCase(), req.getResourceId());
            savedResourceRepository.save(saved);
            return true; // Added to saved
        }
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getSavedResources(User user) {
        List<SavedResource> savedList = savedResourceRepository.findByUserOrderByCreatedAtDesc(user);
        List<Course> savedCourses = new ArrayList<>();
        List<LearningResource> savedLearningResources = new ArrayList<>();

        for (SavedResource sr : savedList) {
            if ("COURSE".equalsIgnoreCase(sr.getResourceType())) {
                courseRepository.findById(sr.getResourceId()).ifPresent(savedCourses::add);
            } else if ("RESOURCE".equalsIgnoreCase(sr.getResourceType())) {
                resourceRepository.findById(sr.getResourceId()).ifPresent(savedLearningResources::add);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("courses", savedCourses);
        result.put("resources", savedLearningResources);
        result.put("totalCount", savedList.size());
        return result;
    }

    @Transactional(readOnly = true)
    public Set<Long> getSavedCourseIds(User user) {
        List<SavedResource> saved = savedResourceRepository.findByUserIdAndResourceType(user.getId(), "COURSE");
        Set<Long> ids = new HashSet<>();
        for (SavedResource s : saved) {
            ids.add(s.getResourceId());
        }
        return ids;
    }
}
