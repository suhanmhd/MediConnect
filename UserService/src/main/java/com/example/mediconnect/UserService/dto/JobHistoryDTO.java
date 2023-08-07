package com.example.mediconnect.UserService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class JobHistoryDTO {
    private UUID id;
    private String clinicName;
    private int startYear;
    private String endYear;
    private int duration;

    // Getters and setters
}
