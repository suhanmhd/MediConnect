package com.example.mediconnect.UserService.service;

import com.example.mediconnect.UserService.dto.*;
//import com.example.mediconnect.UserService.dto.doctor.AvailableSlotResonseDTO;
//import com.example.mediconnect.UserService.dto.doctor.SlotResponseDTO;
import com.example.mediconnect.UserService.dto.doctor.AvailableSlotResonseDTO;
import com.example.mediconnect.UserService.dto.user.AvailableSlotToUserDTO;
import com.example.mediconnect.UserService.dto.user.SlotResponseListDTO;
import com.example.mediconnect.UserService.dto.user.SlotResponseToUserDTO;
import com.example.mediconnect.UserService.entity.AvailableSlot;
import com.example.mediconnect.UserService.entity.Slot;
import com.example.mediconnect.UserService.entity.doctor.DoctorCredentials;
import com.example.mediconnect.UserService.entity.user.UserDetails;
import com.example.mediconnect.UserService.kafka.BookingConsumer;
import com.example.mediconnect.UserService.kafka.DepartmentConsumer;
import com.example.mediconnect.UserService.kafka.Producer;
import com.example.mediconnect.UserService.repository.AvailableSlotRepository;
import com.example.mediconnect.UserService.repository.DoctorRepository;
import com.example.mediconnect.UserService.repository.SlotRepository;
import com.example.mediconnect.UserService.repository.UserDetailsRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
public class UserService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Value("${secret.key}")
    private String SECRETKEY;

    @Autowired
    private DepartmentConsumer departmentConsumer;
    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private Producer producer;
    @Autowired
    private BookingConsumer bookingConsumer;


    @Autowired
    private AvailableSlotRepository availableSlotRepository;
    @Autowired
    private SlotRepository slotRepository;

    private final Object lock = new Object();


    public void saveUser(UserDetails userDetails) {
        System.out.println(userDetails);
        userDetailsRepository.save(userDetails);
    }

    public List<DepartmentResponse> getAllDepartments() {

        producer.getAllDepartments();

        List<DepartmentResponse> departmentResponses = null;
        while (departmentResponses == null) {
            departmentResponses = departmentConsumer.getRecievedDepartmentResponse();
        }

        return departmentResponses;
    }


    public List<Doctor> getAllDoctorsToUser() {
        List<DoctorCredentials> doctorList = doctorRepository.findAll();
        List<Doctor> doctors = new ArrayList<>();
        for (DoctorCredentials doctorCredentials : doctorList) {
            Doctor doctor = new Doctor();
            copyProperties(doctorCredentials, doctor);
            doctors.add(doctor);
        }
        return doctors;
    }

    public Doctor getDoctorById(UUID id) {

        DoctorCredentials doctorCredentials = doctorRepository.getById(id);
        Doctor doctor = new Doctor();
        copyProperties(doctorCredentials, doctor);
        return doctor;
//
//        DoctorId doctorId =new DoctorId();
//        doctorId.setId(id);
//
//
//        producer.getDoctorById(doctorId);
//        Doctor doctor=null;
//        while (doctor==null) {
//            doctor= departmentConsumer.getReceivedDoctor();
//        }
//        return doctor;
    }

    public void getAllUsers() {
        List<UserDetails> userDetails = userDetailsRepository.findAll();
        List<Userdto> userResponse = new ArrayList<>();

        for (UserDetails user : userDetails) {
            Userdto userdto = new Userdto();
            copyProperties(user, userdto);
            userResponse.add(userdto);
        }


        producer.sendAllUsers(userResponse);

    }


    public void blockUserById(UserId userId) {
        UserDetails user = userDetailsRepository.getById(userId.getId());


        user.setEnabled(false);
        userDetailsRepository.save(user);
        UserDetails userDetails = userDetailsRepository.getById(userId.getId());
        System.out.println(userId);

        Userdto userdto = new Userdto();
        copyProperties(userDetails, userdto);
        System.out.println("____" + userdto);

        producer.sendblockUserRes(userdto);
    }

    public void UnblockUserById(UserId userId) {
        UserDetails user = userDetailsRepository.getById(userId.getId());


        user.setEnabled(true);
        userDetailsRepository.save(user);
        List<UserDetails> userDetails = userDetailsRepository.findAll();
        List<Userdto> userResponse = new ArrayList<>();

        for (UserDetails users : userDetails) {
            Userdto userdto = new Userdto();
            copyProperties(users, userdto);
            userResponse.add(userdto);
        }


        producer.sendUnblockUserRes(userResponse);

    }

