package com.careerguidance.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SendMessageRequest {

    @NotNull(message = "Connection request ID is required")
    private Long connectionRequestId;

    @NotBlank(message = "Message text cannot be empty")
    private String messageText;

    public SendMessageRequest() {}

    public Long getConnectionRequestId() { return connectionRequestId; }
    public void setConnectionRequestId(Long connectionRequestId) { this.connectionRequestId = connectionRequestId; }

    public String getMessageText() { return messageText; }
    public void setMessageText(String messageText) { this.messageText = messageText; }
}
