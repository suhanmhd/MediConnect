package com.example.mediconnect.DoctorService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailableSlotDTO {
    private LocalDate date;
    private List<String>times;
}
