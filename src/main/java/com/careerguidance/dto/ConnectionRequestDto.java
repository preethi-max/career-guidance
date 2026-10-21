package com.careerguidance.dto;

import com.careerguidance.entity.MentorConnectionRequest;
import java.time.LocalDateTime;

public class ConnectionRequestDto {
    private Long id;
    private Long studentId;
    private String studentName;
    private String studentEmail;
    private Long guideId;
    private String guideName;
    private String guideDesignation;
    private String guideOrganization;
    private String status;
    private String initialMessage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private long unreadMessagesCount;
    private String lastMessage;

    public ConnectionRequestDto() {}

    public static ConnectionRequestDto fromEntity(MentorConnectionRequest req) {
        ConnectionRequestDto dto = new ConnectionRequestDto();
        dto.setId(req.getId());
        if (req.getStudent() != null) {
            dto.setStudentId(req.getStudent().getId());
            dto.setStudentName(req.getStudent().getFullName());
            dto.setStudentEmail(req.getStudent().getEmail());
        }
        if (req.getGuide() != null) {
            dto.setGuideId(req.getGuide().getId());
            dto.setGuideName(req.getGuide().getFullName());
        }
        dto.setStatus(req.getStatus());
        dto.setInitialMessage(req.getInitialMessage());
        dto.setCreatedAt(req.getCreatedAt());
        dto.setUpdatedAt(req.getUpdatedAt());
        return dto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getStudentEmail() { return studentEmail; }
    public void setStudentEmail(String studentEmail) { this.studentEmail = studentEmail; }

    public Long getGuideId() { return guideId; }
    public void setGuideId(Long guideId) { this.guideId = guideId; }

    public String getGuideName() { return guideName; }
    public void setGuideName(String guideName) { this.guideName = guideName; }

    public String getGuideDesignation() { return guideDesignation; }
    public void setGuideDesignation(String guideDesignation) { this.guideDesignation = guideDesignation; }

    public String getGuideOrganization() { return guideOrganization; }
    public void setGuideOrganization(String guideOrganization) { this.guideOrganization = guideOrganization; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getInitialMessage() { return initialMessage; }
    public void setInitialMessage(String initialMessage) { this.initialMessage = initialMessage; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public long getUnreadMessagesCount() { return unreadMessagesCount; }
    public void setUnreadMessagesCount(long unreadMessagesCount) { this.unreadMessagesCount = unreadMessagesCount; }

    public String getLastMessage() { return lastMessage; }
    public void setLastMessage(String lastMessage) { this.lastMessage = lastMessage; }
}
