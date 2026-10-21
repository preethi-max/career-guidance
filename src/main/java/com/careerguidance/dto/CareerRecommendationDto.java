package com.careerguidance.dto;

import com.careerguidance.entity.Career;
import java.util.List;

public class CareerRecommendationDto {
    private Career career;
    private int matchPercentage;
    private List<String> matchedSkills;
    private List<String> missingSkills;
    private List<String> matchedInterests;
    private String educationFit;
    private String recommendationReason;

    public CareerRecommendationDto() {}

    public CareerRecommendationDto(Career career, int matchPercentage, List<String> matchedSkills,
                                   List<String> missingSkills, List<String> matchedInterests,
                                   String educationFit, String recommendationReason) {
        this.career = career;
        this.matchPercentage = matchPercentage;
        this.matchedSkills = matchedSkills;
        this.missingSkills = missingSkills;
        this.matchedInterests = matchedInterests;
        this.educationFit = educationFit;
        this.recommendationReason = recommendationReason;
    }

    public Career getCareer() { return career; }
    public void setCareer(Career career) { this.career = career; }

    public int getMatchPercentage() { return matchPercentage; }
    public void setMatchPercentage(int matchPercentage) { this.matchPercentage = matchPercentage; }

    public List<String> getMatchedSkills() { return matchedSkills; }
    public void setMatchedSkills(List<String> matchedSkills) { this.matchedSkills = matchedSkills; }

    public List<String> getMissingSkills() { return missingSkills; }
    public void setMissingSkills(List<String> missingSkills) { this.missingSkills = missingSkills; }

    public List<String> getMatchedInterests() { return matchedInterests; }
    public void setMatchedInterests(List<String> matchedInterests) { this.matchedInterests = matchedInterests; }

    public String getEducationFit() { return educationFit; }
    public void setEducationFit(String educationFit) { this.educationFit = educationFit; }

    public String getRecommendationReason() { return recommendationReason; }
    public void setRecommendationReason(String recommendationReason) { this.recommendationReason = recommendationReason; }
}
