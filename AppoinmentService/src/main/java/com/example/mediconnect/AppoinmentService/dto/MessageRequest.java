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
public class MessageRequest {


    private UUID senderId;
    private  UUID receiverId;


    private  String text;
}
