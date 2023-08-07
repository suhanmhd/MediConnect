package com.example.mediconnect.UserService.kafka.doctor;

import com.example.mediconnect.UserService.dto.ApproveRequest;

import com.example.mediconnect.UserService.dto.DoctorId;
import com.example.mediconnect.UserService.entity.doctor.DoctorCredentials;
import com.example.mediconnect.UserService.service.DoctorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class DoctorConsumer {
    @Autowired
    private DoctorService doctorService;

    @KafkaListener(topics = "doctor-details-data-topic", groupId = "foo")
    public void doctorDetails(String message) {

        ObjectMapper object = new ObjectMapper();
        DoctorCredentials doctorCredentials = null;
        try {
            doctorCredentials = object.readValue(message, DoctorCredentials.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        doctorService.saveDoctor(doctorCredentials);
    }

    @KafkaListener(topics = "get-all-doctors-to-admin-topic", groupId = "foo")
    public void getAllDoctors(String message) {


        doctorService.getAllDoctors();
    }


    @KafkaListener(topics = "get-all-pendingApprovals-to-admin-topic", groupId = "foo")
    public void pendingApprovals(String message) {


        doctorService.getAllPendingApprovals();
    }





    @KafkaListener(topics = "set_approve_doctor_to_admin_topic", groupId = "foo")
    public void ApproveDoctor(String message) {

        ObjectMapper object = new ObjectMapper();
        ApproveRequest request = null;
        try {
            request = object.readValue(message, ApproveRequest.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        doctorService.approveDoctor(request);
    }

    @KafkaListener(topics = "get_doctor_by_department_name", groupId = "foo")
    public void getDoctorByDepartment(String specialization) {


        doctorService.getDoctorByDepartment(specialization);
    }



    @KafkaListener(topics = "get_doctor_by_id_topic", groupId = "foo")

    public void getDoctorById(String message) {

        ObjectMapper object = new ObjectMapper();
     DoctorId doctorId = null;
        try {
            doctorId = object.readValue(message, DoctorId.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        doctorService.getDoctorById(doctorId);
    }


    @KafkaListener(topics = "send_block_to_doctor_topic", groupId = "foo")
    @Transactional

    public void blockDoctorById(String message) {

        ObjectMapper object = new ObjectMapper();
        DoctorId doctorId = null;
        try {
            doctorId = object.readValue(message, DoctorId.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        doctorService.blockDoctorById(doctorId);


    }


    @KafkaListener(topics = "send_unblock_to_doctor_topic", groupId = "foo")
    @Transactional

    public void UnblockDoctorById(String message) {
        System.out.println("sldf");

        ObjectMapper object = new ObjectMapper();
        DoctorId doctorId = null;
        try {
            doctorId = object.readValue(message, DoctorId.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        doctorService.UnblockDoctorById(doctorId);


    }




}