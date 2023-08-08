package com.example.mediconnect.UserService.service;



import com.example.mediconnect.UserService.dto.*;
import com.example.mediconnect.UserService.dto.doctor.AvailableSlotResonseDTO;
import com.example.mediconnect.UserService.dto.doctor.SlotDTO;

import com.example.mediconnect.UserService.dto.doctor.SlotResponseDTO;
import com.example.mediconnect.UserService.entity.AvailableSlot;
import com.example.mediconnect.UserService.entity.Slot;
import com.example.mediconnect.UserService.entity.doctor.ClinicInfo;
import com.example.mediconnect.UserService.entity.doctor.DoctorCredentials;
import com.example.mediconnect.UserService.entity.doctor.Education;
import com.example.mediconnect.UserService.entity.doctor.JobHistory;
import com.example.mediconnect.UserService.repository.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private ClinicInfoRepository clinicInfoRepository;

    @Autowired
    private JobHistoryRepository jobHistoryRepository;

    @Autowired
    private AvailableSlotRepository availableSlotRepository;
    @Autowired
    private SlotRepository slotRepository;






    public void saveDoctor(DoctorCredentials doctorCredentials) {
        doctorCredentials.setIsApproved("pending");
        doctorCredentials.setEnabled(true);
        doctorRepository.save(doctorCredentials);


    }


    public void getAllDoctors() {
        List<DoctorCredentials> doctorCredentials = doctorRepository.findAll();





        List<Doctor> doctorResponse = new ArrayList<>();


        for (DoctorCredentials doctors : doctorCredentials) {

            Doctor doctor = new Doctor();
            copyProperties(doctors, doctor);
            doctorResponse.add(doctor);

        }
//        producer.sendAllDoctors(doctorResponse);

    }

    public void getAllPendingApprovals() {
        List<DoctorCredentials> doctorCredentials = doctorRepository.findByIsApproved("pending");
        List<Doctor> pendingApprovals = new ArrayList<>();


        for (DoctorCredentials doctors : doctorCredentials) {
            Doctor doctor = new Doctor();
            copyProperties(doctors, doctor);
            pendingApprovals.add(doctor);
        }
        //      producer.sendAllPendingApprovals( pendingApprovals);

    }

    public void approveDoctor(ApproveRequest request) {
        Optional<DoctorCredentials> optionalDoctorCredentials = doctorRepository.findById(request.getId());

        if (optionalDoctorCredentials.isPresent()) {
            DoctorCredentials doctorCredentials = optionalDoctorCredentials.get();
            doctorCredentials.setIsApproved(request.getStatus());

            doctorRepository.save(doctorCredentials);
        } else {
            throw new IllegalArgumentException("Doctor not found for ID: " + request.getId());
        }
    }

    public void getDoctorByDepartment(String specialization) {
        List<DoctorCredentials> doctorCredentials = doctorRepository.findBySpecialization(specialization);

        List<Doctor> doctorResponse = new ArrayList<>();


        for (DoctorCredentials doctors : doctorCredentials) {
            Doctor doctor = new Doctor();
            copyProperties(doctors, doctor);
            doctorResponse.add(doctor);
        }
        System.out.println(doctorResponse + "dmsdmfsdfmsd,fs");
//        producer.sendDoctorByDepartment(doctorResponse);
    }

    @Transactional
    public void getDoctorById(DoctorId doctorId) {
        DoctorCredentials doctor = doctorRepository.getById(doctorId.getId());
        Doctor doctors = new Doctor();
        copyProperties(doctor, doctors);

//        producer.sendDoctorById( doctors);

    }

    public void blockDoctorById(DoctorId doctorId) {


        DoctorCredentials doctor = doctorRepository.getById(doctorId.getId());

        doctor.setEnabled(false);
        doctorRepository.save(doctor);
        DoctorCredentials doctors = doctorRepository.getById(doctorId.getId());
//        System.out.println(doctor);
        Doctor doctordetails = new Doctor();
        copyProperties(doctors, doctordetails);
        System.out.println(doctordetails);
//        producer.sendblockDoctorRes(doctordetails);
    }

    public void UnblockDoctorById(DoctorId doctorId) {
        DoctorCredentials doctor = doctorRepository.getById(doctorId.getId());


        doctor.setEnabled(true);
        doctorRepository.save(doctor);
        DoctorCredentials doctors = doctorRepository.getById(doctorId.getId());
//        System.out.println(doctor);
        Doctor doctordetails = new Doctor();
        copyProperties(doctors, doctordetails);
        System.out.println(doctordetails);
//        producer.sendUnblockDoctorRes(doctordetails);
    }

    public Doctor GetDoctorById(UUID id) {
        DoctorCredentials doctorCredentials = doctorRepository.getById(id);
        Doctor doctor = new Doctor();
        copyProperties(doctorCredentials, doctor);
        return doctor;
    }

    public Doctor updateDoctor(Doctor doctor) {


        DoctorCredentials doctorCredentials = doctorRepository.getById(doctor.getId());


        String[] ignoredProperties = {"id", "name", "email"};
        BeanUtils.copyProperties(doctor, doctorCredentials, ignoredProperties);


//        // Update or create JobHistory entries
//        List<JobHistory> existingJobHistoryList = doctorCredentials.getJobHistoryList();
//        List<JobHistoryDTO> updatedJobHistoryList = doctor.getJobHistoryList();
//        for (JobHistoryDTO updatedJobHistory : updatedJobHistoryList) {
//            boolean jobHistoryExists = false;
//            for (JobHistory existingJobHistory : existingJobHistoryList) {
//                if (existingJobHistory.getId() != null && updatedJobHistory.getId() != null && existingJobHistory.getId().equals(updatedJobHistory.getId())) {
//                    // Update existing JobHistory
//                    BeanUtils.copyProperties(updatedJobHistory, existingJobHistory, "id");
//                    jobHistoryExists = true;
//                    break;
//                }
//            }
//            if (!jobHistoryExists) {
//                // Create new JobHistory
//                JobHistory newJobHistory = new JobHistory();
//                BeanUtils.copyProperties(updatedJobHistory, newJobHistory);
//                newJobHistory.setDoctor(doctorCredentials);
//                if (existingJobHistoryList == null) {
//                    existingJobHistoryList = new ArrayList<>();
//                    doctorCredentials.setJobHistoryList(existingJobHistoryList);
//                }
//
//
//                existingJobHistoryList.add(newJobHistory);
//            }
//        }
//
//        // Update or create Education entries
//        List<Education> existingEducationList = doctorCredentials.getEducationList();
//        List<EducationDTO> updatedEducationList = doctor.getEducationList();
//        for (EducationDTO updatedEducation : updatedEducationList) {
//            boolean educationExists = false;
//            for (Education existingEducation : existingEducationList) {
//                if (existingEducation.getId() != null && updatedEducation.getId() != null && existingEducation.getId().equals(updatedEducation.getId())) {
//                    // Update existing Education
//                    BeanUtils.copyProperties(updatedEducation, existingEducation, "id");
//                    educationExists = true;
//                    break;
//                }
//            }
//            if (!educationExists) {
//                // Create new Education
//                Education education = new Education();
//                BeanUtils.copyProperties(updatedEducation, education);
//
//                education.setDoctor(doctorCredentials);
//
//                if (existingEducationList == null) {
//                    existingEducationList = new ArrayList<>();
//                    doctorCredentials.setEducationList(existingEducationList);
//                    System.out.println("help" + doctorCredentials);
//                }
//                existingEducationList.add(education);
//            }
//        }
//
//        // Update or create ClinicInfo
//        ClinicInfo existingClinicInfo = doctorCredentials.getClinicInfo();
//        ClinicInfoDTO updatedClinicInfo = doctor.getClinicInfo();
//        if (updatedClinicInfo != null) {
//            if (existingClinicInfo != null) {
//                // Update existing ClinicInfo
//                BeanUtils.copyProperties(updatedClinicInfo, existingClinicInfo, "id");
//            } else {
//                // Create new ClinicInfo
//                ClinicInfo clinic = new ClinicInfo();
//
//                BeanUtils.copyProperties(updatedClinicInfo, clinic);
//                doctorCredentials.setClinicInfo(clinic);
//
//
//                clinic.setDoctor(doctorCredentials);
//                doctorCredentials.setClinicInfo(clinic);
//            }
//        }

        // Save the updated DoctorCredentials
        doctorRepository.save(doctorCredentials);

        // Return the updated Doctor
        Doctor updatedDoctor = new Doctor();
        BeanUtils.copyProperties(doctorCredentials, updatedDoctor);
        System.out.println(updatedDoctor + "+++++++++++");

        return updatedDoctor;
    }




    //fixed

