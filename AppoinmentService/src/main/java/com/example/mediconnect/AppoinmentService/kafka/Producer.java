package com.example.mediconnect.AppoinmentService.kafka;

import com.example.mediconnect.AppoinmentService.entity.Appointment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class Producer {
    @Value("${appointment.send.book.res}")
    private String bookingResTopicName;

    @Value("${view.all.appointment.res}")
    private String appointmentViewTopicName;


    @Value("${send.cancel.appointment.res}")
    private  String appointmentCancelResTopicName;

    @Value("${send.appointment.req.doctor}")
    private String appointmentsToDoctorTopicName;


    @Value("${send.appointment.times.of.doctor}")
    private String appointmentsTimesofToDoctorTopicName;
    @Value("${send.todays.appointments.to.doctor}")
    private String todaysAppointmentsToDoctorTopicName;


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendBookingRes(Map<String, Object> response) {
//        Gson gson = new Gson();
//        String message = gson.toJson(response);
//        System.out.println(message+"____");
//
//
        String message =response.toString();



//
        System.out.println(message);
        kafkaTemplate.send(bookingResTopicName, message);

    }

    public void getAppointments(List<Appointment> appointments) {

        ObjectMapper om = new ObjectMapper();


        String message = null;
        try {
            message = om.writeValueAsString(appointments);
        } catch (
                JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(message);
        kafkaTemplate.send(appointmentViewTopicName, message);
    }

    public void cancelAppointmentRes() {
    String message= "Appointment cancelled successfully";
        kafkaTemplate.send(appointmentCancelResTopicName, message);

    }

    public void getAppointmentRequests(List<Appointment> appointmentRequests) {
        ObjectMapper om = new ObjectMapper();


        String message = null;
        try {
            message = om.writeValueAsString(appointmentRequests);
        } catch (
                JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(message);
        kafkaTemplate.send(appointmentsToDoctorTopicName, message);
    }

    public void getAppointmentTime(List<Appointment> appointmentTimes) {
        ObjectMapper om = new ObjectMapper();


        String message = null;
        try {
            message = om.writeValueAsString(appointmentTimes);
        } catch (
                JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(message);
        kafkaTemplate.send(appointmentsTimesofToDoctorTopicName, message);

    }

    public void getTodaysAppointments(List<Appointment> todaysAppointments) {
        ObjectMapper om = new ObjectMapper();


        String message = null;
        try {
            message = om.writeValueAsString(todaysAppointments);
        } catch (
                JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(message);
        kafkaTemplate.send(todaysAppointmentsToDoctorTopicName, message);

    }
}
