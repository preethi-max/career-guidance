package com.careerguidance.repository;

import com.careerguidance.entity.Career;
import com.careerguidance.entity.CareerSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CareerSkillRepository extends JpaRepository<CareerSkill, Long> {
    List<CareerSkill> findByCareer(Career career);
    List<CareerSkill> findByCareerId(Long careerId);
}
