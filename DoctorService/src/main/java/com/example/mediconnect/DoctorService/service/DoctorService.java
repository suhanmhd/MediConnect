package com.example.mediconnect.DoctorService.service;

import com.example.mediconnect.DoctorService.dto.Appointment;
import com.example.mediconnect.DoctorService.dto.ApproveRequest;
import com.example.mediconnect.DoctorService.dto.Doctor;
import com.example.mediconnect.DoctorService.dto.DoctorId;
import com.example.mediconnect.DoctorService.entity.DoctorCredentials;
import com.example.mediconnect.DoctorService.kafka.AppointmentConsumer;
import com.example.mediconnect.DoctorService.kafka.Consumer;
import com.example.mediconnect.DoctorService.kafka.Producer;
import com.example.mediconnect.DoctorService.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        System.out.println(doctor+"sssssssssssssssssssssssssssssss");
    }

    public void blockDoctorById(DoctorId doctorId) {


       DoctorCredentials  doctor=doctorRepository.getById(doctorId.getId());

        doctor.setEnabled(true);
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


        doctor.setEnabled(false);
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
        doctorCredentials.setTimings(doctor.getTimings());
        doctorRepository.save(doctorCredentials);

        Doctor updatedDoctor = new Doctor();
        copyProperties(doctorCredentials,updatedDoctor);
        System.out.println(updatedDoctor+"+++++++++++");

        return updatedDoctor;
    }

    public List<Appointment> getAppointmentRequest(UUID id) {
        producer.getAppointmentRequest(id);
        List<Appointment> appointmentList=appointmentConsumer.getAllAppointmetsToDoctor();
        return appointmentList;
    }
}
