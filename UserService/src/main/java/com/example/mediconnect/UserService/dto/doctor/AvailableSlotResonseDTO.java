package com.example.mediconnect.UserService.dto.doctor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailableSlotResonseDTO {
    private LocalDate date;
    private UUID doctorId;
    private List<SlotResponseDTO> slots;




    public AvailableSlotResonseDTO(UUID id, List<SlotResponseDTO> slotDTOs) {

    }
}
