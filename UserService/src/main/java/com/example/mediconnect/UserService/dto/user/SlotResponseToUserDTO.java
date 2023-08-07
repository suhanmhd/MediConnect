package com.example.mediconnect.UserService.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SlotResponseToUserDTO {
    private UUID id;
    private String startTime;
    private String endTime;
    private boolean status;
}
