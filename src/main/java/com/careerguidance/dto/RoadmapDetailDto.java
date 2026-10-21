package com.careerguidance.dto;

import java.util.List;

public class RoadmapDetailDto {
    private Long roadmapId;
    private Long careerId;
    private String careerTitle;
    private String title;
    private String description;
    private String estimatedMonths;
    private int totalSteps;
    private int completedSteps;
    private int progressPercentage;
    private List<RoadmapStepDto> steps;

    public RoadmapDetailDto() {}

    public Long getRoadmapId() { return roadmapId; }
    public void setRoadmapId(Long roadmapId) { this.roadmapId = roadmapId; }

    public Long getCareerId() { return careerId; }
    public void setCareerId(Long careerId) { this.careerId = careerId; }

    public String getCareerTitle() { return careerTitle; }
    public void setCareerTitle(String careerTitle) { this.careerTitle = careerTitle; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getEstimatedMonths() { return estimatedMonths; }
    public void setEstimatedMonths(String estimatedMonths) { this.estimatedMonths = estimatedMonths; }

    public int getTotalSteps() { return totalSteps; }
    public void setTotalSteps(int totalSteps) { this.totalSteps = totalSteps; }

    public int getCompletedSteps() { return completedSteps; }
    public void setCompletedSteps(int completedSteps) { this.completedSteps = completedSteps; }

    public int getProgressPercentage() { return progressPercentage; }
    public void setProgressPercentage(int progressPercentage) { this.progressPercentage = progressPercentage; }

    public List<RoadmapStepDto> getSteps() { return steps; }
    public void setSteps(List<RoadmapStepDto> steps) { this.steps = steps; }

    public static class RoadmapStepDto {
        private Long stepId;
        private int stepNumber;
        private String title;
        private String description;
        private String keyTopics;
        private String learningResourcesText;
        private String estimatedWeeks;
        private boolean completed;

        public RoadmapStepDto() {}

        public Long getStepId() { return stepId; }
        public void setStepId(Long stepId) { this.stepId = stepId; }

        public int getStepNumber() { return stepNumber; }
        public void setStepNumber(int stepNumber) { this.stepNumber = stepNumber; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public String getKeyTopics() { return keyTopics; }
        public void setKeyTopics(String keyTopics) { this.keyTopics = keyTopics; }

        public String getLearningResourcesText() { return learningResourcesText; }
        public void setLearningResourcesText(String learningResourcesText) { this.learningResourcesText = learningResourcesText; }

        public String getEstimatedWeeks() { return estimatedWeeks; }
        public void setEstimatedWeeks(String estimatedWeeks) { this.estimatedWeeks = estimatedWeeks; }

        public boolean isCompleted() { return completed; }
        public void setCompleted(boolean completed) { this.completed = completed; }
    }
}
