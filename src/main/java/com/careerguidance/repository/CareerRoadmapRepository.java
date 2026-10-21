package com.careerguidance.repository;

import com.careerguidance.entity.Career;
import com.careerguidance.entity.CareerRoadmap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CareerRoadmapRepository extends JpaRepository<CareerRoadmap, Long> {
    Optional<CareerRoadmap> findByCareer(Career career);
    Optional<CareerRoadmap> findByCareerId(Long careerId);
}
