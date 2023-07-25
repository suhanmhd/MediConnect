package com.example.mediconnect.AppoinmentService.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id",referencedColumnName = "conversationId")
    private Conversation conversation;
    private UUID senderId;

    private String text;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;


    // Constructors, getters, and setters

}