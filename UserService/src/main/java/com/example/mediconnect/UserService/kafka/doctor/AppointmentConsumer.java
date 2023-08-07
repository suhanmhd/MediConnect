package com.example.mediconnect.UserService.kafka.doctor;


import com.example.mediconnect.UserService.dto.Appointment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class AppointmentConsumer {

    private List<Appointment> getAppointments;
    private List<Appointment>  getTodaysAppointments;
    private final Object lock = new Object();

    @KafkaListener(topics = "appointment_send_all_appointment_req_to_doctor_topic", groupId = "foo")
    public void getAllappointments(String message) {


        ObjectMapper object = new ObjectMapper();

        Appointment[] appointments = null;
        List<Appointment> appointmentList  = null;
        try {
            appointments = object.readValue(message,  Appointment[].class);
            appointmentList = Arrays.asList(appointments);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        this.getAppointments =appointmentList;

    }

    public List<Appointment> getAllAppointmetsToDoctor() {

        while(getAppointments==null){

           }

//        synchronized (lock) {
//            while (getAppointments == null) {
//                try {
//                    lock.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        return getAppointments;
    }






    @KafkaListener(topics = "appointment_send_todays_appointment_req_to_doctor_topic", groupId = "foo")
    public void getTodaysappointments(String message) {


        ObjectMapper object = new ObjectMapper();

        Appointment[] appointments = null;
        List<Appointment> appointmentList  = null;
        try {
            appointments = object.readValue(message,  Appointment[].class);
            appointmentList = Arrays.asList(appointments);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        this.getTodaysAppointments =appointmentList;

    }

    public List<Appointment> getTodaysAppointmentsToDoctor() {
        while (getTodaysAppointments==null){

        }

//        synchronized (lock) {
//            while (getTodaysAppointments == null) {
//                try {
//                    lock.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        return getTodaysAppointments;
    }

}
