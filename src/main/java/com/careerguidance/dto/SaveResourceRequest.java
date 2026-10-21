package com.careerguidance.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SaveResourceRequest {

    @NotBlank(message = "Resource type is required")
    private String resourceType; // COURSE, RESOURCE, CAREER

    @NotNull(message = "Resource ID is required")
    private Long resourceId;

    public SaveResourceRequest() {}

    public SaveResourceRequest(String resourceType, Long resourceId) {
        this.resourceType = resourceType;
        this.resourceId = resourceId;
    }

    public String getResourceType() { return resourceType; }
    public void setResourceType(String resourceType) { this.resourceType = resourceType; }

    public Long getResourceId() { return resourceId; }
    public void setResourceId(Long resourceId) { this.resourceId = resourceId; }
}
