package com.careerguidance.dto;

import com.careerguidance.entity.CareerGuideProfile;

public class CareerGuideProfileDto {
    private Long id;
    private Long userId;
    private String fullName;
    private String email;
    private String phone;
    private String qualifications;
    private String expertiseAreas;
    private Integer experienceYears;
    private String currentDesignation;
    private String organization;
    private String bio;
    private String availabilityInfo;
    private String linkedinUrl;
    private String approvalStatus;

    public CareerGuideProfileDto() {}

    public static CareerGuideProfileDto fromEntity(CareerGuideProfile profile) {
        CareerGuideProfileDto dto = new CareerGuideProfileDto();
        dto.setId(profile.getId());
        if (profile.getUser() != null) {
            dto.setUserId(profile.getUser().getId());
            dto.setFullName(profile.getUser().getFullName());
            dto.setEmail(profile.getUser().getEmail());
            dto.setPhone(profile.getUser().getPhone());
        }
        dto.setQualifications(profile.getQualifications());
        dto.setExpertiseAreas(profile.getExpertiseAreas());
        dto.setExperienceYears(profile.getExperienceYears());
        dto.setCurrentDesignation(profile.getCurrentDesignation());
        dto.setOrganization(profile.getOrganization());
        dto.setBio(profile.getBio());
        dto.setAvailabilityInfo(profile.getAvailabilityInfo());
        dto.setLinkedinUrl(profile.getLinkedinUrl());
        dto.setApprovalStatus(profile.getApprovalStatus());
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

    public String getQualifications() { return qualifications; }
    public void setQualifications(String qualifications) { this.qualifications = qualifications; }

    public String getExpertiseAreas() { return expertiseAreas; }
    public void setExpertiseAreas(String expertiseAreas) { this.expertiseAreas = expertiseAreas; }

    public Integer getExperienceYears() { return experienceYears; }
    public void setExperienceYears(Integer experienceYears) { this.experienceYears = experienceYears; }

    public String getCurrentDesignation() { return currentDesignation; }
    public void setCurrentDesignation(String currentDesignation) { this.currentDesignation = currentDesignation; }

    public String getOrganization() { return organization; }
    public void setOrganization(String organization) { this.organization = organization; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getAvailabilityInfo() { return availabilityInfo; }
    public void setAvailabilityInfo(String availabilityInfo) { this.availabilityInfo = availabilityInfo; }

    public String getLinkedinUrl() { return linkedinUrl; }
    public void setLinkedinUrl(String linkedinUrl) { this.linkedinUrl = linkedinUrl; }

    public String getApprovalStatus() { return approvalStatus; }
    public void setApprovalStatus(String approvalStatus) { this.approvalStatus = approvalStatus; }
}
