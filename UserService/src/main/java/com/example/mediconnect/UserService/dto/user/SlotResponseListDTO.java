package com.example.mediconnect.UserService.dto.user;

import com.example.mediconnect.UserService.dto.doctor.AvailableSlotResonseDTO;
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
public class SlotResponseListDTO {
    private UUID doctorId;

    private List<AvailableSlotToUserDTO>slotList;
}
