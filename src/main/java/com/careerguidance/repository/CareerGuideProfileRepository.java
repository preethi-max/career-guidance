package com.careerguidance.repository;

import com.careerguidance.entity.CareerGuideProfile;
import com.careerguidance.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CareerGuideProfileRepository extends JpaRepository<CareerGuideProfile, Long> {
    Optional<CareerGuideProfile> findByUser(User user);
    Optional<CareerGuideProfile> findByUserId(Long userId);
    List<CareerGuideProfile> findByApprovalStatus(String approvalStatus);
    long countByApprovalStatus(String approvalStatus);
}
