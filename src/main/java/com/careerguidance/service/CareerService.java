package com.careerguidance.service;

import com.careerguidance.entity.Career;
import com.careerguidance.entity.CareerSkill;
import com.careerguidance.repository.CareerRepository;
import com.careerguidance.repository.CareerSkillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CareerService {

    private final CareerRepository careerRepository;
    private final CareerSkillRepository careerSkillRepository;

    public CareerService(CareerRepository careerRepository, CareerSkillRepository careerSkillRepository) {
        this.careerRepository = careerRepository;
        this.careerSkillRepository = careerSkillRepository;
    }

    @Transactional(readOnly = true)
    public List<Career> getAllCareers(String category, String search) {
        if (search != null && !search.isBlank()) {
            return careerRepository.searchCareers(search.trim());
        }
        if (category != null && !category.isBlank() && !"all".equalsIgnoreCase(category)) {
            return careerRepository.findByCategoryIgnoreCase(category.trim());
        }
        return careerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<String> getCategories() {
        return careerRepository.findDistinctCategories();
    }

    @Transactional(readOnly = true)
    public Optional<Career> getCareerById(Long id) {
        return careerRepository.findById(id);
    }

    @Transactional
    public Career saveCareer(Career career) {
        return careerRepository.save(career);
    }

    @Transactional
    public void deleteCareer(Long id) {
        careerRepository.deleteById(id);
    }

    @Transactional
    public CareerSkill addSkillToCareer(Long careerId, String skillName, String skillType, int weight) {
        Career career = careerRepository.findById(careerId)
                .orElseThrow(() -> new IllegalArgumentException("Career not found with ID: " + careerId));
        CareerSkill skill = new CareerSkill(career, skillName, skillType, weight);
        return careerSkillRepository.save(skill);
    }
}
