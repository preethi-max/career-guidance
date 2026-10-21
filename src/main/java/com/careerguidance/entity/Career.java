package com.careerguidance.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "careers")
public class Career {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 150)
    private String title;

    @Column(nullable = false, length = 100)
    private String category;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "required_education", nullable = false, columnDefinition = "TEXT")
    private String requiredEducation;

    @Column(name = "salary_range", length = 100)
    private String salaryRange;

    @Column(name = "job_outlook", length = 100)
    private String jobOutlook;

    @Column(name = "sample_roles", columnDefinition = "TEXT")
    private String sampleRoles;

    @Column(name = "preparation_steps", columnDefinition = "TEXT")
    private String preparationSteps;

    @OneToMany(mappedBy = "career", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<CareerSkill> skills = new ArrayList<>();

    @OneToOne(mappedBy = "career", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private CareerRoadmap roadmap;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Career() {}

    public Career(String title, String category, String description, String requiredEducation,
                  String salaryRange, String jobOutlook, String sampleRoles, String preparationSteps) {
        this.title = title;
        this.category = category;
        this.description = description;
        this.requiredEducation = requiredEducation;
        this.salaryRange = salaryRange;
        this.jobOutlook = jobOutlook;
        this.sampleRoles = sampleRoles;
        this.preparationSteps = preparationSteps;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getRequiredEducation() { return requiredEducation; }
    public void setRequiredEducation(String requiredEducation) { this.requiredEducation = requiredEducation; }

    public String getSalaryRange() { return salaryRange; }
    public void setSalaryRange(String salaryRange) { this.salaryRange = salaryRange; }

    public String getJobOutlook() { return jobOutlook; }
    public void setJobOutlook(String jobOutlook) { this.jobOutlook = jobOutlook; }

    public String getSampleRoles() { return sampleRoles; }
    public void setSampleRoles(String sampleRoles) { this.sampleRoles = sampleRoles; }

    public String getPreparationSteps() { return preparationSteps; }
    public void setPreparationSteps(String preparationSteps) { this.preparationSteps = preparationSteps; }

    @JsonIgnore
    public List<CareerSkill> getSkills() { return skills; }
    public void setSkills(List<CareerSkill> skills) { this.skills = skills; }

    @JsonIgnore
    public CareerRoadmap getRoadmap() { return roadmap; }
    public void setRoadmap(CareerRoadmap roadmap) { this.roadmap = roadmap; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
