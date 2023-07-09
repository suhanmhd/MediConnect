package com.example.mediconnect.DoctorService.repository;

import com.example.mediconnect.DoctorService.entity.DoctorCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface DoctorRepository extends JpaRepository<DoctorCredentials,UUID> {


    List<DoctorCredentials> findByIsApproved(String isApproved);




    List<DoctorCredentials> findBySpecialization(String specialization);
}