//    public boolean checkAvailability(AppointmentData appointmentData) {
//
//
//        String date = appointmentData.getDate();
//        String fromTime = appointmentData.getTime();
//
//
//
//        LocalDate today = LocalDate.now();
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//
//        LocalDate selectedDate = LocalDate.parse(date, dateFormatter);
//
//        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("h:mm a");
//        LocalTime toTimeObj = LocalTime.parse(fromTime, inputFormatter);
//
//
//        LocalTime addedTime = toTimeObj.plusMinutes(16);
//
//        // Format the added time as "h:mm a"
//        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("h:mm a");
//        String toTime = addedTime.format(outputFormatter);
//
//        System.out.println(toTime);
//
//
//
//
//
//        Doctor doctor = getDoctorById(appointmentData.getDocId());
//        System.out.println(doctor);
//
//            producer.getAllAppointmentsByDoctorId(appointmentData.getDocId());
//        System.out.println("hello");
//          List<Appointment> appointment =  bookingConsumer.getAllReceivedAppointmetTimes();
//        System.out.println(":::"+appointment+"::::");
//        List<Appointment> appointments = new ArrayList<>();
//
//        for(Appointment appointmentlist: appointment ){
//            if(appointmentlist.getDate().equals(date)){
//                appointments.add(appointmentlist);
//            }
//        }
//        if (selectedDate.isBefore(today)) {
//            return false;
//        }
//
//        if (doctor != null && doctor.getTimings().size() >= 2) {
//            LocalTime doctorStartTime = LocalTime.parse(doctor.getTimings().get(0), inputFormatter);
//            LocalTime doctorEndTime = LocalTime.parse(doctor.getTimings().get(1), inputFormatter);
//
//            if (toTimeObj.compareTo(doctorStartTime) >= 0 && toTimeObj.compareTo(doctorEndTime) <= 0) {
//
//                for(Appointment appointmentTime: appointments){
//
//                    LocalTime AppointmentTime =LocalTime.parse(appointmentTime.getTime(),inputFormatter);
//                    LocalTime appointmentTimePlus15 = AppointmentTime.plusMinutes(15);
//
//                    if(toTimeObj.isAfter(AppointmentTime)&&toTimeObj.isBefore(appointmentTimePlus15)){
//                        return false;
//                    }
//                    if(AppointmentTime.isAfter(toTimeObj)&&AppointmentTime.isBefore(addedTime)){
//                        return false;
//                    }
//                }
//
//                System.out.println("success");
//                return true;
//
//
//            } else {
//                System.out.println("not");
//                return false;
//
//            }
//
//
//        }
//        return false;
//    }


    public Map<String, Object> bookingAppoinment(AppointmentData appointmentData, String authorizationHeader) {
        System.out.println("<<<<"+appointmentData);
        appointmentData.getSlotId();

        updateSlotStatus(appointmentData.getSlotId(), true);



        String token = authorizationHeader.substring(7);

        Claims claims = Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(token).getBody();
        String name = claims.getSubject();
        String role = (String) claims.get("role");

//        Optional<UserDetails>userDetails =userDetailsRepository.findByName(name);

        UserDetails userDetails = userDetailsRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("User details not found"));
        System.out.println(userDetails);

        Doctor doctor = getDoctorById(appointmentData.getDocId());
        System.out.println(doctor);


        AppointmentReq appointmentReq = AppointmentReq.builder()
                .docId(appointmentData.getDocId())
                .date(appointmentData.getDate())
                .time(appointmentData.getTime())
                .doctorInfo(doctor.getFirstname() + " " + doctor.getLastname())
                .amount(doctor.getFeesPerConsultation())
                .userId(userDetails.getId())
                .userInfo(userDetails.getFirstname() + " " + userDetails.getLastname())
                .build();

        producer.bookingAppoinment(appointmentReq);


        Map<String, Object> response = null;
        response = bookingConsumer.getReceivedBookingRes();


        synchronized (lock) {
            while (response == null) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


        System.out.println(userDetails);
        System.out.println(":::" + response);
        System.out.println(name);
        return response;
    }

    public List<Appointment> viewAppointments(UUID id) {

        producer.viewAppointments(id);
        List<Appointment> appointmentList = bookingConsumer.getAllReceivedAppointmets();
        return appointmentList;
    }

    public String cancelAppointemnt(AppointmentCanceldto appointmentCanceldto) {
        producer.cancelAppointemnt(appointmentCanceldto);
        return bookingConsumer.getCancelAppointemntRes();

    }

    public UserProfile getUserProfile(UUID id) {

        UserDetails user = userDetailsRepository.findById(id).orElse(null);
        UserProfile userProfile = new UserProfile();
        copyProperties(user, userProfile);
        return userProfile;

    }


    public UserProfile updateUserProfile(UserProfile userProfile) {
        UserDetails user = userDetailsRepository.findById(userProfile.getId()).orElseThrow(null);

//        UserDetails user = userDetailsRepository.getById(userProfile.getId());
        user.setAge(userProfile.getAge());
        user.setGender(userProfile.getGender());
        user.setImage(userProfile.getImage());
        userDetailsRepository.save(user);
        UserProfile updatedUser = new UserProfile();
        copyProperties(user, updatedUser);
        return updatedUser;


    }


