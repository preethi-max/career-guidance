package com.careerguidance.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class JobPrepUpdateDto {

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Item key is required")
    private String itemKey;

    @NotNull(message = "Completion status is required")
    private Boolean isCompleted;

    private String notes;

    public JobPrepUpdateDto() {}

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getItemKey() { return itemKey; }
    public void setItemKey(String itemKey) { this.itemKey = itemKey; }

    public Boolean getIsCompleted() { return isCompleted; }
    public void setIsCompleted(Boolean completed) { isCompleted = completed; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
