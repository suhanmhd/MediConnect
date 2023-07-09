package com.example.mediconnect.AdminService.repository;

import com.example.mediconnect.AdminService.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface DepartmentRepository extends JpaRepository<Department, UUID> {




    Department findByDepartmentName(String departmentName);
}
