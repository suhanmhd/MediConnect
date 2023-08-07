package com.example.mediconnect.UserService.kafka;

import com.example.mediconnect.UserService.dto.UserId;
import com.example.mediconnect.UserService.entity.user.UserDetails;
import com.example.mediconnect.UserService.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class Consumer {

    @Autowired
    private UserService userService;




    @KafkaListener(topics ="user-details-data-topic", groupId = "foo")
    public void userDetails(String message) {

        ObjectMapper object = new ObjectMapper();
        UserDetails userDetails = null;
        try {
           userDetails = object.readValue(message, UserDetails.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        userService.saveUser(userDetails);
    }



    @KafkaListener(topics = "get_all_users_to_admin_topic", groupId = "foo")
    public void getAllUsers(String message) {


        userService.getAllUsers();
    }



    @KafkaListener(topics = "send_block_to_user_topic", groupId = "foo")

    @Transactional
    public void blockUserById(String message) {
        System.out.println(message);

        ObjectMapper object = new ObjectMapper();
       UserId userId = null;
        try {
            userId = object.readValue(message, UserId.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        userService.blockUserById(userId);
    }




    @KafkaListener(topics = "send_Unblock_to_user_topic", groupId = "foo")

    @Transactional
    public void UnblockUserById(String message) {
        System.out.println(message);

        ObjectMapper object = new ObjectMapper();
        UserId userId = null;
        try {
            userId = object.readValue(message, UserId.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        userService.UnblockUserById(userId);
    }




}


