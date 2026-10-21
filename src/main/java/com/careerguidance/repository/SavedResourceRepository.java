package com.careerguidance.repository;

import com.careerguidance.entity.SavedResource;
import com.careerguidance.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedResourceRepository extends JpaRepository<SavedResource, Long> {
    List<SavedResource> findByUserOrderByCreatedAtDesc(User user);
    List<SavedResource> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<SavedResource> findByUserIdAndResourceType(Long userId, String resourceType);
    Optional<SavedResource> findByUserIdAndResourceTypeAndResourceId(Long userId, String resourceType, Long resourceId);
    boolean existsByUserIdAndResourceTypeAndResourceId(Long userId, String resourceType, Long resourceId);
    void deleteByUserIdAndResourceTypeAndResourceId(Long userId, String resourceType, Long resourceId);
    long countByUserId(Long userId);
}
