package com.careerguidance.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "student_roadmap_progress",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "step_id"})})
public class StudentRoadmapProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roadmap_id", nullable = false)
    private CareerRoadmap roadmap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "step_id", nullable = false)
    private RoadmapStep step;

    @Column(name = "is_completed", nullable = false)
    private boolean isCompleted = false;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    public StudentRoadmapProgress() {}

    public StudentRoadmapProgress(User user, CareerRoadmap roadmap, RoadmapStep step, boolean isCompleted) {
        this.user = user;
        this.roadmap = roadmap;
        this.step = step;
        this.isCompleted = isCompleted;
        this.completedAt = isCompleted ? LocalDateTime.now() : null;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public CareerRoadmap getRoadmap() { return roadmap; }
    public void setRoadmap(CareerRoadmap roadmap) { this.roadmap = roadmap; }

    public RoadmapStep getStep() { return step; }
    public void setStep(RoadmapStep step) { this.step = step; }

    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) {
        isCompleted = completed;
        this.completedAt = completed ? LocalDateTime.now() : null;
    }

    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
}
