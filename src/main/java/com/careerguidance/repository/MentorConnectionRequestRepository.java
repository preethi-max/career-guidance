package com.careerguidance.repository;

import com.careerguidance.entity.MentorConnectionRequest;
import com.careerguidance.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MentorConnectionRequestRepository extends JpaRepository<MentorConnectionRequest, Long> {
    List<MentorConnectionRequest> findByStudentOrderByCreatedAtDesc(User student);
    List<MentorConnectionRequest> findByStudentIdOrderByCreatedAtDesc(Long studentId);
    List<MentorConnectionRequest> findByGuideOrderByCreatedAtDesc(User guide);
    List<MentorConnectionRequest> findByGuideIdOrderByCreatedAtDesc(Long guideId);
    List<MentorConnectionRequest> findByGuideIdAndStatusOrderByCreatedAtDesc(Long guideId, String status);
    Optional<MentorConnectionRequest> findByStudentIdAndGuideId(Long studentId, Long guideId);
    long countByStatus(String status);
}
