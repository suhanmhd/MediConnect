package com.example.mediconnect.UserService.controller.userController;

import com.example.mediconnect.UserService.dto.*;
import com.example.mediconnect.UserService.dto.user.SlotResponseListDTO;
import com.example.mediconnect.UserService.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getdepartments")
    public ResponseEntity<Map<String, List>> getDepartments() {
        List<DepartmentResponse> departmentResponses = userService.getAllDepartments();
        System.out.println(departmentResponses.toString());
        Map<String, List> response = new HashMap<>();
        response.put("categoryDetails", departmentResponses);


        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping("/getAllDoctors")
    public ResponseEntity<Map<String,List>>getAllDoctorsToUser(@RequestParam("query") String keyword){
        List<Doctor> doctorList;
        if(keyword!=null){
            doctorList = userService.searchDoctorsByName(keyword);
        }
        else{
         doctorList = userService.getAllDoctorsToUser();
        }

        Map<String, List> response = new HashMap<>();
        response.put("doctorDetails", doctorList);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }


    @GetMapping ("/getSingleDoctor/{docId}")
    public  ResponseEntity<Map<String, Doctor>> getDoctorById(@PathVariable("docId") UUID id){
        Doctor doctor=userService.getDoctorById(id);
        Map<String, Doctor> response = new HashMap<>();
        response.put("doctorDetails", doctor);
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }


    @GetMapping("/search")
    public ResponseEntity<Map<String,List>>searchDoctorsByName(@RequestParam("query") String keyword) {


        List<Doctor> doctorList = userService.searchDoctorsByName(keyword);
        Map<String, List> response = new HashMap<>();
        response.put("doctorDetails", doctorList);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/getAvailableSlot/{doctorId}")
    public ResponseEntity <Map<String, SlotResponseListDTO>>getAvailableSlot(@PathVariable("doctorId") UUID doctorId) {
        SlotResponseListDTO availableSlots = userService.getAvailableSlots(doctorId);
        Map<String,SlotResponseListDTO>response = new HashMap<>();
        response.put("slot",availableSlots);
        return  new ResponseEntity<>(response,HttpStatus.OK);


    }
    @PostMapping("/updateSlot/{slotId}/status")
    public ResponseEntity<String> updateSlotStatus(@PathVariable UUID slotId, @RequestParam boolean newStatus) {
        try {
           userService.updateSlotStatus(slotId, newStatus);
            return ResponseEntity.ok("Slot status updated successfully.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Slot not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the slot status.");
        }
    }
    @PostMapping("/check-availability")
    public ResponseEntity<Map<String, Object>>checkAvailability(@RequestBody AppointmentData appointmentData) {
        System.out.println(appointmentData+"hello000000000000000000000000000000000000000000000000000");


     boolean res=true;

//       boolean res=userService.checkAvailability(appointmentData);
        Map<String, Object> response = new HashMap<>();


       if(res){
           response.put("appointmentData",appointmentData);
           response.put("message","Slot Available");
           return  new ResponseEntity<>(response,HttpStatus.OK);

       }
        response.put("message","Slot Not  Available");
        return  new ResponseEntity<>(response,HttpStatus.OK);



    }



    @PostMapping("/payment")
    public ResponseEntity<Map<String, Object>>bookingAppoinment(@RequestBody AppointmentData appointmentData, @RequestHeader("Authorization") String authorizationHeader) {
        System.out.println(authorizationHeader);

  Map<String, Object> response=null;
          response=userService.bookingAppoinment(appointmentData,authorizationHeader);
        System.out.println(response+"________________________");

        return  new ResponseEntity<>(response,HttpStatus.OK);
    }



    @GetMapping("/view-appointments/{userId}")
    public ResponseEntity<Map<String,List>>viewAppointments(@PathVariable("userId") UUID id){
            Map<String,List> response = new HashMap<>();
        List<Appointment> appointmentList=userService.viewAppointments(id);
       response.put("appointmentsDetails",appointmentList);
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }


    @PostMapping("/cancelAppointment")
    public ResponseEntity<Map<String,String>>cancelAppointemnt(@RequestBody AppointmentCanceldto appointmentCanceldto){
          String res=userService.cancelAppointemnt(appointmentCanceldto);
          Map<String,String>response = new HashMap<>();
          response.put("response",res);

        return  new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/getUserProfile/{userId}")
    public ResponseEntity<Map<String,UserProfile>>getUserProfile(@PathVariable("userId") UUID id){

        UserProfile userProfile=userService.getUserProfile(id);
        Map<String,UserProfile>response = new HashMap<>();
        response.put("userProfile",userProfile);
        System.out.println(userProfile);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }



    @PostMapping("/updateUserProfile")
    public ResponseEntity<UserProfile> updateUser(@RequestBody UserProfile request) {
        UserProfile userProfile = userService.updateUserProfile(request);
        return  new ResponseEntity<>(userProfile,HttpStatus.OK);
    }



}