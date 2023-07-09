package com.example.mediconnect.AuthService.dto;

public class ErrorResponse {
    private int status;
    private String message;
    // Add any additional fields as needed

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    // Add getters and setters
}
