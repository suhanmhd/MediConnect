package com.example.mediconnect.DoctorService.dto;

import com.example.mediconnect.DoctorService.entity.AvailableSlot;
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
public class DoctorSlotDto {

    private UUID id;

    private List<AvailableSlotDTO> availableSlots;
}
