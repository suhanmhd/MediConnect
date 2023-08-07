package com.example.mediconnect.UserService.kafka.doctor;


import com.example.mediconnect.UserService.dto.Doctor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class DoctorProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${doctor.topic}")
    private String topicName;
    @Value("${doctor.bydepartment.topic}")
    private String doctorByDtopicName;


    @Value("${doctor.by.id.topic}")
    private String doctorByIdtopicName;

    @Value("${doctor.pendingapprvl.topic}")
    private String PendingRes_topicName;

    @Value("${doctor.block.res.topic}")
    private String  doctorBlktopicName;

    @Value("${doctor.unblock.res.topic}")
    private String  doctorUnBlktopicName;
    @Value("${doctor.get.all.appointment.topic}")
   private String getAppointmentRequestTopicName;

    @Value("${doctor.get.today.appointments.topic}")
    private  String getTodaysAppointmentTopicName;



    public void sendAllDoctors(List<Doctor> doctorResponse) {

        ObjectMapper om = new ObjectMapper();


        String message = null;
        try {
            message = om.writeValueAsString(doctorResponse);
        } catch (
                JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(message);

        kafkaTemplate.send(topicName, message);
    }

    public void sendAllPendingApprovals(List<Doctor> pendingApprovals) {

        ObjectMapper om = new ObjectMapper();


        String message = null;
        try {
            message = om.writeValueAsString(pendingApprovals);
        } catch (
                JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(message);

        kafkaTemplate.send(PendingRes_topicName, message);
    }


    public void sendDoctorByDepartment(List<Doctor> doctorResponse) {

        ObjectMapper om = new ObjectMapper();


        String message = null;
        try {
            message = om.writeValueAsString(doctorResponse);
        } catch (
                JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(message);

        kafkaTemplate.send(doctorByDtopicName, message);

    }

    public void sendDoctorById(Doctor doctors) {

        ObjectMapper om = new ObjectMapper();


        String message = null;
        try {
            message = om.writeValueAsString(doctors);
        } catch (
                JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(message);


        kafkaTemplate.send(doctorByIdtopicName, message);


    }

    public void sendblockDoctorRes(Doctor doctor) {
        System.out.println(doctor+"ffffffff");

        ObjectMapper om = new ObjectMapper();


        String message = null;
        try {
            message = om.writeValueAsString(doctor);
        } catch (
                JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(message);
        kafkaTemplate.send(doctorBlktopicName, message);

    }

    public void sendUnblockDoctorRes(Doctor doctordetails) {

        ObjectMapper om = new ObjectMapper();


        String message = null;
        try {
            message = om.writeValueAsString(doctordetails);
        } catch (
                JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(message);

        kafkaTemplate.send(doctorUnBlktopicName, message);



    }

    public void getAppointmentRequest(UUID id) {
        String message= id.toString();
        kafkaTemplate.send(getAppointmentRequestTopicName, message);
    }

    public void getTodaysAppointments(UUID id) {
        String message= id.toString();
        kafkaTemplate.send(getTodaysAppointmentTopicName, message);
    }
}
