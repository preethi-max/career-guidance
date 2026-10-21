package com.careerguidance.repository;

import com.careerguidance.entity.LearningResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LearningResourceRepository extends JpaRepository<LearningResource, Long> {
    List<LearningResource> findByCareerCategoryIgnoreCase(String careerCategory);
    List<LearningResource> findByTypeIgnoreCase(String type);
}
