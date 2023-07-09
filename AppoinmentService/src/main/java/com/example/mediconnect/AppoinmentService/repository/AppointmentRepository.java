package com.example.mediconnect.AppoinmentService.repository;


import com.example.mediconnect.AppoinmentService.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    Optional<Appointment> findByUserId(UUID userId);

    Optional<Object> getByUserId(String userId);

    List<Appointment> findAllByUserId(String userId);

    List<Appointment> findAllByDoctorId(String doctorId);
}

