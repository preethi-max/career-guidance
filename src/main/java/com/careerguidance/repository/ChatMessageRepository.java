package com.careerguidance.repository;

import com.careerguidance.entity.ChatMessage;
import com.careerguidance.entity.MentorConnectionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByConnectionRequestOrderBySentAtAsc(MentorConnectionRequest connectionRequest);
    List<ChatMessage> findByConnectionRequestIdOrderBySentAtAsc(Long connectionRequestId);

    @Query("SELECT m FROM ChatMessage m WHERE m.connectionRequest.id = :requestId AND m.sentAt > :afterTime ORDER BY m.sentAt ASC")
    List<ChatMessage> findNewMessages(@Param("requestId") Long requestId, @Param("afterTime") LocalDateTime afterTime);

    long countByRecipientIdAndIsReadFalse(Long recipientId);
}
