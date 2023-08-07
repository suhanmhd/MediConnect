package com.example.mediconnect.AdminService.kafka;

import com.example.mediconnect.AdminService.config.ResponseHolder;

import com.example.mediconnect.AdminService.dto.Doctor;
import com.example.mediconnect.AdminService.dto.Userdto;
import com.example.mediconnect.AdminService.service.AdminService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class Consumer {

    private List<Doctor> receivedDoctors;
    private List<Userdto> receivedUsers;

    private List<Doctor> receivedPendingApprovals;

    private Doctor receivedBlockedDoctor;
    private Doctor receivedUnBlockedDoctor;

    private Userdto receivedBlockedUser;
    private List<Userdto> receivedUnBlockedUser;





    @KafkaListener(topics = "send-all-doctors-topic", groupId = "foo")
    public void getDoctorDetails(String message) {

        ObjectMapper object = new ObjectMapper();

        Doctor[] doctors = null;
        List<Doctor> doctorList = null;
        try {
            doctors = object.readValue(message, Doctor[].class);
            doctorList = Arrays.asList(doctors);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        this.receivedDoctors = doctorList;
    }

    public List<Doctor> getReceivedDoctors() {

        return  receivedDoctors;
    }









    @KafkaListener(topics = "send-all-pendingApproval-doctors-topic", groupId = "foo")
    public void getAllPendingApprovals(String message) {

        ObjectMapper object = new ObjectMapper();

        Doctor[] doctors = null;
        List<Doctor> pendiApprovalList  = null;
        try {
            doctors = object.readValue(message, Doctor[].class);
            pendiApprovalList = Arrays.asList(doctors);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        this.receivedPendingApprovals =  pendiApprovalList;
    }

    public List<Doctor> getReceivedPendingApprovals() {

        return receivedPendingApprovals;
    }






    @KafkaListener(topics = "send_all_doctor_by_blocked_id_topic", groupId = "foo")
    public void getBlockedDoctor(String message) {
        System.out.println("message for"+message);


        ObjectMapper object = new ObjectMapper();

        Doctor doctors = null;

        try {
            doctors = object.readValue(message, Doctor.class);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        this.receivedBlockedDoctor =  doctors;
    }

    public Doctor getReceivedBlockedDoctor() {

        return receivedBlockedDoctor;
    }


    @KafkaListener(topics = "send_all_doctor_by_unblocked_id_topic", groupId = "foo")
    public void getUnBlockedDoctor(String message) {
        System.out.println("message for"+message);

        ObjectMapper object = new ObjectMapper();

        Doctor doctors = null;

        try {
            doctors = object.readValue(message, Doctor.class);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        this.receivedUnBlockedDoctor =  doctors;
    }

    public Doctor getReceivedUnBlockedDoctor() {

        return receivedUnBlockedDoctor;
    }






    @KafkaListener(topics = "send_all_users_to_admin", groupId = "foo")
    public void getUsers(String message) {
        System.out.println("{user message catched by kafka }"+message);

        ObjectMapper object = new ObjectMapper();

        Userdto[] users = null;
        List<Userdto> userList = null;
        try {
            users = object.readValue(message, Userdto[].class);
            userList = Arrays.asList(users);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        this.receivedUsers=userList;

    }

    public List<Userdto> getReceivedUsers() {
        return receivedUsers;
    }





    @KafkaListener(topics = "send_all_user_by_blocked_id_topic", groupId = "foo")
    public void getBlockedUser(String message) {
        System.out.println("kafka blocked user"+message);

        ObjectMapper object = new ObjectMapper();

       Userdto userdto = null;

        try {
            userdto = object.readValue(message, Userdto.class);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        this.receivedBlockedUser =  userdto;
    }

    public Userdto getReceivedBlockedUser() {


        return receivedBlockedUser;
    }



    @KafkaListener(topics = "send_all_user_by_un_blocked_id_topic", groupId = "foo")
    public void getUnBlockedUser(String message) {
        System.out.println("kafka unblocked res"+message);

//        ObjectMapper object = new ObjectMapper();
//
//        Userdto userdto = null;
//
//        try {
//            userdto = object.readValue(message, Userdto.class);
//
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        this.receivedUnBlockedUser =  userdto;
//    }

        ObjectMapper object = new ObjectMapper();

        Userdto[] users = null;
        List<Userdto> userList = null;
        try {
            users = object.readValue(message, Userdto[].class);
            userList = Arrays.asList(users);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        this.receivedUnBlockedUser = userList;
    }
    public List<Userdto> getReceivedUnBlockedUser() {

        return receivedUnBlockedUser;
    }


}

