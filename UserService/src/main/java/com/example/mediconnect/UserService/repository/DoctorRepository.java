package com.example.mediconnect.UserService.repository;

import com.example.mediconnect.UserService.entity.doctor.DoctorCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface DoctorRepository extends JpaRepository<DoctorCredentials,UUID> {


    List<DoctorCredentials> findByIsApproved(String isApproved);




    List<DoctorCredentials> findBySpecialization(String specialization);
}
