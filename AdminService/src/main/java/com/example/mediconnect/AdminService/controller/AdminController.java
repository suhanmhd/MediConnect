package com.example.mediconnect.AdminService.controller;

import com.example.mediconnect.AdminService.dto.ApproveRequest;
import com.example.mediconnect.AdminService.dto.DepartmentResponse;
import com.example.mediconnect.AdminService.dto.Doctor;
import com.example.mediconnect.AdminService.dto.Userdto;
import com.example.mediconnect.AdminService.entity.Department;
import com.example.mediconnect.AdminService.kafka.Producer;
import com.example.mediconnect.AdminService.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
//@PreAuthorize("hasRole('ADMIN')")
public class AdminController {





    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


@PostMapping("/add/department")
public ResponseEntity<String>addDepartment(@RequestBody Department department){
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



    @GetMapping("/doctors")
    public ResponseEntity<Map<String, List>> getAllDoctors(){
        List<Doctor> responseDoctors=adminService.getAllDoctors();
        Map<String, List> response = new HashMap<>();
        response.put("doctorDetails",responseDoctors);
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }


    @GetMapping("/users")
    public ResponseEntity<Map<String, List>>getAllUsers(){
        List<Userdto> responseUsers=adminService.getAllUsers();
        Map<String, List> response = new HashMap<>();
        response.put("userDetails",responseUsers);
        return  new ResponseEntity<>(response,HttpStatus.CREATED);
    }


    @GetMapping("/pendingApprovals")
    public ResponseEntity<Map<String, List>> getpendingApprovals(){
               List<Doctor> pendingApprovals=adminService.getpendingApprovals();
        Map<String, List> response = new HashMap<>();
        response.put("approvalDetails",pendingApprovals);
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }


    @PostMapping("/approve")
    public ResponseEntity<Void> ApproveDoctor(@RequestBody ApproveRequest request) {
        adminService.ApproveDoctor(request);
        System.out.println(request);
        return ResponseEntity.noContent().header("Location", "/pendingApprovals").build();
    }
@GetMapping("/blockDoctor/{id}")
public ResponseEntity<Map<String, Doctor>> blockDoctor(@PathVariable("id") UUID id){

        Doctor doctor=adminService.blockDoctor(id);
    System.out.println(doctor);
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
    public ResponseEntity<Map<String,Userdto>> blockUser(@PathVariable("userId") UUID id){

    Userdto user=adminService.blockUser(id);
        System.out.println(id);
        Map<String,Userdto> response = new HashMap<>();
        response.put("userDetails",user);
        System.out.println(response);
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }


    @GetMapping("/unblockUser/{userId}")
    public ResponseEntity<Map<String,List>> unBlockUser(@PathVariable("userId") UUID id){

        List<Userdto> user=adminService.UnBlockUser(id);
        System.out.println(id);
        Map<String,List> response = new HashMap<>();
        response.put("userDetails",user);
        System.out.println(response);

        return  new ResponseEntity<>(response,HttpStatus.OK);
    }


}


