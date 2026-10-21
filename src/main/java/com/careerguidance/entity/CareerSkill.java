package com.careerguidance.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "career_skills")
public class CareerSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "career_id", nullable = false)
    @JsonIgnore
    private Career career;

    @Column(name = "skill_name", nullable = false, length = 100)
    private String skillName;

    @Column(name = "skill_type", nullable = false, length = 30)
    private String skillType = "TECHNICAL"; // TECHNICAL, SOFT

    @Column(nullable = false)
    private int weight = 1;

    public CareerSkill() {}

    public CareerSkill(Career career, String skillName, String skillType, int weight) {
        this.career = career;
        this.skillName = skillName;
        this.skillType = skillType;
        this.weight = weight;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Career getCareer() { return career; }
    public void setCareer(Career career) { this.career = career; }

    public String getSkillName() { return skillName; }
    public void setSkillName(String skillName) { this.skillName = skillName; }

    public String getSkillType() { return skillType; }
    public void setSkillType(String skillType) { this.skillType = skillType; }

    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }
}