// ...

    public List<AvailableSlotResonseDTO> getAvailableSlots(UUID doctorId) {
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
        List<AvailableSlotResonseDTO> availableSlotDTOs = new ArrayList<>();
        for (AvailableSlot availableSlot : filteredSlots) {
            List<SlotResponseDTO> slotDTOs = new ArrayList<>();
            for (Slot slot : availableSlot.getSlots()) {
                SlotResponseDTO slotDTO = new SlotResponseDTO(
                        slot.getId(),
                        slot.getStartTime(),
                        slot.getEndTime(),
                        slot.isStatus()
                );
                slotDTOs.add(slotDTO);
            }
            AvailableSlotResonseDTO availableSlotDTO = new AvailableSlotResonseDTO(availableSlot.getDate(), availableSlot.getDoctor().getId(), slotDTOs);
            availableSlotDTOs.add(availableSlotDTO);
        }
        return availableSlotDTOs;
    }


//        public List<AvailableSlotResonseDTO> getAvailableSlots(UUID doctorId) {
//            // Find the doctor by ID
//            DoctorCredentials doctor = doctorRepository.findById(doctorId)
//                    .orElseThrow(() -> new IllegalArgumentException("Doctor with ID " + doctorId + " not found"));
//
//            // Get all available slots for the doctor
//            List<AvailableSlot> availableSlots = availableSlotRepository.findByDoctorOrderByDate(doctor);
//
//            // Convert the entities to DTOs for the response
//            List<AvailableSlotResonseDTO> availableSlotDTOs = new ArrayList<>();
//            for (AvailableSlot availableSlot : availableSlots) {
//                List<SlotResponseDTO> slotDTOs = new ArrayList<>();
//                for (Slot slot : availableSlot.getSlots()) {
//                    SlotResponseDTO slotDTO = new SlotResponseDTO(
//
//                            slot.getId(),
//                            slot.getStartTime(),
//                            slot.getEndTime(),
//                            slot.isStatus()
//                    );
//                    slotDTOs.add(slotDTO);
//                }
//                AvailableSlotResonseDTO availableSlotDTO = new AvailableSlotResonseDTO(availableSlot.getDate(), availableSlot.getDoctor().getId(), slotDTOs);
//                availableSlotDTOs.add(availableSlotDTO);
//            }
//            return availableSlotDTOs;
//        }

