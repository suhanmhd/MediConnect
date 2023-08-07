package com.example.mediconnect.UserService.dto;

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

    private String location;

    private String about;


    private String specialization;

    private String experience;

    private String image;

    private long feesPerConsultation;

    private  String license;

    private List<JobHistoryDTO> jobHistoryList;
    private List<EducationDTO> educationList;
    private ClinicInfoDTO clinicInfo;
}
