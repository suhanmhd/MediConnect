package com.example.mediconnect.AdminService.kafka;

import com.example.mediconnect.AdminService.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerDept {

    @Autowired
    private AdminService adminService;
    @KafkaListener(topics = "get_all_department_topic_to_user", groupId = "foo")
    public void getAllDepartments(String message) {
       adminService.getAllDepartmentsToUser();


    }
}