//fixed




//    public List<Appointment> getAppointmentRequest(UUID id) {
//        producer.getAppointmentRequest(id);
//        List<Appointment> appointmentList=null;
//        while (appointmentList==null) {
//            appointmentList=appointmentConsumer.getAllAppointmetsToDoctor();
//        }
//        return appointmentList;
//    }
//
//    public List<Appointment> getTodaysAppointments(UUID id) {
//        producer.getTodaysAppointments(id);
//
//        List<Appointment> appointmentList =null;
//        while (appointmentList==null) {
//            appointmentList = appointmentConsumer.getTodaysAppointmentsToDoctor();
//        }
//        return  appointmentList;
//    }

//    public void addBookingSlot(DoctorSlotDto doctorSlotDto) {
//        Optional<DoctorCredentials> optionalDoctor = doctorRepository.findById(doctorSlotDto.getDoctor_id());
//        if (optionalDoctor.isPresent()) {
//            DoctorCredentials doctor = optionalDoctor.get();
//            AvailableSlotDTO availableSlots = doctorSlotDto.getAvailable_slots();
//
//            // Check if the day already exists in the available slots
//            AvailableSlot existingSlot = doctor.getAvailableSlots().stream()
//                    .filter(slot -> slot.getDay().equals(availableSlots.getDay()))
//                    .findFirst()
//                    .orElse(null);
//
//            if (existingSlot != null) {
//                // Day already exists, append new times to the existing ones
////                existingSlot.getTimes().addAll(availableSlots.getTimes());
//                existingSlot.getTimeSlotAvailability().putAll(availableSlots.getTimes().stream().collect(Collectors.toMap(time -> time, time -> false)));
//
//            } else {
//                // Day doesn't exist, create a new slot entry
//                AvailableSlot newSlot = new AvailableSlot();
////                newSlot.setTimes(availableSlots.getTimes());
//                newSlot.setDoctor(doctor);
//                newSlot.setDay(availableSlots.getDay());
//                doctor.getAvailableSlots().add(newSlot);
//
//
//                // Initialize the timeSlotAvailability map for the new slot with all times set to true (available)
//                Map<String, Boolean> timeSlotAvailability = new HashMap<>();
//                for (String time : availableSlots.getTimes()) {
//                    timeSlotAvailability.put(time, false);
//                }
//                newSlot.setTimeSlotAvailability(timeSlotAvailability);
//            }
//
//            doctorRepository.save(doctor);
//        }
//    }


