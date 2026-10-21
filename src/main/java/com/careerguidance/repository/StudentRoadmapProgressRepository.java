package com.careerguidance.repository;

import com.careerguidance.entity.CareerRoadmap;
import com.careerguidance.entity.RoadmapStep;
import com.careerguidance.entity.StudentRoadmapProgress;
import com.careerguidance.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRoadmapProgressRepository extends JpaRepository<StudentRoadmapProgress, Long> {
    List<StudentRoadmapProgress> findByUserAndRoadmap(User user, CareerRoadmap roadmap);
    List<StudentRoadmapProgress> findByUserIdAndRoadmapId(Long userId, Long roadmapId);
    Optional<StudentRoadmapProgress> findByUserAndStep(User user, RoadmapStep step);
    Optional<StudentRoadmapProgress> findByUserIdAndStepId(Long userId, Long stepId);

    @Query("SELECT COUNT(p) FROM StudentRoadmapProgress p WHERE p.user.id = :userId AND p.roadmap.id = :roadmapId AND p.isCompleted = true")
    long countCompletedStepsByUserAndRoadmap(@Param("userId") Long userId, @Param("roadmapId") Long roadmapId);

    @Query("SELECT COUNT(p) FROM StudentRoadmapProgress p WHERE p.user.id = :userId AND p.isCompleted = true")
    long countTotalCompletedStepsByUser(@Param("userId") Long userId);
}
