package com.careerguidance.repository;

import com.careerguidance.entity.Career;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CareerRepository extends JpaRepository<Career, Long> {
    Optional<Career> findByTitle(String title);
    List<Career> findByCategoryIgnoreCase(String category);

    @Query("SELECT DISTINCT c.category FROM Career c ORDER BY c.category")
    List<String> findDistinctCategories();

    @Query("SELECT c FROM Career c WHERE " +
           "LOWER(c.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(c.category) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(c.description) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(c.sampleRoles) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Career> searchCareers(@Param("query") String query);
}
