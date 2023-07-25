package com.example.mediconnect.AppoinmentService.controller;

import com.example.mediconnect.AppoinmentService.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//@RestController
//@RequestMapping("/api/messages")
//public class WebSocketController {
//    @Autowired
// private MessageService messageService;
//    @MessageMapping("/sendMessage")
//    @SendTo("/topic/chat")
//    public Message sendMessage(@Payload Message message) {
//        // Process and handle the incoming message
//        messageService.saveMessage(message);
//        return message;
//    }


//
//    @GetMapping("/{conversationId}")
//    public ResponseEntity<List<Message>> getMessages(@PathVariable Long conversationId) {
//        // Logic for retrieving messages for a conversation
//    }
//
//    @PostMapping
//    public ResponseEntity<Message> sendMessage(@RequestBody Message message) {
//        // Logic for sending a new message
//    }
////
//
////}
//
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Controller;
//
//@Controller
//public class WebSocketController {
//
//    @MessageMapping("/message")
//    @SendTo("/topic/messages")
//    public String handleMessage(String message) {
//        // Process the received message
//        return "Processed: " + message;
//    }
//}
