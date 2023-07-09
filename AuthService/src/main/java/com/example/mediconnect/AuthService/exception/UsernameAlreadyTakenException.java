package com.example.mediconnect.AuthService.exception;

import lombok.Data;

@Data
public class UsernameAlreadyTakenException extends RuntimeException {
    public UsernameAlreadyTakenException(String message) {
        super(message);
    }
}