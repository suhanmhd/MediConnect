package com.example.mediconnect.UserService.kafka.adminkafka;


import com.example.mediconnect.UserService.dto.DepartmentResponse;
import com.example.mediconnect.UserService.dto.DoctorId;
import com.example.mediconnect.UserService.dto.UserId;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplates;
    @Autowired
    private KafkaTemplate<String, String> kafka_Template_Approve;
    @Autowired
    private KafkaTemplate<String, String> kafka_Template;
    @Value("${admin.topic.name}")
    private String topicName;
    @Value("${admin.pendingapproval.topic}")
    private String  topicName_PendingApproval;

    @Value("${admin.approve.topic}")
    private String  approveTopic;
    @Value("${admin.approve.dctr}")
    private String  approveDctr;

    @Value("${admin.send.department}")
    private String  departMentTopic;

    @Value("${admin.send.blockdr}")
    private String DrblockTopicName;

    @Value("${admin.send.block.user}")
    private String UserblockTopicName;



    @Value("${admin.send.UnBlock.user}")
    private String UserUnblockTopicName;

    @Value("${admin.send.unblockdr}")
    private String DrUnblockTopicName;



    @Value("${admin.send.getAll.users}")
    private String getUsersTopicName;




    @Value("${admin.send.blockdr.auth}")
    private String DrblockAuthTopicName;
    @Value("${admin.send.unblockdr.auth}")
    private String DrUnblockAuthTopicName;

    @Value("${admin.send.block.user.auth}")
    private String UserblockAuthTopicName;

    @Value("${admin.send.UnBlock.user.auth}")
    private String UserUnblockAuthTopicName;





    public void getAllDoctors() {
        String message ="get all doctors";

        kafka_Template.send(topicName, message);
    }

    public void getpendingApprovals() {
        String message ="get all pending approvals";
        kafka_Template.send(topicName_PendingApproval, message);
    }


    public void sendAllDepartmentsToUser(List<DepartmentResponse> departmentResponse) {
        ObjectMapper om = new ObjectMapper();


        String message = null;
        try {
            message = om.writeValueAsString(departmentResponse);
        } catch (
                JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(message);

        kafkaTemplate.send(departMentTopic, message);

    }
    public void sendBlockDoctor(DoctorId doctorId) {

        ObjectMapper om = new ObjectMapper();


        String message = null;
        try {
            message = om.writeValueAsString(doctorId);
        } catch (
                JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(message);


        kafkaTemplate.send(DrblockAuthTopicName, message);


    }


    public void sendUnBlockDoctor(DoctorId doctorId) {
        ObjectMapper om = new ObjectMapper();


        String message = null;
        try {
            message = om.writeValueAsString(doctorId);
        } catch (
                JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(message);
        kafkaTemplate.send(DrUnblockAuthTopicName, message);
        kafkaTemplate.send(DrUnblockTopicName, message);

    }

    public void getAllUsers() {
        String message ="get all users";

        kafka_Template.send(getUsersTopicName, message);
    }

    public void sendBlockUser(UserId userId) {

        ObjectMapper om = new ObjectMapper();


        String message = null;
        try {
            message = om.writeValueAsString(userId);
        } catch (
                JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(message);


        kafkaTemplate.send(UserblockAuthTopicName, message);

    }

    public void sendUnBlockUser(UserId userId) {
        ObjectMapper om = new ObjectMapper();


        String message = null;
        try {
            message = om.writeValueAsString(userId);
        } catch (
                JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(message);


        kafkaTemplate.send(UserUnblockAuthTopicName, message);

    }
}