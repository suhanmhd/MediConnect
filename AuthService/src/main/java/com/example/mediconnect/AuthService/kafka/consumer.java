package com.example.mediconnect.AuthService.kafka;

import com.example.mediconnect.AuthService.dto.ApproveRequest;
import com.example.mediconnect.AuthService.dto.DoctorId;
import com.example.mediconnect.AuthService.dto.UserId;
import com.example.mediconnect.AuthService.service.AuthenticationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class consumer {
    @Autowired
    private AuthenticationService authenticationService;


//    @KafkaListener(topics = "get-all-pendingApprovals-to-admin-topic", groupId = "foo")
//    public void getAllDoctors(String message) {
//
//
//        authenticationService.getAllPendingApprovals();
//    }


    @KafkaListener(topics = "set_approve_to_admin_topic", groupId = "foo")
    public void ApproveDoctor(String message) {

        ObjectMapper object = new ObjectMapper();
        ApproveRequest request = null;
        try {
            request = object.readValue(message, ApproveRequest.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        authenticationService.approveDoctor(request);
    }











    @KafkaListener(topics = "send_block_to_doctor_auth_topic", groupId = "foo")
    @Transactional

    public void blockDoctorById(String message) {
        System.out.println("slkdjflskfjslkf");

        ObjectMapper object = new ObjectMapper();
        DoctorId doctorId = null;
        try {
            doctorId = object.readValue(message, DoctorId.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

       authenticationService.blockDoctorById(doctorId);


    }


    @KafkaListener(topics = "send_unblock_to_doctor_auth_topic", groupId = "foo")
    @Transactional

    public void UnblockDoctorById(String message) {

        ObjectMapper object = new ObjectMapper();
        DoctorId doctorId = null;
        try {
            doctorId = object.readValue(message, DoctorId.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        authenticationService.UnblockDoctorById(doctorId);


    }


    @KafkaListener(topics = "send_block_to_user_auth_topic", groupId = "foo")
    @Transactional

    public void blockUserById(String message) {
        System.out.println("slkdjflskfjslkf");

        ObjectMapper object = new ObjectMapper();
       UserId userId = null;
        try {
          userId = object.readValue(message, UserId.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        authenticationService.blockUserById(userId);


    }


    @KafkaListener(topics = "send_Unblock_to_user_auth_topic", groupId = "foo")
    @Transactional

    public void UnblockUserById(String message) {
        System.out.println("slkdjflskfjslkf");

        ObjectMapper object = new ObjectMapper();
        UserId userId = null;
        try {
            userId = object.readValue(message, UserId.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        authenticationService.UnblockUserById(userId);


    }








}
