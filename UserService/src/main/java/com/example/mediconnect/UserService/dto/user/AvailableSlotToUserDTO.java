package com.example.mediconnect.UserService.dto.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailableSlotToUserDTO {
    private LocalDate date;
//    private UUID doctorId;
    private List<SlotResponseToUserDTO> slots;


//    public AvailableSlotToUserDTO(LocalDate date, List<SlotResponseToUserDTO> slotDTOs) {
//
//    }
}