//    public void updateBookingSlot(SlotResponseDTO slotResponseDTO) {
//        Optional<DoctorCredentials> optionalDoctor = doctorRepository.findById(slotResponseDTO.getDoctor_id());
//        if (optionalDoctor.isPresent()) {
//            DoctorCredentials doctor = optionalDoctor.get();
//
//            // Check if the day already exists in the available slots
//            Optional<AvailableSlot> existingSlot = doctor.getAvailableSlots().stream()
//                    .filter(slot -> slot.getDay().equals(slotResponseDTO.getDay()))
//                    .findFirst();
//
//            if (existingSlot.isPresent()) {
//                // Day already exists, update the timeSlotAvailability
//                Map<String, Boolean> timeSlotAvailability = existingSlot.get().getTimeSlotAvailability();
//                slotResponseDTO.getTimes().forEach(time -> timeSlotAvailability.put(time, slotResponseDTO.getTimeSlotAvailability().getOrDefault(time, false)));
//            }
//
//            doctorRepository.save(doctor);
//        }
//    }

//    public void deleteBookingSlot(SlotResponseDTO slotResponseDTO) {
//        Optional<DoctorCredentials> optionalDoctor = doctorRepository.findById(slotResponseDTO.getDoctor_id());
//        if (optionalDoctor.isPresent()) {
//            DoctorCredentials doctor = optionalDoctor.get();
//
//            Optional<AvailableSlot> existingSlot = doctor.getAvailableSlots().stream()
//                    .filter(slot -> slot.getDay().equals(slotResponseDTO.getDay()))
//                    .findFirst();
//
//            if (existingSlot.isPresent()) {
//                AvailableSlot slot = existingSlot.get();
//                Map<String, Boolean> timeSlotAvailability = slot.getTimeSlotAvailability();
//                String timeSlot = slotResponseDTO.getTimeSlotAvailability().toString(); // Assuming this returns the time slot like "10.00 am"
//
//                if (timeSlotAvailability.keySet().contains(timeSlot)) {
//                    timeSlotAvailability.remove(timeSlot);
//
//                    // If all times are removed from the slot, delete the slot entry
//                    if (timeSlotAvailability.isEmpty()) {
//                        doctor.getAvailableSlots().remove(slot);
//                        availableSlotRepository.delete(slot);
//                    } else {
//                        doctorRepository.save(doctor);
//                    }
//                }
//            }
//        }
//    }


