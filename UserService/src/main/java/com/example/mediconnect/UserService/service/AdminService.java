package com.example.mediconnect.UserService.service;


import com.example.mediconnect.UserService.dto.*;
import com.example.mediconnect.UserService.entity.doctor.Department;
import com.example.mediconnect.UserService.entity.doctor.DoctorCredentials;
import com.example.mediconnect.UserService.entity.user.UserDetails;

import com.example.mediconnect.UserService.kafka.adminkafka.AdminProducer;
import com.example.mediconnect.UserService.repository.DepartmentRepository;
import com.example.mediconnect.UserService.repository.DoctorRepository;
import com.example.mediconnect.UserService.repository.UserDetailsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
public class AdminService {


@Autowired
    private DepartmentRepository departmentRepository;
@Autowired
    private UserDetailsRepository userDetailsRepository;
@Autowired
private DoctorRepository doctorRepository;

@Autowired
private AdminProducer adminProducer;

//    @Autowired
//    private ResponseHolder<List<Userdto>> doctorResponse;




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

    }

    public void deleteDepartmentById(UUID id) {
        if(!departmentRepository.existsById(id)){
            throw new RuntimeException("department with given id not found");
        }
        departmentRepository.deleteById(id);
    }


    public Department getDepartmentById(UUID id) {
              Department department=departmentRepository.getById(id);
        return department;
    }

    public List<DepartmentResponse> getAllDepartmentsForDoctorSignup() {

        List<Department> departments = departmentRepository.findAll();
        List<DepartmentResponse>departmentResponses = new ArrayList<>();
        for(Department department:departments){
            DepartmentResponse departmentResponse = new DepartmentResponse();
            copyProperties(department,departmentResponse);
            departmentResponses.add(departmentResponse);
        }
        return departmentResponses;
    }
//         GET ALL USERS FOR ADMIN
    public List<Userdto> getAllUsers() {
        List<Userdto>userdto =new ArrayList<>();
        List<UserDetails>  userdetails =userDetailsRepository.findAll();

        for(UserDetails users: userdetails){
            Userdto user = new Userdto();
            BeanUtils.copyProperties(users,user);
            userdto.add(user);

        }


        return userdto;

    }

//        GET ALL DOCTORS FOR ADMIN
    public List<Doctor> getAllDoctors() {

        List<DoctorCredentials>doctorCredentials =doctorRepository.findAll();
        List<Doctor>doctorList =new ArrayList<>();

        for(DoctorCredentials doctors: doctorCredentials){
            Doctor doctor = new Doctor();
            BeanUtils.copyProperties(doctors,doctor);
            doctorList.add(doctor);

        }
        return  doctorList;

    }

    public Userdto blockUser(UUID id) {

        UserId userId = new UserId();
       userId.setId(id);

        //KAFKA PRODUCER TO SEND USER ID TO THE AUTH SERVICE
        adminProducer.sendBlockUser(userId);
        UserDetails  user=userDetailsRepository.getById(id);
        user.setEnabled(false);
        userDetailsRepository.save(user);
        UserDetails userDetails=userDetailsRepository.getById(id);
        Userdto userdto = new Userdto();
        copyProperties(userDetails,userdto);
        return  userdto;
    }

    public Userdto UnBlockUser(UUID id) {
        UserId userId = new UserId();
        userId.setId(id);

        //KAFKA PRODUCER TO SEND USER ID TO THE AUTH SERVICE
        adminProducer.sendUnBlockUser(userId);
        UserDetails  user=userDetailsRepository.getById(id);
        user.setEnabled(true);
        userDetailsRepository.save(user);
        UserDetails userDetails=userDetailsRepository.getById(id);
        Userdto userdto = new Userdto();
        copyProperties(userDetails,userdto);
        return  userdto;
    }


    public List<Doctor> getpendingApprovals() {
        List<DoctorCredentials> doctorCredentials = doctorRepository.findByIsApproved("pending");
        List<Doctor>pendingApprovals = new ArrayList<>();


        for(DoctorCredentials doctors:doctorCredentials){
            Doctor doctor = new Doctor();
            copyProperties(doctors,doctor);
            pendingApprovals.add(doctor);
        }

        return pendingApprovals;
    }

    public Doctor ApproveDoctor(ApproveRequest request) {
        Optional<DoctorCredentials> optionalDoctorCredentials = doctorRepository.findById(request.getId());


            DoctorCredentials doctorCredentials = optionalDoctorCredentials.get();
            doctorCredentials.setIsApproved(request.getStatus());

            DoctorCredentials approvedDoctor = doctorRepository.save(doctorCredentials);

            Doctor doctor = new Doctor();
            copyProperties(approvedDoctor,doctor);
            return doctor;

        }


    public Doctor blockDoctor(UUID id) {
        DoctorId doctorId = new DoctorId();
        doctorId.setId(id);
        adminProducer.sendBlockDoctor(doctorId);
              DoctorCredentials doctorCredentials= doctorRepository.getById(id);
              doctorCredentials.setEnabled(false);
              doctorRepository.save(doctorCredentials);
              DoctorCredentials doctorData =doctorRepository.getById(id);
              Doctor doctor = new Doctor();
              copyProperties(doctorData,doctor);
              System.out.println("________blocked__________"+doctor);
         return doctor;
    }

    public Doctor unBlockDoctor(UUID id) {
        DoctorId doctorId = new DoctorId();
        doctorId.setId(id);
        adminProducer.sendUnBlockDoctor(doctorId);
        DoctorCredentials doctorCredentials= doctorRepository.getById(id);
        doctorCredentials.setEnabled(true);
        doctorRepository.save(doctorCredentials);
        DoctorCredentials doctorData =doctorRepository.getById(id);

        Doctor doctor = new Doctor();
        copyProperties(doctorData,doctor);
        System.out.println("________un--blocked__________"+doctor);
        return doctor;
    }



}
