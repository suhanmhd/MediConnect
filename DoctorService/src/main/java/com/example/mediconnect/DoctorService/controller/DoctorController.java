package com.example.mediconnect.DoctorService.controller;

import com.example.mediconnect.DoctorService.dto.Appointment;
import com.example.mediconnect.DoctorService.dto.Doctor;
import com.example.mediconnect.DoctorService.dto.DoctorSlotDto;
import com.example.mediconnect.DoctorService.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public ResponseEntity<Map<String,Doctor>>GetDoctorById(@PathVariable("docId") UUID id){

        Doctor doctor = doctorService. GetDoctorById(id);


        Map<String,Doctor> response = new HashMap<>();
        response.put("doctorProfile",doctor);
        System.out.println(response);
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }


    @PostMapping("/addBookingSlot")
        public ResponseEntity<Map<String,DoctorSlotDto>>updateBookingSlot(@RequestBody DoctorSlotDto doctorSlotDto){
        System.out.println(doctorSlotDto);
        doctorService.updateBookingSlot(doctorSlotDto);
        System.out.println("hello world");
        System.out.println(doctorSlotDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @PostMapping("/addBookingSlot")
//    public ResponseEntity<Map<String,Object>>updateBookingSlot(@RequestBody Map<String,Object> res){
//        System.out.println(res);
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
    @GetMapping("/doctors/{doctorId}/time-slots")
    public ResponseEntity<List<String>> getTimeSlotsByDate(@PathVariable UUID doctorId,
                                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        DoctorSlotDto doctorSlotDto = new DoctorSlotDto();
        doctorSlotDto.setDoctor_id(doctorId);

        List<String> timeSlots = doctorService.getTimeSlotsByDate(doctorSlotDto, date);
        return ResponseEntity.ok(timeSlots);
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
        System.out.println(appointmentList);
        response.put("appointmentsDetails",appointmentList);
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }


    @GetMapping("/getTodaysAppointmentRequests/{docId}")
    public ResponseEntity<Map<String,List>>getTodaysAppointments(@PathVariable("docId") UUID id){
        Map<String,List> response = new HashMap<>();

        List<Appointment> appointmentList=null;
        while (appointmentList==null) {
          appointmentList=doctorService.getTodaysAppointments(id);
        }

        response.put("appointmentsDetails",appointmentList);
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }





    @GetMapping("/getDoctor/{docId}")
    public ResponseEntity<Map<String,Doctor>>getDoctorById(@PathVariable("docId") String docId){

        UUID id = UUID.fromString(docId);

        Doctor doctor = doctorService. GetDoctorById(id);


        Map<String,Doctor> response = new HashMap<>();
        response.put("doctorProfile",doctor);
        System.out.println(response);
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }



}
