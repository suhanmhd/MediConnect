package com.example.mediconnect.AuthService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorDetails {
    private UUID id;
    private String firstname;


    private String lastname;

    private String email;


    private String name;

    private String password;

    private boolean enabled;

    private String isApproved;

    private List<String> timings;


    private String specialization;

    private String experience;

    private long feesPerConsultation;

    private  String license;
}
