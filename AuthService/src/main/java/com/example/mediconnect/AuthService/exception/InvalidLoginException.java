package com.example.mediconnect.AuthService.exception;

import lombok.Data;

@Data
public class InvalidLoginException extends RuntimeException {
    public InvalidLoginException(String message) {
        super(message);
    }
}
