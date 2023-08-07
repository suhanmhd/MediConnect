package com.example.mediconnect.UserService.repository;

import com.example.mediconnect.UserService.entity.doctor.JobHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface JobHistoryRepository extends JpaRepository<JobHistory, UUID> {

}