package com.example.mediconnect.UserService.controller;




import com.example.mediconnect.UserService.dto.*;
import com.example.mediconnect.UserService.dto.doctor.AvailableSlotResonseDTO;
import com.example.mediconnect.UserService.dto.doctor.SlotDTO;

import com.example.mediconnect.UserService.dto.user.SlotResponseListDTO;
import com.example.mediconnect.UserService.service.DoctorService;
import com.example.mediconnect.UserService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @Autowired
    private UserService userService;
    @GetMapping("/doctors")
    public ResponseEntity<Void> getAllDoctors(){



        return  new ResponseEntity<>( HttpStatus.OK);
    }

    @GetMapping("/getDoctorProfile/{docId}")
    public ResponseEntity<Map<String, Doctor>>GetDoctorById(@PathVariable("docId") UUID id){

        Doctor doctor = doctorService. GetDoctorById(id);


        Map<String,Doctor> response = new HashMap<>();
        response.put("doctorProfile",doctor);
        System.out.println(response);
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }



    @PostMapping("/addBookingSlot")
    public ResponseEntity<String> addSlots(@RequestBody List<SlotDTO> slotDTOs) {
        try {
            doctorService.addSlots(slotDTOs);
            return ResponseEntity.ok("Slots added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add slots: " + e.getMessage());
        }
    }


    @DeleteMapping("/deleteSlot/{slotId}")
    public ResponseEntity<String> deleteSlotById(@PathVariable UUID slotId) {
        System.out.println(slotId);
        try {
           doctorService.deleteSlotById(slotId);
            return ResponseEntity.ok("Slot deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request");
        }
    }
//    @PostMapping("/addBookingSlot")
//    public ResponseEntity<Map<String, DoctorSlotDto>>updateBookingSlot(@RequestBody DoctorSlotDto  doctorSlotDto){
//        System.out.println(doctorSlotDto);
////        DoctorSlotDto doctorSlotDto = new DoctorSlotDto();
//        System.out.println(doctorSlotDto);
//        doctorService.addBookingSlot(doctorSlotDto);
//        System.out.println("hello world");
//        System.out.println(doctorSlotDto);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

//    @GetMapping("/getBookingSlot/{docId}")
//    public ResponseEntity<Map<String,List<SlotResponseDTO>>>getBookingSlot(@PathVariable("docId") UUID docId){
//
//        List<SlotResponseDTO> doctorSlotDto=doctorService.getBookingSlot(docId);
//        System.out.println("hello world");
//        Map<String,  List<SlotResponseDTO>> response = new HashMap<>();
//        response.put("scheduleData",doctorSlotDto);
//        System.out.println(doctorSlotDto);
//        return new ResponseEntity<>(response,HttpStatus.OK);
//    }

//    @PostMapping("/updateBookingSlot")
//    public ResponseEntity<String> updateBookingSlot(@RequestBody SlotResponseDTO slotResponseDTO) {
//        try {
//            doctorService.updateBookingSlot(slotResponseDTO);
//            return ResponseEntity.ok("Booking slot updated successfully.");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating booking slot: " + e.getMessage());
//        }
//    }
//
//    @PostMapping("/deleteBookingSlot")
//    public ResponseEntity<Void> deleteBookingSlot(@RequestBody SlotResponseDTO doctorSlotDto) {
//        doctorService. deleteBookingSlot(doctorSlotDto);
//        return ResponseEntity.ok().build();
//    }




//    @PostMapping("/addBookingSlot")
//    public ResponseEntity<Map<String, DoctorSlotDto>>updateBookingSlot(@RequestBody  Map<String,Object> requestBody){
//        System.out.println(requestBody);
//        DoctorSlotDto doctorSlotDto = new DoctorSlotDto();
//        System.out.println(doctorSlotDto);
//        doctorService.updateBookingSlot(doctorSlotDto);
//        System.out.println("hello world");
//        System.out.println(doctorSlotDto);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    //    @PostMapping("/addBookingSlot")
//    public ResponseEntity<Map<String,Object>>updateBookingSlot(@RequestBody Map<String,Object> res){
//        System.out.println(res);
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//    @GetMapping("/doctors/{doctorId}/time-slots")
//    public ResponseEntity<List<String>> getTimeSlotsByDate(@PathVariable UUID doctorId,
//                                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
//        DoctorSlotDto doctorSlotDto = new DoctorSlotDto();
//        doctorSlotDto.setDoctor_id(doctorId);
//
//        List<String> timeSlots = doctorService.getTimeSlotsByDate(doctorSlotDto, date);
//        return ResponseEntity.ok(timeSlots);
//    }



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
//        List<Appointment> appointmentList=doctorService.getAppointmentRequest(id);
//        System.out.println(appointmentList);
//        response.put("appointmentsDetails",appointmentList);
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }


    @GetMapping("/getTodaysAppointmentRequests/{docId}")
    public ResponseEntity<Map<String,List>>getTodaysAppointments(@PathVariable("docId") UUID id){
        Map<String,List> response = new HashMap<>();

        List<Appointment> appointmentList=null;
        while (appointmentList==null) {
//            appointmentList=doctorService.getTodaysAppointments(id);
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

    @GetMapping("/getAvailableSlots/{doctorId}")
    public ResponseEntity <Map<String,List>>getAvailableSlots(@PathVariable("doctorId") UUID doctorId) {
            List<AvailableSlotResonseDTO> availableSlots = doctorService.getAvailableSlots(doctorId);
        Map<String,List>response = new HashMap<>();
        response.put("slot",availableSlots);
            return  new ResponseEntity<>(response,HttpStatus.OK);


    }


    @GetMapping("/getAvailableSlot/{doctorId}")
    public ResponseEntity <Map<String,SlotResponseListDTO>>getAvailableSlot(@PathVariable("doctorId") UUID doctorId) {
       SlotResponseListDTO availableSlots = userService.getAvailableSlots(doctorId);
        Map<String,SlotResponseListDTO>response = new HashMap<>();
        response.put("slot",availableSlots);
        return  new ResponseEntity<>(response,HttpStatus.OK);


    }



}