// ...

    public SlotResponseListDTO getAvailableSlots(UUID doctorId) {
        // Find the doctor by ID
        DoctorCredentials doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor with ID " + doctorId + " not found"));

        // Get all available slots for the doctor and sort them based on date
        List<AvailableSlot> availableSlots = availableSlotRepository.findByDoctorOrderByDate(doctor);

        // Filter out slots that are before today's date
        LocalDate today = LocalDate.now();
        List<AvailableSlot> filteredSlots = availableSlots.stream()
                .filter(slot -> slot.getDate().isAfter(today.minusDays(1))) // include today as well
                .collect(Collectors.toList());

        // Convert the entities to DTOs for the response
        SlotResponseListDTO availableSlotListDTO = new SlotResponseListDTO();
        List<AvailableSlotToUserDTO> availableSlotDTOs = new ArrayList<>();

        for (AvailableSlot availableSlot : filteredSlots) {
            List<SlotResponseToUserDTO> slotDTOs = new ArrayList<>();
            for (Slot slot : availableSlot.getSlots()) {
                SlotResponseToUserDTO slotDTO = new SlotResponseToUserDTO(
                        slot.getId(),
                        slot.getStartTime(),
                        slot.getEndTime(),
                        slot.isStatus()
                );
                slotDTOs.add(slotDTO);
            }
            AvailableSlotToUserDTO availableSlotDTO = new AvailableSlotToUserDTO(
                    availableSlot.getDate(),
                    slotDTOs
            );
            availableSlotDTOs.add(availableSlotDTO);
        }

        availableSlotListDTO.setDoctorId(doctorId);
        availableSlotListDTO.setSlotList(availableSlotDTOs);

        return availableSlotListDTO;
    }


//    public SlotResponseListDTO getAvailableSlots(UUID doctorId) {
//        // Find the doctor by ID
//        DoctorCredentials doctor = doctorRepository.findById(doctorId)
//                .orElseThrow(() -> new IllegalArgumentException("Doctor with ID " + doctorId + " not found"));
//
//        // Get all available slots for the doctor and sort them based on date
//        List<AvailableSlot> availableSlots = availableSlotRepository.findByDoctorOrderByDate(doctor);
//
//        // Convert the entities to DTOs for the response
//        SlotResponseListDTO availableSlotListDTO = new SlotResponseListDTO();
//        List<AvailableSlotToUserDTO> availableSlotDTOs = new ArrayList<>();
//
//        for (AvailableSlot availableSlot : availableSlots) {
//            List<SlotResponseToUserDTO> slotDTOs = new ArrayList<>();
//            for (Slot slot : availableSlot.getSlots()) {
//                SlotResponseToUserDTO slotDTO = new SlotResponseToUserDTO(
//                        slot.getId(),
//                        slot.getStartTime(),
//                        slot.getEndTime(),
//                        slot.isStatus()
//                );
//                slotDTOs.add(slotDTO);
//            }
//            AvailableSlotToUserDTO availableSlotDTO = new AvailableSlotToUserDTO(
//                    availableSlot.getDate(),
//                    slotDTOs
//            );
//            availableSlotDTOs.add(availableSlotDTO);
//        }
//
//        availableSlotListDTO.setDoctorId(doctorId);
//        availableSlotListDTO.setSlotList(availableSlotDTOs);
//
//        return availableSlotListDTO;
//    }



    public void updateSlotStatus(UUID slotId, boolean newStatus) {
        // Find the slot by ID
        Optional<Slot> optionalSlot = slotRepository.findById(slotId);

        if (optionalSlot.isPresent()) {
            Slot slot = optionalSlot.get();

            // Update the status of the slot to true
            slot.setStatus(newStatus);

            // Save the updated slot back to the database
            slotRepository.save(slot);
        } else {
            // Handle case where the slot with the given ID doesn't exist
            throw new RuntimeException("Slot not found");
        }


    }

    public List<Doctor> searchDoctorsByName(String keyword) {
        List<DoctorCredentials> doctorList =  doctorRepository.findByKeyword(keyword);
                List<Doctor>doctors =new ArrayList<>();
        for (DoctorCredentials doctorCredentials:doctorList){
            Doctor doctor = new Doctor();
            copyProperties(doctorCredentials,doctor);
            doctors.add(doctor);
        }
        return doctors;
    }


}






//    public List<Doctor> searchDoctorsByName(String searchQuery) {
//        return null;
//    }


