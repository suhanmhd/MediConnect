package com.example.mediconnect.UserService.dto.doctor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class SlotResponseDTO {
//    private UUID doctorId;

//    private LocalDate date;
    private  UUID id;
    private String startTime;
    private String endTime;
    private boolean status;



}
