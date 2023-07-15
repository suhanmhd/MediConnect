package com.example.mediconnect.UserService.kafka;

import com.example.mediconnect.UserService.dto.DepartmentResponse;
import com.example.mediconnect.UserService.dto.Doctor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
@Component

public class DepartmentConsumer {




    private List<DepartmentResponse>recievedDepartmentResponse;

    private List<Doctor> receivedDoctors;

    private Doctor receivedDoctor;

    @KafkaListener(topics ="send_department_to_user_topic", groupId = "foo")
    public void departmentDetails(String message) {
        System.out.println(message);



        ObjectMapper object = new ObjectMapper();

        DepartmentResponse[] department = null;
        List<DepartmentResponse> departmentResponse=null;
        try {
            department = object.readValue(message, DepartmentResponse[].class);
            departmentResponse = Arrays.asList(department);
            System.out.println(departmentResponse);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        this.recievedDepartmentResponse = departmentResponse;

    }

    public List<DepartmentResponse>getRecievedDepartmentResponse(){

        return recievedDepartmentResponse;
    }







    @KafkaListener(topics = "send_all_doctor_by_department_topic", groupId = "foo")
    public void doctorByDepartment(String message) {
        System.out.println(message+"ssssssssssssssss");

        ObjectMapper object = new ObjectMapper();

        Doctor[] doctors = null;
        List<Doctor> doctorList  = null;
        try {
            doctors = object.readValue(message, Doctor[].class);
            doctorList = Arrays.asList(doctors);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        this.receivedDoctors=doctorList;
        System.out.println(doctorList);
    }
    public List<Doctor>getReceivedDoctors() {

        return receivedDoctors;
    }


@Transactional
    @KafkaListener(topics = "send_all_doctor_by_id_topic", groupId = "foo")
    public void doctorBy(String message) {
        System.out.println(message+"ssssssssssssssss");

        ObjectMapper object = new ObjectMapper();

        Doctor doctor = null;

        try {
            doctor = object.readValue(message, Doctor.class);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        this.receivedDoctor=doctor;

    }
    public Doctor getReceivedDoctor() {

        return receivedDoctor;
    }



}
