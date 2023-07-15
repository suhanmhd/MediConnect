package com.example.mediconnect.DoctorService.service;

import com.example.mediconnect.DoctorService.dto.*;
import com.example.mediconnect.DoctorService.entity.AvailableSlot;
import com.example.mediconnect.DoctorService.entity.DoctorCredentials;
import com.example.mediconnect.DoctorService.kafka.AppointmentConsumer;
import com.example.mediconnect.DoctorService.kafka.Consumer;
import com.example.mediconnect.DoctorService.kafka.Producer;
import com.example.mediconnect.DoctorService.repository.DoctorRepository;
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
    private Producer producer;

    @Autowired
    private AppointmentConsumer appointmentConsumer;





    public void saveDoctor(DoctorCredentials doctorCredentials) {
          doctorCredentials.setIsApproved("pending");
          doctorCredentials.setEnabled(true);
        doctorRepository.save(doctorCredentials);



    }


    public void getAllDoctors() {
        List<DoctorCredentials> doctorCredentials =doctorRepository.findAll();


        List<Doctor>doctorResponse = new ArrayList<>();


        for(DoctorCredentials doctors:doctorCredentials){

            Doctor doctor = new Doctor();
            copyProperties(doctors,doctor);
            doctorResponse.add(doctor);

        }
         producer.sendAllDoctors(doctorResponse);

    }

    public void getAllPendingApprovals() {
        List<DoctorCredentials> doctorCredentials = doctorRepository.findByIsApproved("pending");
        List<Doctor>pendingApprovals = new ArrayList<>();


        for(DoctorCredentials doctors:doctorCredentials){
            Doctor doctor = new Doctor();
            copyProperties(doctors,doctor);
            pendingApprovals.add(doctor);
        }
        producer.sendAllPendingApprovals( pendingApprovals);

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
        System.out.println(doctorResponse+"dmsdmfsdfmsd,fs");
        producer.sendDoctorByDepartment(doctorResponse);
    }
    @Transactional
    public void getDoctorById(DoctorId doctorId) {
       DoctorCredentials doctor=doctorRepository.getById(doctorId.getId());
       Doctor doctors= new Doctor();
        copyProperties(doctor,doctors);

       producer.sendDoctorById( doctors);

    }

    public void blockDoctorById(DoctorId doctorId) {


       DoctorCredentials  doctor=doctorRepository.getById(doctorId.getId());

        doctor.setEnabled(false);
        doctorRepository.save(doctor);
        DoctorCredentials doctors=doctorRepository.getById(doctorId.getId());
//        System.out.println(doctor);
        Doctor doctordetails= new Doctor();
        copyProperties(doctors,doctordetails);
        System.out.println(doctordetails);
        producer.sendblockDoctorRes(doctordetails);
    }

    public void UnblockDoctorById(DoctorId doctorId) {
        DoctorCredentials  doctor=doctorRepository.getById(doctorId.getId());


        doctor.setEnabled(true);
        doctorRepository.save(doctor);
        DoctorCredentials doctors=doctorRepository.getById(doctorId.getId());
//        System.out.println(doctor);
        Doctor doctordetails= new Doctor();
        copyProperties(doctors,doctordetails);
        System.out.println(doctordetails);
        producer.sendUnblockDoctorRes(doctordetails);
    }

    public Doctor GetDoctorById(UUID id) {
        DoctorCredentials  doctorCredentials=doctorRepository.getById(id);
         Doctor doctor = new Doctor();
         copyProperties(doctorCredentials,doctor);
         return doctor;
    }

    public Doctor updateDoctor(Doctor doctor) {

        DoctorCredentials  doctorCredentials=doctorRepository.getById(doctor.getId());

        doctorCredentials.setExperience(doctor.getExperience());
        doctorCredentials.setFeesPerConsultation(doctor.getFeesPerConsultation());
        doctorCredentials.setImage(doctor.getImage());

//        doctorCredentials.setTimings(doctor.getTimings());
        doctorRepository.save(doctorCredentials);


        Doctor updatedDoctor = new Doctor();
        copyProperties(doctorCredentials,updatedDoctor);
        System.out.println(updatedDoctor+"+++++++++++");

        return updatedDoctor;
    }

    public List<Appointment> getAppointmentRequest(UUID id) {
        producer.getAppointmentRequest(id);
        List<Appointment> appointmentList=null;
        while (appointmentList==null) {
            appointmentList=appointmentConsumer.getAllAppointmetsToDoctor();
        }
        return appointmentList;
    }

    public List<Appointment> getTodaysAppointments(UUID id) {
        producer.getTodaysAppointments(id);

        List<Appointment> appointmentList =null;
        while (appointmentList==null) {
            appointmentList = appointmentConsumer.getTodaysAppointmentsToDoctor();
        }
         return  appointmentList;
    }

    public void updateBookingSlot(DoctorSlotDto doctorSlotDto) {

        Optional<DoctorCredentials> optionalDoctor = doctorRepository.findById(doctorSlotDto.getId());
        if (optionalDoctor.isPresent()) {
            DoctorCredentials doctor = optionalDoctor.get();
            List<AvailableSlotDTO> availableSlots = doctorSlotDto.getAvailableSlots();

            // Create AvailableSlot entities and set the data
            for (AvailableSlotDTO slotDTO : availableSlots) {
                AvailableSlot slot = new AvailableSlot();
                slot.setDate(slotDTO.getDate());
                slot.setTimes(slotDTO.getTimes());
                slot.setDoctor(doctor);

                doctor.getAvailableSlots().add(slot);
            }

            doctorRepository.save(doctor);

        }
    }

    public List<String> getTimeSlotsByDate(DoctorSlotDto doctorSlotDto, LocalDate date) {
        Optional<DoctorCredentials> optionalDoctor = doctorRepository.findById(doctorSlotDto.getId());
        if (optionalDoctor.isPresent()) {
            DoctorCredentials doctor = optionalDoctor.get();

            // Filter available slots based on the provided date
            List<String> timeSlots = doctor.getAvailableSlots().stream()
                    .filter(slot -> slot.getDate().equals(date))
                    .flatMap(slot -> slot.getTimes().stream())
                    .distinct()
                    .collect(Collectors.toList());

            return timeSlots;
        } else {

            return Collections.emptyList();
        }
    }
}
