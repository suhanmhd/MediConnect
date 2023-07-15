package com.example.mediconnect.AppoinmentService.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Appointment extends BaseEntity {



     private String userId;


     private String doctorId;

   private String userInfo;

     private String doctorInfo;
//    @Builder.Default
     private  String status="pending";

     private long amount;
//    @Builder.Default
     private  String paymentStatus = "pending";

     private String date;
     private String time;

}
