package com.example.mediconnect.UserService.kafka;

import com.example.mediconnect.UserService.dto.Appointment;
import com.example.mediconnect.UserService.dto.Doctor;
import com.example.mediconnect.UserService.dto.UserId;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

@Component
public class BookingConsumer {

    private Map<String, Object> bookingResponse;


    private String cancelAppointemnt;

    private List<Appointment> viewAppointments;

    private List<Appointment> viewAppointmentTimes;
    private final Object lock = new Object();








    @KafkaListener(topics = "appointment_send_booking_res_to_user_topic", groupId = "foo")
    public void bookingResponse(String message){
        System.out.println("hellloworld");
        System.out.println(message);
        Gson gsons = new Gson();
        TypeToken<Map<String, Object>> typeToken = new TypeToken<Map<String, Object>>() {};
      Map<String, Object> map = gsons.fromJson(message, typeToken.getType());
        System.out.println(map+"+++");


        System.out.println("))))"+map);

        this.bookingResponse=map;


    }
    public Map<String, Object> getReceivedBookingRes() {
        bookingResponse=null;
        System.out.println("hl");

//        synchronized (lock) {
//            while (bookingResponse == null) {
//                try {
//                    lock.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

        while (bookingResponse==null){
            System.out.println("(((("+bookingResponse);
        }
        System.out.println("(((("+bookingResponse);
        return bookingResponse;


    }





    @KafkaListener(topics = "appointment_send_all_view_appointment_topic", groupId = "foo")
    public void getViewAppointment(String message) {


        ObjectMapper object = new ObjectMapper();

        Appointment[] appointments = null;
        List<Appointment> appointmentList  = null;
        try {
            appointments = object.readValue(message,  Appointment[].class);
            appointmentList = Arrays.asList(appointments);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        this.viewAppointments =appointmentList;

    }

    public List<Appointment> getAllReceivedAppointmets() {


        synchronized (lock) {
            while (viewAppointments == null) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return viewAppointments;
    }




    @KafkaListener(topics = "cancel_appointment_send_topic", groupId = "foo")

    public void cancelAppointmentRes(String message) {


               this.cancelAppointemnt=message;
           }


    public String getCancelAppointemntRes(){

      synchronized (lock) {
          while (cancelAppointemnt == null) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    return cancelAppointemnt;
   }






    @KafkaListener(topics = "appointment_send_all_appointment_times_of_doctor_topic", groupId = "foo")
    public void getViewAppointmentTimes(String message) {





        ObjectMapper object = new ObjectMapper();

        Appointment[] appointments = null;
        List<Appointment> appointmentList  = null;
        try {
            appointments = object.readValue(message,  Appointment[].class);
            appointmentList = Arrays.asList(appointments);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        this.viewAppointmentTimes =appointmentList;

    }

    public List<Appointment> getAllReceivedAppointmetTimes() {


        synchronized (lock) {
            while (viewAppointmentTimes == null) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        return viewAppointmentTimes;
    }




}
