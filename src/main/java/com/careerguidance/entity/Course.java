package com.careerguidance.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 150)
    private String provider;

    @Column(name = "career_category", length = 100)
    private String careerCategory;

    @Column(length = 50)
    private String level = "BEGINNER"; // BEGINNER, INTERMEDIATE, ADVANCED

    @Column
    private Double rating = 4.5;

    @Column(name = "is_free")
    private Boolean isFree = true;

    @Column(name = "certification_available")
    private Boolean certificationAvailable = true;

    @Column(length = 500)
    private String url;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Course() {}

    public Course(String title, String provider, String careerCategory, String level, Double rating,
                  Boolean isFree, Boolean certificationAvailable, String url, String description) {
        this.title = title;
        this.provider = provider;
        this.careerCategory = careerCategory;
        this.level = level;
        this.rating = rating;
        this.isFree = isFree;
        this.certificationAvailable = certificationAvailable;
        this.url = url;
        this.description = description;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }

    public String getCareerCategory() { return careerCategory; }
    public void setCareerCategory(String careerCategory) { this.careerCategory = careerCategory; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }

    public Boolean getIsFree() { return isFree; }
    public void setIsFree(Boolean free) { isFree = free; }

    public Boolean getCertificationAvailable() { return certificationAvailable; }
    public void setCertificationAvailable(Boolean certificationAvailable) { this.certificationAvailable = certificationAvailable; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
