package com.example.mediconnect.AdminService.service;

import com.example.mediconnect.AdminService.dto.*;
import com.example.mediconnect.AdminService.entity.Department;
import com.example.mediconnect.AdminService.kafka.Consumer;
import com.example.mediconnect.AdminService.kafka.Producer;
import com.example.mediconnect.AdminService.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
public class AdminService {



    private final DepartmentRepository departmentRepository;
    private final Producer producer;
    private final Consumer consumer;

    @Autowired
    public AdminService(DepartmentRepository departmentRepository, Producer producer, Consumer consumer) {
        this.departmentRepository = departmentRepository;
        this.producer = producer;
        this.consumer = consumer;
    }

    public String addDepartment(Department department) {
        if(departmentRepository.findByDepartmentName(department.getDepartmentName())!=null){
            throw new RuntimeException("department already exist");
        }
         departmentRepository.save(department);
         return  department.getDepartmentName();
    }


    public List<DepartmentResponse> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentResponse>departmentResponses = new ArrayList<>();
        for(Department department:departments){
            DepartmentResponse departmentResponse = new DepartmentResponse();
            copyProperties(department,departmentResponse);
            departmentResponses.add(departmentResponse);
        }
        return departmentResponses;
    }
//GETALL DEPARTMENTS TO USER SIDE
    public void getAllDepartmentsToUser() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentResponse>departmentResponses = new ArrayList<>();
        for(Department department:departments){
            DepartmentResponse departmentResponse = new DepartmentResponse();
            copyProperties(department,departmentResponse);
            departmentResponses.add(departmentResponse);
        }
      producer.sendAllDepartmentsToUser(departmentResponses);
    }

    public void deleteDepartmentById(UUID id) {
        if(!departmentRepository.existsById(id)){
            throw new RuntimeException("department with given id not found");
        }
        departmentRepository.deleteById(id);
    }

    public List<Doctor> getAllDoctors() {
        producer.getAllDoctors();

        List<Doctor> getResponseDoctors = null;

        while (getResponseDoctors==null){
            getResponseDoctors=consumer.getReceivedDoctors();
        }



      return  getResponseDoctors;
    }

    public Department getDepartmentById(UUID id) {
              Department department=departmentRepository.getById(id);
        return department;
    }

    public List<Doctor> getpendingApprovals() {
        producer.getpendingApprovals();

        List<Doctor> getPendingApprovals=null;
        while (getPendingApprovals==null) {
            getPendingApprovals=consumer.getReceivedPendingApprovals();
        }
        return getPendingApprovals;
    }

    public void ApproveDoctor(ApproveRequest request) {
        producer.ApproveDoctor(request);
    }

    public Doctor blockDoctor(UUID id) {
        DoctorId doctorId = new DoctorId();
        doctorId.setId(id);
        producer.sendBlockDoctor(doctorId);

        Doctor doctor = null;
        while (doctor == null) {
            doctor= consumer.getReceivedBlockedDoctor();
        }


        System.out.println("________blocked__________");
         return doctor;
    }

    public Doctor unBlockDoctor(UUID id) {
        DoctorId doctorId = new DoctorId();
        doctorId.setId(id);
        producer.sendUnBlockDoctor(doctorId);


        Doctor doctor = null;
        while (doctor == null) {
            doctor = consumer.getReceivedUnBlockedDoctor();
        }

//        Doctor doctor =consumer.getReceivedUnBlockedDoctor();
        System.out.println("unblocked");
        return doctor;


    }

    public List<Userdto> getAllUsers() {
        producer.getAllUsers();

        List<Userdto> getResponseUsers=null;
        while (getResponseUsers==null){
                getResponseUsers=consumer.getReceivedUsers();
        }

        return  getResponseUsers;

    }

    public Userdto blockUser(UUID id) {

      UserId userId = new UserId();
       userId.setId(id);
        producer.sendBlockUser(userId);

      Userdto user = null;
        while (user == null) {
          user= consumer.getReceivedBlockedUser();
        }


        System.out.println("________blocked__________");
        return user;


    }

    public List<Userdto> UnBlockUser(UUID id) {
        UserId userId = new UserId();
        userId.setId(id);
        producer.sendUnBlockUser(userId);

//        Userdto user = null;
//        while (user == null) {
//            user= consumer.getReceivedUnBlockedUser();
//        }
        List<Userdto> getResponseUsers=  consumer.getReceivedUnBlockedUser();

        return getResponseUsers;
    }


//    public List<DepartmentResponse> getAllDoctors() {
//        List<Doctors> departments = departmentRepository.findAll();
//        List<DepartmentResponse>departmentResponses = new ArrayList<>();
//        for(Department department:departments){
//            DepartmentResponse departmentResponse = new DepartmentResponse();
//            copyProperties(department,departmentResponse);
//            departmentResponses.add(departmentResponse);
//        }
//        return departmentResponses;
//    }
}
