package com.example.mediconnect.UserService.dto;

import com.example.mediconnect.UserService.dto.AvailableSlotDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorSlotDto {

    private UUID doctor_id;

    private AvailableSlotDTO available_slots;
    private Map<String, Boolean> timeSlotAvailability;
}
