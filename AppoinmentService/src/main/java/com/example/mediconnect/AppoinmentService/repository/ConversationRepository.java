package com.example.mediconnect.AppoinmentService.repository;

import com.example.mediconnect.AppoinmentService.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
@Repository
public interface ConversationRepository extends JpaRepository<Conversation, UUID> {
    @Query("SELECT c FROM Conversation c WHERE c.members IN :members")
Conversation findConversationByMembers(@Param("members") ArrayList<String> members);

    @Query("SELECT DISTINCT c FROM Conversation c JOIN c.members m WHERE m = :userId")
    List<Conversation> findDistinctByMembersContaining(@Param("userId") String userId);

//    List<Conversation> findByMembersContaining(String userId);
}
//    @Query("SELECT c FROM Conversation c WHERE :senderId MEMBER OF c.members AND :receiverId MEMBER OF c.members")
//    List<Conversation> findConversationByMembers(List<String> members);