//    public void updateBookingSlot(DoctorSlotDto doctorSlotDto) {
//
//        Optional<DoctorCredentials> optionalDoctor = doctorRepository.findById(doctorSlotDto.getDoctor_id());
//        if (optionalDoctor.isPresent()) {
//            DoctorCredentials doctor = optionalDoctor.get();
//            AvailableSlotDTO availableSlots = (AvailableSlotDTO) doctorSlotDto.getAvailable_slots();
//
//
//                AvailableSlot slot = new AvailableSlot();
//
////                slot.setTimes(availableSlots.getTimes());
//                slot.setDoctor(doctor);
//                slot.setDay(availableSlots.getDay());
//
//                doctor.getAvailableSlots().add(slot);
//
//
//            doctorRepository.save(doctor);
//
//        }
//    }

//    public  List<SlotResponseDTO>  getBookingSlot(UUID doctorId) {
//        DoctorCredentials doctorCredentials = doctorRepository.getById(doctorId);
//        List<AvailableSlot> availableSlots =availableSlotRepository.findByDoctorId(doctorId);
//
//
//        List<SlotResponseDTO> availableSlotDtos = availableSlots.stream()
//                .map(this::convertToDto)

//        System.out.println("sss"+availa//                .collect(Collectors.toList());
//bleSlotDtos);
//        System.out.println(  doctorCredentials.getAvailableSlots());
//        System.out.println(doctorCredentials);
//        DoctorSlotDto dtr = new DoctorSlotDto();
//          return availableSlotDtos;
//
//
//    }
//    private SlotResponseDTO convertToDto(AvailableSlot availableSlot) {
//        return SlotResponseDTO.builder()
//                .doctor_id(availableSlot.getDoctor().getId())
//                .id(availableSlot.getId())
//                .day(availableSlot.getDay())
////                .times(availableSlot.getTimes())
//                .timeSlotAvailability(availableSlot.getTimeSlotAvailability()) // Populate availability information
//                .build();
//    }

    public void addSlots(List<SlotDTO> slotDTOs) {
        for (SlotDTO slotDTO : slotDTOs) {
            addSingleSlot(slotDTO);
        }

    }

    private void addSingleSlot(SlotDTO slotDTO) {
        // First, check if the doctor with the given ID exists
        DoctorCredentials doctor = doctorRepository.findById(slotDTO.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor with ID " + slotDTO.getDoctorId() + " not found"));

        // Find an existing slot if it exists for the given date and doctor
        AvailableSlot availableSlot = availableSlotRepository.findByDateAndDoctor(slotDTO.getDate(), doctor);

        if (availableSlot == null) {
            // If the slot doesn't exist, create a new one
            availableSlot = new AvailableSlot();
            availableSlot.setDate(slotDTO.getDate());
            availableSlot.setDoctor(doctor);
            availableSlot.setSlots(new ArrayList<>()); // Initialize the slots list for the new AvailableSlot
        }

        // Check if the slot with the given start and end time already exists
        boolean slotExists = availableSlot.getSlots().stream()
                .anyMatch(slot -> slot.getStartTime()
                        .equals(slotDTO.getStartTime()) && slot.getEndTime().equals(slotDTO.getEndTime()));

        if (!slotExists) {
            // If the slot with the given start and end time doesn't exist, create a new slot
            Slot newSlot = new Slot();
            newSlot.setStartTime(slotDTO.getStartTime());
            newSlot.setEndTime(slotDTO.getEndTime());
            newSlot.setStatus(slotDTO.isStatus());

            // Associate the Slot with the AvailableSlot
            newSlot.setAvailableSlot(availableSlot);

            // Add the new slot to the list of slots
            availableSlot.getSlots().add(newSlot);

            // Save the AvailableSlot to add the new slot
            availableSlotRepository.save(availableSlot);
        }
    }

//    public List<AvailableSlot> getAvailableSlotsByDoctor(UUID doctorId) {
//        // Find the doctor by ID
//        DoctorCredentials doctor = doctorRepository.findById(doctorId)
//                .orElseThrow(() -> new IllegalArgumentException("Doctor with ID " + doctorId + " not found"));
//        System.out.println(doctor);
//
//        // Get all available slots for the doctor
//
//        List<AvailableSlot> slot = new ArrayList<>();
//        slot = availableSlotRepository.findByDoctor(doctor);
//        List<AvailableSlot> slots = new ArrayList<>();
////        List<Slot> s = slotRepository.findBySlot(slot);
//        System.out.println("__");
////               availableSlotRepository.findByDoctor(doctor);
//        System.out.println(slot);
//        return slots;
//    }




    public void deleteSlotById(UUID slotId) {
        // Find the slot by ID
        Optional<Slot> optionalSlot = slotRepository.findById(slotId);

        if (optionalSlot.isPresent()) {
            Slot slot = optionalSlot.get();
            System.out.println(slot);
            // Remove the slot from its parent AvailableSlot entity
            AvailableSlot availableSlot = slot.getAvailableSlot();
            System.out.println(availableSlot);
            System.out.println(availableSlot.getSlots());
            availableSlot.getSlots().remove(slot);
            System.out.println(slot);
            System.out.println(slotId);
            slotRepository.delete(slot);
        } else {
            // Handle case where the slot with the given ID doesn't exist
            throw new RuntimeException("Slot not found");
        }
    }
}













        // ... (other autowired repositories and services)

