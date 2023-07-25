package com.example.mediconnect.AdminService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Id;
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

    private String image;



    private boolean enabled;

    private  String isApproved;


    private String specialization;

    private String experience;

    private List<String> timings;

    private long feesPerConsultation;

    private  String license;
}
