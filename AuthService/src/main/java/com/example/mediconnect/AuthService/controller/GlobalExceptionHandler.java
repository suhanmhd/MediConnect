package com.example.mediconnect.AuthService.controller;

import com.example.mediconnect.AuthService.exception.EmailAlreadyTakenException;
import com.example.mediconnect.AuthService.exception.InvalidLoginException;
import com.example.mediconnect.AuthService.exception.UsernameAlreadyTakenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyTakenException.class)
    public ResponseEntity<String> handleEmailAlreadyTakenException(EmailAlreadyTakenException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(UsernameAlreadyTakenException.class)
    public ResponseEntity<String> handleUsernameAlreadyTakenException(UsernameAlreadyTakenException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<String> handleInvalidLoginException(InvalidLoginException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }


    // Other exception handlers

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        // Handle generic exceptions
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("invalid credentials");
    }
}