//    public  List<AvailableSlotResponseDTO> getAvailableSlotsByDoctor(UUID doctorId) {
//        // Find the doctor by ID
//        DoctorCredentials doctor = doctorRepository.findById(doctorId)
//                .orElseThrow(() -> new IllegalArgumentException("Doctor with ID " + doctorId + " not found"));
//
//        // Get all available slots for the doctor
//        List<AvailableSlot> availableSlots = availableSlotRepository.findByDoctor(doctor);
//
//        // Convert the entities to DTOs for the response
//        List<AvailableSlotResponseDTO> availableSlotDTOs = new ArrayList<>();
//        for (AvailableSlot availableSlot : availableSlots) {
//            List<SlotResponseDTO> slotDTOs = new ArrayList<>();
//            for (Slot slot : availableSlot.getSlots()) {
//                SlotResponseDTO slotDTO = new SlotResponseDTO(
//                        // Use the doctor ID from the availableSlot entity
//                        availableSlot.getDoctor().getId(),
//                        availableSlot.getDate(),
//                        slot.getStartTime(),
//                        slot.getEndTime(),
//                        slot.isStatus()
//                );
//                slotDTOs.add(slotDTO);
//            }
//            AvailableSlotResponseDTO availableSlotDTO = new AvailableSlotResponseDTO(availableSlot.getDate(), slotDTOs);
//            availableSlotDTOs.add(availableSlotDTO);
//        }
//        return availableSlotDTOs;
//    }





//    public List<String> getTimeSlotsByDate(DoctorSlotDto doctorSlotDto, LocalDate date) {
//        Optional<DoctorCredentials> optionalDoctor = doctorRepository.findById(doctorSlotDto.getDoctor_id());
//        if (optionalDoctor.isPresent()) {
//            DoctorCredentials doctor = optionalDoctor.get();
//
//            // Filter available slots based on the provided date
//            List<String> timeSlots = doctor.getAvailableSlots().stream()
//                    .filter(slot -> slot.getDate().equals(date))
//                    .flatMap(slot -> slot.getTimes().stream())
//                    .distinct()
//                    .collect(Collectors.toList());
//
//            return timeSlots;
//        } else {
//
//            return Collections.emptyList();
//        }
//    }

