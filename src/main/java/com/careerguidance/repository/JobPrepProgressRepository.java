package com.careerguidance.repository;

import com.careerguidance.entity.JobPrepProgress;
import com.careerguidance.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobPrepProgressRepository extends JpaRepository<JobPrepProgress, Long> {
    List<JobPrepProgress> findByUser(User user);
    List<JobPrepProgress> findByUserId(Long userId);
    List<JobPrepProgress> findByUserIdAndCategory(Long userId, String category);
    Optional<JobPrepProgress> findByUserIdAndItemKey(Long userId, String itemKey);
    long countByUserIdAndIsCompletedTrue(Long userId);
}
