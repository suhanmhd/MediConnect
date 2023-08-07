package com.example.mediconnect.UserService.repository;


import com.example.mediconnect.UserService.entity.doctor.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, UUID> {




    Department findByDepartmentName(String departmentName);
}
