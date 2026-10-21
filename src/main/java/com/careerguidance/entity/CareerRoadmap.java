package com.careerguidance.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "career_roadmaps")
public class CareerRoadmap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "career_id", nullable = false, unique = true)
    @JsonIgnore
    private Career career;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "estimated_months", length = 50)
    private String estimatedMonths;

    @OneToMany(mappedBy = "roadmap", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("stepNumber ASC")
    @JsonIgnore
    private List<RoadmapStep> steps = new ArrayList<>();

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public CareerRoadmap() {}

    public CareerRoadmap(Career career, String title, String description, String estimatedMonths) {
        this.career = career;
        this.title = title;
        this.description = description;
        this.estimatedMonths = estimatedMonths;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    @JsonIgnore
    public Career getCareer() { return career; }
    public void setCareer(Career career) { this.career = career; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getEstimatedMonths() { return estimatedMonths; }
    public void setEstimatedMonths(String estimatedMonths) { this.estimatedMonths = estimatedMonths; }

    @JsonIgnore
    public List<RoadmapStep> getSteps() { return steps; }
    public void setSteps(List<RoadmapStep> steps) { this.steps = steps; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
