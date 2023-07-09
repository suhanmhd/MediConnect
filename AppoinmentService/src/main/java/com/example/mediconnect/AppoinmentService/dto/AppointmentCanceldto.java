package com.example.mediconnect.AppoinmentService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentCanceldto {

    private UUID appointmentId;
    private  String status;
}
