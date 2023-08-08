package com.example.mediconnect.AppoinmentService.kafka;

import com.example.mediconnect.AppoinmentService.dto.AppointmentCanceldto;
import com.example.mediconnect.AppoinmentService.dto.AppointmentReq;
import com.example.mediconnect.AppoinmentService.service.AppointmentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Consumer {

    @Autowired
    private AppointmentService appointmentService;


    @KafkaListener(topics = "send_appointment_to_topic", groupId = "foo")
    public void appointmentBooking(String message) {

        ObjectMapper object = new ObjectMapper();
        AppointmentReq appointmentReq = null;
        try {
            appointmentReq = object.readValue(message,AppointmentReq.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
       appointmentService.appointmentBooking(appointmentReq);
    }

    @KafkaListener(topics = "send_view_appointment_topic", groupId = "foo")
    public void viewAppointments(String userId) {


        appointmentService.viewAppointments(userId);
    }




    @KafkaListener(topics = "send_cancel_appointment_topic", groupId = "foo")
    public void cancelAppointment(String message) {

        ObjectMapper object = new ObjectMapper();
        AppointmentCanceldto appointmentCanceldto = null;
        try {
            appointmentCanceldto = object.readValue(message, AppointmentCanceldto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        appointmentService.cancelAppointment(appointmentCanceldto);
    }




    @KafkaListener(topics = "get_all_appointment_request_to_doctor_topic", groupId = "foo")
    public void getAppointmentRequest(String docId) {

        appointmentService.getAppointmentRequest(docId);
    }


    @KafkaListener(topics = "get_todays_appointment_requests_to_doctor_topic", groupId = "foo")
    public void getTodaysAppointments(String docId) {

//        appointmentService.getTodaysAppointment(docId);
    }


    @KafkaListener(topics = "send_get_appointment_time_topic", groupId = "foo")
    public void getAppointmentTimes(String docId) {

        appointmentService.getAppointmentTimes(docId);
    }

}
