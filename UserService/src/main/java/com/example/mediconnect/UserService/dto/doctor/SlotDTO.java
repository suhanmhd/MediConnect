package com.example.mediconnect.UserService.dto.doctor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SlotDTO {
    private UUID doctorId;
    private LocalDate date;
    private String startTime;
    private String endTime;
    private boolean status;
}
