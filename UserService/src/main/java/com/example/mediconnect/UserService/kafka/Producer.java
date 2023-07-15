package com.example.mediconnect.UserService.kafka;

import com.example.mediconnect.UserService.dto.AppointmentCanceldto;
import com.example.mediconnect.UserService.dto.AppointmentReq;
import com.example.mediconnect.UserService.dto.DoctorId;
import com.example.mediconnect.UserService.dto.Userdto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class Producer {
    @Autowired
    private  KafkaTemplate<String, String> kafkaTemplate;

    @Value("${user.department.topic}")
    private String topicName;

    @Value("${user.doctorBydptmnt.topic}")
    private String topicName_Dpt;

    @Value("${user.doctorByid.topic}")
    private String Get_Doctor_topicName;

    @Value("${user.block.res.topic}")
    private String UserBlocktopicName;

    @Value("${user.Unblock.res.topic}")
    private String UserUnBlocktopicName;

    @Value("${user.cancel.appointment.topic}")
   private String cancelAppointemntTopicName;



    @Value("${user.get.topic}")
    private String getUserTopicName;

    @Value("${user.book.appointment.topic}")
    String bookingAppoinmentTopicName;

    @Value("${user.view.appointment.topic}")
    String viewAppoinmentTopicName;

    @Value("${user.get.appointment.time.topic}")
    String getAppointmentTimeTopicName;





    public void getAllDepartments() {

            ObjectMapper om = new ObjectMapper();


            String message = "get all departments";


            kafkaTemplate.send(topicName, message);
        }


    public void getDoctorByDepartment(String departmentName) {
        kafkaTemplate.send(topicName_Dpt, departmentName);
    }

    public void getDoctorById(DoctorId doctorId) {


        ObjectMapper om = new ObjectMapper();


        String message = null;
        try {
            message = om.writeValueAsString(doctorId);
        } catch (
                JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(message);



        kafkaTemplate.send(Get_Doctor_topicName, message);
    }

    public void sendAllUsers(List<Userdto> userResponse) {


        ObjectMapper om = new ObjectMapper();


        String message = null;
        try {
            message = om.writeValueAsString(userResponse);
        } catch (
                JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(message);

        kafkaTemplate.send(getUserTopicName, message);
    }

    public void sendblockUserRes(Userdto userdto) {


            ObjectMapper om = new ObjectMapper();


            String message = null;
            try {
                message = om.writeValueAsString(userdto);
            } catch (
                    JsonProcessingException e) {
                e.printStackTrace();
            }
            System.out.println("message blocked user"+message);
            kafkaTemplate.send(UserBlocktopicName, message);


    }

    public void sendUnblockUserRes(List<Userdto> userdto) {


        ObjectMapper om = new ObjectMapper();


        String message = null;
        try {
            message = om.writeValueAsString(userdto);
        } catch (
                JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(message);
        kafkaTemplate.send(UserUnBlocktopicName, message);


    }

    public void bookingAppoinment(AppointmentReq appointmentReq) {

        ObjectMapper om = new ObjectMapper();

        String message = null;
        try {
            message = om.writeValueAsString(appointmentReq);
        }
        catch (JsonProcessingException e){
            e.printStackTrace();
        }

        kafkaTemplate.send(bookingAppoinmentTopicName, message);
    }

    public void viewAppointments(UUID id) {
       String message= id.toString();
        kafkaTemplate.send(viewAppoinmentTopicName, message);
    }

    public void cancelAppointemnt(AppointmentCanceldto appointmentCanceldto) {

        ObjectMapper om = new ObjectMapper();

        String message = null;
        try {
            message = om.writeValueAsString(appointmentCanceldto);
        }
        catch (JsonProcessingException e){
            e.printStackTrace();
        }
        kafkaTemplate.send(cancelAppointemntTopicName, message);
    }

    public void getAllAppointmentsByDoctorId(UUID docId) {
        String message= docId.toString();
        kafkaTemplate.send(getAppointmentTimeTopicName, message);

    }
}

