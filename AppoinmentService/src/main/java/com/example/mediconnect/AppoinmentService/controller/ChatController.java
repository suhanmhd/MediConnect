package com.example.mediconnect.AppoinmentService.controller;//package com.example.mediconnect.AppoinmentService.controller;
//
//import com.example.mediconnect.AppoinmentService.dto.AppointmentStatus;
//import com.example.mediconnect.AppoinmentService.dto.DoctorInfo;
//import com.example.mediconnect.AppoinmentService.dto.MessageDto;
//import com.example.mediconnect.AppoinmentService.entity.Conversation;
//import com.example.mediconnect.AppoinmentService.entity.Message;
//import com.example.mediconnect.AppoinmentService.repository.ConversationRepository;
//import com.example.mediconnect.AppoinmentService.repository.MessageRepository;
//import com.example.mediconnect.AppoinmentService.service.AppointmentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/appointment")
//public class ChatController {
//    @Autowired
//    private MessageRepository messageRepository;
//    @Autowired
//    private ConversationRepository conversationRepository;
//    @Autowired
//    private AppointmentService appointmentService;
//
//
//
//
//    @GetMapping("/getBookedDoctors/{userId}")
//    public ResponseEntity<Map<String,List<DoctorInfo>>>getBookedDoctors(@PathVariable("userId") UUID id) {
//        System.out.println(id);
//           List<DoctorInfo> doctorInfo=appointmentService.getBookedDoctors(id);
//        Map<String,List<DoctorInfo>>response =new HashMap<>();
//        response.put("bookedDoctors",doctorInfo);
//        System.out.println(doctorInfo);
//        return  new ResponseEntity<>(response,HttpStatus.OK);
//    }
//
//    @PostMapping("/messages")
//    public ResponseEntity<?> newMessage(@RequestBody MessageDto messageReq) {
//        Conversation conversations=conversationRepository.getById(messageReq.getConversationId());
//        System.out.println(conversations);
//
//
//
////        message.setText(messageRequest.getText());
//        Message  messages = new Message()
//                .builder()
//                .senderId(messageReq.getSenderId())
//                .conversation(conversations)
//                .text(messageReq.getText())
//                .build();
////        Conversation conversation =conversationRepository.getById(messageReq.getConversationId());
//
//        System.out.println(conversations);
//
//
//
//        System.out.println(messages);
//        System.out.println("Inside backend");
//
////        try {
//            Message savedMessage = messageRepository.save(messages);
//            return ResponseEntity.ok().body(savedMessage);
////        } catch (Exception e) {
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
////        }
//    }
//
//
//
//    @GetMapping("/conversation/{conversationId}")
//    public ResponseEntity<?> getMessages(@PathVariable("conversationId") UUID conversationId) {
//        System.out.println(conversationId);
//
//        Conversation conversation=conversationRepository.getById(conversationId);
//
//        System.out.println(conversation);
////
//        try {
//          List<Message>   message = messageRepository.findByConversation(conversation);
//
//           return ResponseEntity.ok().body(message);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }
//
//
//
//
//    }
//
//


import com.example.mediconnect.AppoinmentService.dto.MessageRequest;
import com.example.mediconnect.AppoinmentService.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/message")
    @SendTo("/group/public")
    public MessageRequest sendMessage(@Payload MessageRequest message) {
        System.out.println("hekk");
        System.out.println(message);


        // Broadcast the message to the conversation topic
        messagingTemplate.convertAndSend("/group/"+message.getReceiverId(), message);
        return message;
    }

//    @MessageMapping("/message")
//    @SendTo("/chatroom/public")
//    public Message receiveMessage(@Payload Message message){
//        return message;
//    }
//
//    @MessageMapping("/private-message")
//    public Message recMessage(@Payload Message message){
//        System.out.println(message.toString());
//       messagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);
//        System.out.println(message.toString());
//        return message;
//    }
}
