package com.example.mediconnect.AuthService.kafka;

import com.example.mediconnect.AuthService.dto.DoctorDetails;
import com.example.mediconnect.AuthService.dto.UserData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Value("${doctor.topic.name}")
    private String topicName;
    @Value("${user.topic.name}")
    private String usertopicName;

    public void sendDoctorDetails(DoctorDetails doctorDetails) {

        ObjectMapper om = new ObjectMapper();


        String message = null;
        try {
            message = om.writeValueAsString(doctorDetails);
        } catch (
                JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(message);

        kafkaTemplate.send(topicName, message);
    }


    public void sendUserData(UserData userData) {

        ObjectMapper om = new ObjectMapper();


        String message = null;
        try {
            message = om.writeValueAsString(userData);
        } catch (
                JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(message);

        kafkaTemplate.send(usertopicName, message);
    }
}