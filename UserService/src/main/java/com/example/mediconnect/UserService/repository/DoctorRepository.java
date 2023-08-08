package com.example.mediconnect.UserService.repository;

import com.example.mediconnect.UserService.dto.Doctor;
import com.example.mediconnect.UserService.entity.doctor.DoctorCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface DoctorRepository extends JpaRepository<DoctorCredentials,UUID> {


    List<DoctorCredentials> findByIsApproved(String isApproved);




    List<DoctorCredentials> findBySpecialization(String specialization);


//    List<DoctorCredentials> findByFirstnameIgnoreCaseContainingOrLastnameIgnoreCaseContaining(String keyword);

    @Query(value = "select * from doctor_credentials where name like %:keyword% or firstname like  %:keyword%", nativeQuery = true)
    List<DoctorCredentials> findByKeyword(@Param("keyword") String keyword);
}
