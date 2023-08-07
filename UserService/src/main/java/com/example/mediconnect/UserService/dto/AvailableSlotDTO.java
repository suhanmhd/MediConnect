package com.example.mediconnect.UserService.dto;

import com.example.mediconnect.UserService.dto.doctor.SlotDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailableSlotDTO {
    private UUID id;

    private Date date;
    private List<SlotDTO> slots;

//    private String day;
//    private List<String>times;
}
