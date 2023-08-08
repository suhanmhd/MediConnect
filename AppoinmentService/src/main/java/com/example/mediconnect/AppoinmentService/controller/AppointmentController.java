package com.example.mediconnect.AppoinmentService.controller;

import com.example.mediconnect.AppoinmentService.dto.AppointmentStatus;
import com.example.mediconnect.AppoinmentService.dto.DoctorInfo;
import com.example.mediconnect.AppoinmentService.entity.Appointment;
import com.example.mediconnect.AppoinmentService.entity.Conversation;
import com.example.mediconnect.AppoinmentService.entity.Message;
import com.example.mediconnect.AppoinmentService.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/appointment")
@RestController
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;



    @PostMapping("/verifyPayment")
    public ResponseEntity<Map<String,Object>> verifyPayment(@RequestBody Map<String, Object> requestBody) {
        System.out.println("ppppp"+requestBody);

        String res=appointmentService.verifyPayment(requestBody);
        Map<String,Object>response =new HashMap<>();
        if(res=="success"){
            response.put("status",true);
            response.put("message", "Payment Successfull !") ;
            return  new ResponseEntity<>(response,HttpStatus.OK);

        }else {
            response.put("status",false);
            response.put("message", "Payment failed !") ;
            return  new ResponseEntity<>(response,HttpStatus.OK);
        }





        }



    @PostMapping("/update-appointment-status")
    public ResponseEntity<Map<String,String>> updateAppointmentStatus(@RequestBody AppointmentStatus appointmentStatus) {
        String res=appointmentService.updateAppointmentStatus(appointmentStatus);
        Map<String,String>response =new HashMap<>();
        response.put("message",res);
        System.out.println(appointmentStatus);
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/getBookedDoctors/{userId}")
    public ResponseEntity<Map<String,List<DoctorInfo>>>getBookedDoctors(@PathVariable("userId") UUID id) {
        System.out.println(id);
           List<DoctorInfo> doctorInfo=appointmentService.getBookedDoctors(id);
        Map<String,List<DoctorInfo>>response =new HashMap<>();
        response.put("bookedDoctors",doctorInfo);
        System.out.println(doctorInfo);
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }


    @GetMapping("/getDoctor/{userId}")
    public ResponseEntity<Map<String,DoctorInfo>>getDoctors(@PathVariable("userId") UUID id) {
        System.out.println(id);
        DoctorInfo doctorInfo=appointmentService.getDoctor(id);
        Map<String,DoctorInfo>response =new HashMap<>();
        response.put("bookedDoctors",doctorInfo);
        System.out.println(doctorInfo);
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }


    @GetMapping("/getAppointmentRequests/{docId}")
    public ResponseEntity<Map<String,List>>getAppointmentRequest(@PathVariable("docId") UUID id){
        Map<String,List> response = new HashMap<>();
        List<Appointment> appointmentList=appointmentService.getAppointmentRequest(id.toString());
        System.out.println(appointmentList);
        response.put("appointmentsDetails",appointmentList);
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }


    @GetMapping("/getTodaysAppointmentRequests/{docId}")
    public ResponseEntity<Map<String,List>>getTodaysAppointments(@PathVariable("docId") UUID id){
        Map<String,List> response = new HashMap<>();

        List<Appointment> appointmentList=appointmentService.getTodaysAppointments(id.toString());

        response.put("appointmentsDetails",appointmentList);
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }



}
