package com.careerguidance.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "roadmap_steps")
public class RoadmapStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roadmap_id", nullable = false)
    @JsonIgnore
    private CareerRoadmap roadmap;

    @Column(name = "step_number", nullable = false)
    private int stepNumber;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "key_topics", columnDefinition = "TEXT")
    private String keyTopics;

    @Column(name = "learning_resources_text", columnDefinition = "TEXT")
    private String learningResourcesText;

    @Column(name = "estimated_weeks", length = 50)
    private String estimatedWeeks;

    public RoadmapStep() {}

    public RoadmapStep(CareerRoadmap roadmap, int stepNumber, String title, String description,
                       String keyTopics, String learningResourcesText, String estimatedWeeks) {
        this.roadmap = roadmap;
        this.stepNumber = stepNumber;
        this.title = title;
        this.description = description;
        this.keyTopics = keyTopics;
        this.learningResourcesText = learningResourcesText;
        this.estimatedWeeks = estimatedWeeks;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public CareerRoadmap getRoadmap() { return roadmap; }
    public void setRoadmap(CareerRoadmap roadmap) { this.roadmap = roadmap; }

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
}
