//package com.example.mediconnect.AuthService.service;
//
//import com.example.mediconnect.AuthService.repository.UserCredentialRepository;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class UserDetailsMessage {
//
//    @Autowired
//   private UserCredentialRepository userCredentialRepository;
//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//
//
//    @KafkaListener(topics = "request_user_topic", groupId = "foo")
//    public void receiveProductMessage(String username) {
//
//        Optional<UserCredential> user = userCredentialRepository.findByName(username);
//        if (user.isPresent()) {
//            UserCredential userCredential = user.get();
//            int userId = userCredential.getId();
//
//            UserResponse userInfo = new UserResponse(userId, username);
//
//
//            System.out.println("hello world");
//            System.out.println(userInfo.getId());
//
//
//            ObjectMapper object = new ObjectMapper();
//
//
//            String userResponse = null;
//            try {
//                userResponse = object.writeValueAsString(userInfo);
//
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//            kafkaTemplate.send("user_response_topic",userResponse);
//
//        }
//
//    }
//
//
//
//
//
//}