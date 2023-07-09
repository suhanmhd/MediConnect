package com.example.mediconnect.DoctorService.controller;

import com.example.mediconnect.DoctorService.dto.Appointment;
import com.example.mediconnect.DoctorService.dto.Doctor;
import com.example.mediconnect.DoctorService.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/doctor")
@RestController
public class DoctorController {
    @Autowired
    private DoctorService doctorService;
    @GetMapping("/doctors")
    public ResponseEntity<Void> getAllDoctors(){


        return  new ResponseEntity<>( HttpStatus.OK);
    }

    @GetMapping("/getDoctorProfile/{docId}")
    public ResponseEntity<Map<String,Doctor>> GetDoctorById(@PathVariable("docId") UUID id){

        Doctor doctor = doctorService. GetDoctorById(id);


        Map<String,Doctor> response = new HashMap<>();
        response.put("doctorProfile",doctor);
        System.out.println(response);
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }



    @PostMapping("/updateDoctorProfile")
    public ResponseEntity<Map<String,Doctor>> updateDoctor(@RequestBody Doctor doctor) {
        System.out.println(doctor);

        Doctor updatedDoctor = doctorService.updateDoctor(doctor);


        Map<String,Doctor> response = new HashMap<>();
        response.put("doctorProfile",updatedDoctor);
        System.out.println(response);
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }



    @GetMapping("/getAppointmentRequests/{docId}")
    public ResponseEntity<Map<String,List>>getAppointmentRequest(@PathVariable("docId") UUID id){
        Map<String,List> response = new HashMap<>();
        List<Appointment> appointmentList=doctorService.getAppointmentRequest(id);
        response.put("appointmentsDetails",appointmentList);
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }

}
