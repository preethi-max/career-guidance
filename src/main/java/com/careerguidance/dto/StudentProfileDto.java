package com.careerguidance.dto;

import com.careerguidance.entity.StudentProfile;

public class StudentProfileDto {
    private Long id;
    private Long userId;
    private String fullName;
    private String email;
    private String phone;
    private String educationLevel;
    private String degree;
    private String branch;
    private String collegeName;
    private String yearOfStudy;
    private String marksOrCgpa;
    private String technicalSkills;
    private String nonTechnicalSkills;
    private String interests;
    private String strengths;
    private String preferredFields;
    private String shortTermGoal;
    private String longTermGoal;
    private Integer profileCompletionPercentage;

    public StudentProfileDto() {}

    public static StudentProfileDto fromEntity(StudentProfile profile) {
        StudentProfileDto dto = new StudentProfileDto();
        dto.setId(profile.getId());
        if (profile.getUser() != null) {
            dto.setUserId(profile.getUser().getId());
            dto.setFullName(profile.getUser().getFullName());
            dto.setEmail(profile.getUser().getEmail());
            dto.setPhone(profile.getUser().getPhone());
        }
        dto.setEducationLevel(profile.getEducationLevel());
        dto.setDegree(profile.getDegree());
        dto.setBranch(profile.getBranch());
        dto.setCollegeName(profile.getCollegeName());
        dto.setYearOfStudy(profile.getYearOfStudy());
        dto.setMarksOrCgpa(profile.getMarksOrCgpa());
        dto.setTechnicalSkills(profile.getTechnicalSkills());
        dto.setNonTechnicalSkills(profile.getNonTechnicalSkills());
        dto.setInterests(profile.getInterests());
        dto.setStrengths(profile.getStrengths());
        dto.setPreferredFields(profile.getPreferredFields());
        dto.setShortTermGoal(profile.getShortTermGoal());
        dto.setLongTermGoal(profile.getLongTermGoal());
        dto.setProfileCompletionPercentage(profile.getProfileCompletionPercentage());
        return dto;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEducationLevel() { return educationLevel; }
    public void setEducationLevel(String educationLevel) { this.educationLevel = educationLevel; }

    public String getDegree() { return degree; }
    public void setDegree(String degree) { this.degree = degree; }

    public String getBranch() { return branch; }
    public void setBranch(String branch) { this.branch = branch; }

    public String getCollegeName() { return collegeName; }
    public void setCollegeName(String collegeName) { this.collegeName = collegeName; }

    public String getYearOfStudy() { return yearOfStudy; }
    public void setYearOfStudy(String yearOfStudy) { this.yearOfStudy = yearOfStudy; }

    public String getMarksOrCgpa() { return marksOrCgpa; }
    public void setMarksOrCgpa(String marksOrCgpa) { this.marksOrCgpa = marksOrCgpa; }

    public String getTechnicalSkills() { return technicalSkills; }
    public void setTechnicalSkills(String technicalSkills) { this.technicalSkills = technicalSkills; }

    public String getNonTechnicalSkills() { return nonTechnicalSkills; }
    public void setNonTechnicalSkills(String nonTechnicalSkills) { this.nonTechnicalSkills = nonTechnicalSkills; }

    public String getInterests() { return interests; }
    public void setInterests(String interests) { this.interests = interests; }

    public String getStrengths() { return strengths; }
    public void setStrengths(String strengths) { this.strengths = strengths; }

    public String getPreferredFields() { return preferredFields; }
    public void setPreferredFields(String preferredFields) { this.preferredFields = preferredFields; }

    public String getShortTermGoal() { return shortTermGoal; }
    public void setShortTermGoal(String shortTermGoal) { this.shortTermGoal = shortTermGoal; }

    public String getLongTermGoal() { return longTermGoal; }
    public void setLongTermGoal(String longTermGoal) { this.longTermGoal = longTermGoal; }

    public Integer getProfileCompletionPercentage() { return profileCompletionPercentage; }
    public void setProfileCompletionPercentage(Integer profileCompletionPercentage) { this.profileCompletionPercentage = profileCompletionPercentage; }
}
