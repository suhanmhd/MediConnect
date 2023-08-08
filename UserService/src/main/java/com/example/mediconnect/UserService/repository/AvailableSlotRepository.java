package com.example.mediconnect.UserService.repository;

import com.example.mediconnect.UserService.dto.Doctor;
import com.example.mediconnect.UserService.entity.AvailableSlot;
import com.example.mediconnect.UserService.entity.doctor.DoctorCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@Repository
public interface AvailableSlotRepository extends JpaRepository<AvailableSlot, UUID> {


    List<AvailableSlot> findByDoctorId(UUID doctorId);

    AvailableSlot findByDateAndDoctor(LocalDate date, DoctorCredentials doctor);

    List<AvailableSlot> findByDoctor(DoctorCredentials doctor);

    List<AvailableSlot> findByDoctorOrderByDate(DoctorCredentials doctor);


//    AvailableSlot findByDoctorIdAndDayAndTime(UUID doctorId, String day, List<String> times);
}
