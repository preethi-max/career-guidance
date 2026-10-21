package com.careerguidance.dto;

import com.careerguidance.entity.ChatMessage;
import java.time.LocalDateTime;

public class ChatMessageDto {
    private Long id;
    private Long connectionRequestId;
    private Long senderId;
    private String senderName;
    private Long recipientId;
    private String recipientName;
    private String messageText;
    private Boolean isRead;
    private LocalDateTime sentAt;
    private boolean isMine;

    public ChatMessageDto() {}

    public static ChatMessageDto fromEntity(ChatMessage msg, Long currentUserId) {
        ChatMessageDto dto = new ChatMessageDto();
        dto.setId(msg.getId());
        if (msg.getConnectionRequest() != null) {
            dto.setConnectionRequestId(msg.getConnectionRequest().getId());
        }
        if (msg.getSender() != null) {
            dto.setSenderId(msg.getSender().getId());
            dto.setSenderName(msg.getSender().getFullName());
            dto.setMine(currentUserId != null && msg.getSender().getId().equals(currentUserId));
        }
        if (msg.getRecipient() != null) {
            dto.setRecipientId(msg.getRecipient().getId());
            dto.setRecipientName(msg.getRecipient().getFullName());
        }
        dto.setMessageText(msg.getMessageText());
        dto.setIsRead(msg.getIsRead());
        dto.setSentAt(msg.getSentAt());
        return dto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getConnectionRequestId() { return connectionRequestId; }
    public void setConnectionRequestId(Long connectionRequestId) { this.connectionRequestId = connectionRequestId; }

    public Long getSenderId() { return senderId; }
    public void setSenderId(Long senderId) { this.senderId = senderId; }

    public String getSenderName() { return senderName; }
    public void setSenderName(String senderName) { this.senderName = senderName; }

    public Long getRecipientId() { return recipientId; }
    public void setRecipientId(Long recipientId) { this.recipientId = recipientId; }

    public String getRecipientName() { return recipientName; }
    public void setRecipientName(String recipientName) { this.recipientName = recipientName; }

    public String getMessageText() { return messageText; }
    public void setMessageText(String messageText) { this.messageText = messageText; }

    public Boolean getIsRead() { return isRead; }
    public void setIsRead(Boolean read) { isRead = read; }

    public LocalDateTime getSentAt() { return sentAt; }
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }

    public boolean isMine() { return isMine; }
    public void setMine(boolean mine) { isMine = mine; }
}
