package com.careerguidance.service;

import com.careerguidance.dto.ChatMessageDto;
import com.careerguidance.dto.SendMessageRequest;
import com.careerguidance.entity.ChatMessage;
import com.careerguidance.entity.MentorConnectionRequest;
import com.careerguidance.entity.User;
import com.careerguidance.repository.ChatMessageRepository;
import com.careerguidance.repository.MentorConnectionRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final MentorConnectionRequestRepository requestRepository;

    public ChatService(ChatMessageRepository chatMessageRepository,
                       MentorConnectionRequestRepository requestRepository) {
        this.chatMessageRepository = chatMessageRepository;
        this.requestRepository = requestRepository;
    }

    @Transactional
    public ChatMessageDto sendMessage(User sender, SendMessageRequest req) {
        MentorConnectionRequest connReq = requestRepository.findById(req.getConnectionRequestId())
                .orElseThrow(() -> new IllegalArgumentException("Connection request not found"));

        if (!"ACCEPTED".equalsIgnoreCase(connReq.getStatus())) {
            throw new IllegalStateException("Messages can only be sent once the mentor connection request is ACCEPTED.");
        }

        User recipient;
        if (connReq.getStudent().getId().equals(sender.getId())) {
            recipient = connReq.getGuide();
        } else if (connReq.getGuide().getId().equals(sender.getId())) {
            recipient = connReq.getStudent();
        } else {
            throw new IllegalStateException("You are not an authorized participant in this conversation.");
        }

        ChatMessage message = new ChatMessage(connReq, sender, recipient, req.getMessageText().trim());
        ChatMessage saved = chatMessageRepository.save(message);

        return ChatMessageDto.fromEntity(saved, sender.getId());
    }

    @Transactional
    public List<ChatMessageDto> getMessages(User user, Long connectionRequestId, LocalDateTime afterTime) {
        MentorConnectionRequest connReq = requestRepository.findById(connectionRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Connection request not found"));

        boolean isParticipant = connReq.getStudent().getId().equals(user.getId()) ||
                                connReq.getGuide().getId().equals(user.getId());
        if (!isParticipant) {
            throw new IllegalStateException("Unauthorized access to this conversation.");
        }

        List<ChatMessage> messages;
        if (afterTime != null) {
            messages = chatMessageRepository.findNewMessages(connectionRequestId, afterTime);
        } else {
            messages = chatMessageRepository.findByConnectionRequestIdOrderBySentAtAsc(connectionRequestId);
        }

        // Mark incoming messages as read
        for (ChatMessage msg : messages) {
            if (msg.getRecipient() != null && msg.getRecipient().getId().equals(user.getId()) && !Boolean.TRUE.equals(msg.getIsRead())) {
                msg.setIsRead(true);
            }
        }
        chatMessageRepository.saveAll(messages);

        return messages.stream()
                .map(m -> ChatMessageDto.fromEntity(m, user.getId()))
                .collect(Collectors.toList());
    }
}
