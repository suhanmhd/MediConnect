package com.example.mediconnect.UserService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentData {

    private UUID docId;

    private UUID  slotId;
    private String date;
    private String time;



}