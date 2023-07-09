package com.example.mediconnect.UserService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import java.sql.Timestamp;
import java.util.UUID;

@Data

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Appointment{


     private UUID id;
     private String userId;


     private String doctorId;

     private String userInfo;

     private String doctorInfo;

     private  String status;

     private long amount;

     private  String paymentStatus ;

     private String date;
     private String time;
     private Timestamp createdDate;


     private Timestamp lastModifiedDate;
}
