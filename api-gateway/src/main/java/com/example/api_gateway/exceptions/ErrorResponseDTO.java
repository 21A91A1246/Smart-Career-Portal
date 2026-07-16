package com.example.api_gateway.exceptions;

import java.time.LocalDateTime;
public class ErrorResponseDTO {

    private String message;
    private String errorCode;
    private int status;
    private LocalDateTime timestamp;

    public ErrorResponseDTO() {}

    public ErrorResponseDTO(String message, String errorCode, int status, LocalDateTime timestamp) {
        this.message = message;
        this.errorCode = errorCode;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}