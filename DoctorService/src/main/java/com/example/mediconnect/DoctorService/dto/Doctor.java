package com.example.mediconnect.DoctorService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

//    @Id
//    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;
    private String firstname;


    private String lastname;

    private String email;


    private String name;



    private boolean enabled;

    private  String isApproved;

    private List<String> timings;


    private String specialization;

    private String experience;

    private long feesPerConsultation;

    private String image;

    private  String license;
}
