package com.example.mediconnect.AppoinmentService.controller;

import com.example.mediconnect.AppoinmentService.dto.AppointmentStatus;
import com.example.mediconnect.AppoinmentService.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/appointment")
@RestController
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;



    @PostMapping("/verifyPayment")
    public ResponseEntity<Map<String,Object>> verifyPayment(@RequestBody Map<String, Object> requestBody) {
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

}
