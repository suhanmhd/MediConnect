package com.example.mediconnect.AuthService.exception;

import lombok.Data;

@Data
public class EmailAlreadyTakenException extends RuntimeException {
    public EmailAlreadyTakenException(String message) {
        super(message);
    }
}
