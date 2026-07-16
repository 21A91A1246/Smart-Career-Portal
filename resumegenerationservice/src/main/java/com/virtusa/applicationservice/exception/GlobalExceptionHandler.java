package com.virtusa.applicationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AIServiceException.class)
    public ResponseEntity<?> handleAI(AIServiceException ex) {

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                        "error", "AI_SERVICE_ERROR",
                        "message", ex.getMessage(),
                        "timestamp", LocalDateTime.now()
                ));
    }
}
