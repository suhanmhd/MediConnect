package com.example.mediconnect.UserService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EducationDTO {
    private UUID id;
    private String university;
    private String degree;
    private int startYear;
    private int endYear;

    // Getters and setters
}
