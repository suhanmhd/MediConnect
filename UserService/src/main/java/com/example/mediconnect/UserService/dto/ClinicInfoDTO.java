package com.example.mediconnect.UserService.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClinicInfoDTO {
    private UUID id;
    private String clinicName;
    private String clinicAddress;
    private List<String> clinicImages; // List to store multiple image URLs

    // Getters and setters
}