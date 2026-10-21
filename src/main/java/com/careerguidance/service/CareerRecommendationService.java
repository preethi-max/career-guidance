package com.careerguidance.service;

import com.careerguidance.dto.CareerRecommendationDto;
import com.careerguidance.entity.Career;
import com.careerguidance.entity.CareerSkill;
import com.careerguidance.entity.StudentProfile;
import com.careerguidance.entity.User;
import com.careerguidance.repository.CareerRepository;
import com.careerguidance.repository.StudentProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CareerRecommendationService {

    private final CareerRepository careerRepository;
    private final StudentProfileRepository studentProfileRepository;

    public CareerRecommendationService(CareerRepository careerRepository,
                                       StudentProfileRepository studentProfileRepository) {
        this.careerRepository = careerRepository;
        this.studentProfileRepository = studentProfileRepository;
    }

    @Transactional(readOnly = true)
    public List<CareerRecommendationDto> getRecommendationsForStudent(User user) {
        Optional<StudentProfile> profileOpt = studentProfileRepository.findByUser(user);
        List<Career> allCareers = careerRepository.findAll();

        if (profileOpt.isEmpty()) {
            return fallbackRecommendations(allCareers, "Complete your profile to receive personalized recommendations.");
        }

        StudentProfile profile = profileOpt.get();
        Set<String> studentTechSkills = parseTokens(profile.getTechnicalSkills());
        Set<String> studentSoftSkills = parseTokens(profile.getNonTechnicalSkills());
        Set<String> studentInterests = parseTokens(profile.getInterests());
        Set<String> studentStrengths = parseTokens(profile.getStrengths());
        Set<String> studentPreferredFields = parseTokens(profile.getPreferredFields());

        String branch = profile.getBranch() != null ? profile.getBranch().toLowerCase().trim() : "";
        String degree = profile.getDegree() != null ? profile.getDegree().toLowerCase().trim() : "";

        List<CareerRecommendationDto> recommendations = new ArrayList<>();

        for (Career career : allCareers) {
            List<CareerSkill> careerSkills = career.getSkills();
            List<String> careerTechSkills = careerSkills.stream()
                    .filter(s -> "TECHNICAL".equalsIgnoreCase(s.getSkillType()))
                    .map(CareerSkill::getSkillName)
                    .collect(Collectors.toList());

            List<String> careerSoftSkills = careerSkills.stream()
                    .filter(s -> "SOFT".equalsIgnoreCase(s.getSkillType()))
                    .map(CareerSkill::getSkillName)
                    .collect(Collectors.toList());

            // 1. Technical Skills Match (Weight: 45)
            List<String> matchedSkills = new ArrayList<>();
            List<String> missingSkills = new ArrayList<>();

            for (String cSkill : careerTechSkills) {
                if (containsFuzzyMatch(studentTechSkills, cSkill)) {
                    matchedSkills.add(cSkill);
                } else {
                    missingSkills.add(cSkill);
                }
            }

            double techScore = 0.0;
            if (!careerTechSkills.isEmpty()) {
                techScore = ((double) matchedSkills.size() / careerTechSkills.size()) * 45.0;
            } else {
                techScore = 20.0;
            }

            // 2. Interests and Preferred Fields Match (Weight: 25)
            double interestScore = 0.0;
            List<String> matchedInterests = new ArrayList<>();
            String careerCategoryLower = career.getCategory().toLowerCase();
            String careerTitleLower = career.getTitle().toLowerCase();

            for (String interest : studentInterests) {
                if (careerCategoryLower.contains(interest) || careerTitleLower.contains(interest)
                        || career.getDescription().toLowerCase().contains(interest)) {
                    matchedInterests.add(interest);
                }
            }
            for (String field : studentPreferredFields) {
                if (careerCategoryLower.contains(field) || careerTitleLower.contains(field)) {
                    if (!matchedInterests.contains(field)) {
                        matchedInterests.add(field);
                    }
                }
            }

            if (!matchedInterests.isEmpty()) {
                interestScore = Math.min(25.0, matchedInterests.size() * 12.5);
            }

            // 3. Education Alignment (Weight: 20)
            double educationScore = 10.0; // baseline
            String educationFit = "Standard Requirement";
            String reqEduLower = career.getRequiredEducation().toLowerCase();

            if (!branch.isBlank() && (reqEduLower.contains(branch) ||
                    (branch.contains("computer") || branch.contains("cs") || branch.contains("it")) && careerCategoryLower.contains("software") ||
                    (branch.contains("business") || branch.contains("management")) && careerCategoryLower.contains("business") ||
                    (branch.contains("design") || branch.contains("art")) && careerCategoryLower.contains("design") ||
                    (branch.contains("finance") || branch.contains("commerce") || branch.contains("economics")) && careerCategoryLower.contains("finance"))) {
                educationScore = 20.0;
                educationFit = "High Alignment (" + profile.getDegree() + " in " + profile.getBranch() + ")";
            } else if (!degree.isBlank()) {
                educationScore = 15.0;
                educationFit = "Eligible Foundation (" + profile.getDegree() + ")";
            }

            // 4. Soft Skills & Strengths (Weight: 10)
            double softScore = 0.0;
            for (String sSkill : careerSoftSkills) {
                if (containsFuzzyMatch(studentSoftSkills, sSkill) || containsFuzzyMatch(studentStrengths, sSkill)) {
                    softScore += 3.5;
                }
            }
            softScore = Math.min(10.0, softScore);

            // Compute total percentage (constrained between 20% and 98%)
            int totalMatchPercentage = (int) Math.round(techScore + interestScore + educationScore + softScore);
            if (studentTechSkills.isEmpty() && studentInterests.isEmpty()) {
                totalMatchPercentage = 40; // baseline if empty profile
            }
            totalMatchPercentage = Math.max(15, Math.min(99, totalMatchPercentage));

            // Generate Explanation
            StringBuilder reasonBuilder = new StringBuilder();
            if (!matchedSkills.isEmpty()) {
                reasonBuilder.append("Matches your technical skills in ").append(String.join(", ", matchedSkills)).append(". ");
            }
            if (!matchedInterests.isEmpty()) {
                reasonBuilder.append("Directly aligns with your interest in ").append(String.join(", ", matchedInterests)).append(". ");
            }
            if (!missingSkills.isEmpty()) {
                reasonBuilder.append("Key skill recommended to learn: ").append(missingSkills.get(0)).append(". ");
            } else {
                reasonBuilder.append("You possess strong foundational technical competencies for this path. ");
            }

            recommendations.add(new CareerRecommendationDto(
                    career,
                    totalMatchPercentage,
                    matchedSkills,
                    missingSkills,
                    matchedInterests,
                    educationFit,
                    reasonBuilder.toString().trim()
            ));
        }

        recommendations.sort((a, b) -> Integer.compare(b.getMatchPercentage(), a.getMatchPercentage()));
        return recommendations;
    }

    private List<CareerRecommendationDto> fallbackRecommendations(List<Career> careers, String reason) {
        return careers.stream().map(c -> new CareerRecommendationDto(
                c,
                50,
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                "Baseline Fit",
                reason
        )).collect(Collectors.toList());
    }

    private Set<String> parseTokens(String input) {
        if (input == null || input.isBlank()) return Collections.emptySet();
        return Arrays.stream(input.split("[,;\n\r]+"))
                .map(String::trim)
                .map(String::toLowerCase)
                .filter(s -> !s.isBlank())
                .collect(Collectors.toSet());
    }

    private boolean containsFuzzyMatch(Set<String> studentTokens, String target) {
        String targetLower = target.toLowerCase().trim();
        for (String token : studentTokens) {
            if (token.equalsIgnoreCase(targetLower) ||
                token.contains(targetLower) ||
                targetLower.contains(token)) {
                return true;
            }
        }
        return false;
    }
}
