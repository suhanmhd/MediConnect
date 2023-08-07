package com.example.mediconnect.UserService.controller;

import com.example.mediconnect.UserService.dto.ApproveRequest;
import com.example.mediconnect.UserService.dto.DepartmentResponse;
import com.example.mediconnect.UserService.dto.Doctor;
import com.example.mediconnect.UserService.dto.Userdto;
import com.example.mediconnect.UserService.entity.doctor.Department;
import com.example.mediconnect.UserService.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user/admin")
//@PreAuthorize("hasRole('ADMIN')")
public class AdminController {




  @Autowired
    private AdminService adminService;




    @PostMapping("/add/department")
    public ResponseEntity<String>addDepartment(@RequestBody Department department){
        System.out.println("hlell");
        System.out.println(department);
        String dept= adminService.addDepartment(department);
        return new ResponseEntity<>(dept,HttpStatus.CREATED);
    }

    @GetMapping("/departments")
    public ResponseEntity<Map<String, List>> getAllDepartments(){
        List<DepartmentResponse> departmentResponses= adminService.getAllDepartments();
        Map<String, List> response = new HashMap<>();
        response.put("categoryDetails",departmentResponses );
        return  new ResponseEntity<>(response,HttpStatus.OK);

    }


    @GetMapping("/department")
    public ResponseEntity<Map<String, List>> getAllDepartmentsForDoctorSignup(){
        List<DepartmentResponse> departmentResponses= adminService.getAllDepartmentsForDoctorSignup();
        Map<String, List> response = new HashMap<>();
        response.put("categoryDetails",departmentResponses );
        return  new ResponseEntity<>(response,HttpStatus.OK);

    }

    @GetMapping ("/department/{id}")
    public  ResponseEntity<Department> getDepartmentById(@PathVariable("id")UUID id){
        Department department=adminService.getDepartmentById(id);
        return  new ResponseEntity<>(department,HttpStatus.OK);
    }




    @GetMapping ("/delete/department/{id}")
    public  ResponseEntity<Map<String, List>> deleteDepartmentById(@PathVariable("id")UUID id){
        adminService.deleteDepartmentById(id);
        return ResponseEntity.noContent().header("Location", "/departments").build();
    }

    @GetMapping("/users")
    public ResponseEntity<Map<String, List>>getAllUsers(){

        List<Userdto> responseUsers=adminService.getAllUsers();
        Map<String, List> response = new HashMap<>();
        response.put("userDetails",responseUsers);

        return  new ResponseEntity<>(response,HttpStatus.CREATED);
    }


    @GetMapping("/doctors")
    public ResponseEntity<Map<String, List>> getAllDoctors(){

        List<Doctor> responseDoctors= adminService.getAllDoctors();
        Map<String, List> response = new HashMap<>();
        response.put("doctorDetails",responseDoctors);

        return  new ResponseEntity<>(response,HttpStatus.OK);
    }



//    @GetMapping("/doctors")
//    public ResponseEntity<Map<String, List<Doctor>>> getAllDoctors() {
//
//        CompletableFuture<List<Doctor>> doctorsFuture = adminService.getAllDoctors();
//
//        try {
//            List<Doctor> responseDoctors = doctorsFuture.get(); // This will block until the future is completed
//            Map<String, List<Doctor>> response = new HashMap<>();
//
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


    @GetMapping("/pendingApprovals")
    public ResponseEntity<Map<String, List>> getpendingApprovals(){
        List<Doctor> pendingApprovals=adminService.getpendingApprovals();
        Map<String, List> response = new HashMap<>();
        response.put("approvalDetails",pendingApprovals);
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }


    @PostMapping("/approve")
    public ResponseEntity<Map<String,Doctor>> ApproveDoctor(@RequestBody ApproveRequest request) {
       Doctor doctor=adminService.ApproveDoctor(request);
        System.out.println(request);
        Map<String,Doctor> response = new HashMap<>();
        response.put("approvalDetails",doctor);
        return  new ResponseEntity<>(response,HttpStatus.OK);

    }
    @GetMapping("/blockDoctor/{id}")
    public ResponseEntity<Map<String, Doctor>> blockDoctor(@PathVariable("id") UUID id){

        Doctor doctor=adminService.blockDoctor(id);

        Map<String,Doctor> response = new HashMap<>();
        response.put("doctorDetails",doctor);
        System.out.println(response);
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }




    @GetMapping("/unblockDoctor/{id}")
    public ResponseEntity<Map<String, Doctor>>unBlockDoctor(@PathVariable("id") UUID id){

        Doctor doctor=adminService.unBlockDoctor(id);
        System.out.println(doctor);
        Map<String,Doctor> response = new HashMap<>();
        response.put("doctorDetails",doctor);
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }


    @GetMapping("/blockUser/{userId}")
    public ResponseEntity<Map<String,Object>> blockUser(@PathVariable("userId") UUID id){

        Userdto user=adminService.blockUser(id);
        System.out.println(id);
        Map<String,Object> response = new HashMap<>();
        response.put("userDetails",user);

        return  new ResponseEntity<>(response,HttpStatus.OK);
    }
//
//
    @GetMapping("/unblockUser/{userId}")
    public ResponseEntity<Map<String,Object>> unBlockUser(@PathVariable("userId") UUID id){

        Userdto user=adminService.UnBlockUser(id);
        System.out.println(id);
        Map<String,Object> response = new HashMap<>();
        response.put("userDetails",user);


        return  new ResponseEntity<>(response,HttpStatus.OK);
    }


}


