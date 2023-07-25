package com.example.mediconnect.AppoinmentService.service;

import com.example.mediconnect.AppoinmentService.dto.MessageDto;
import com.example.mediconnect.AppoinmentService.entity.Conversation;
import com.example.mediconnect.AppoinmentService.entity.Message;
import com.example.mediconnect.AppoinmentService.repository.ConversationRepository;
import com.example.mediconnect.AppoinmentService.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MessageService {

        @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private  ConversationRepository conversationRepository;
    public List<Conversation> getConversation(UUID userId) {


        return  conversationRepository.findDistinctByMembersContaining(userId.toString());

    }

    public void getMessages(UUID conversationId) {
        Conversation conversation=conversationRepository.getById(conversationId);
        System.out.println(conversation);
    }

    public Message sendMessages(MessageDto messageDto) {
        Conversation conversation=conversationRepository.getById(messageDto.getConversationId());
        Message message = new Message();
        message.setSenderId(messageDto.getSenderId());
        message.setConversation(conversation);
        message.setText(messageDto.getText());
        Message savedMessage=messageRepository.save(message);
        return savedMessage;

    }
//    @Autowired
//    private MessageRepository messageRepository;
//    public void saveMessage(MessageDto message) {
//        messageRepository.save(message);

//
//        public List<Message> getMessagesForConversation(Long conversationId) {
//            // Logic for retrieving messages for a conversation
//        }
//
//        public Message sendMessage(Message message) {
//            // Logic for sending a new message
//        }

//
//    }
}
