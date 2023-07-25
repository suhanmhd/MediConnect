package com.example.mediconnect.AppoinmentService.repository;

import com.example.mediconnect.AppoinmentService.entity.Conversation;
import com.example.mediconnect.AppoinmentService.entity.Message;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {

//    List<Message> findByConversationId(UUID conversation);
    List<Message> findByConversation(Conversation conversation, Sort createdAt);
}