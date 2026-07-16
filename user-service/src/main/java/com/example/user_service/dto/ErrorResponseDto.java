package com.example.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorResponseDto {
    private String message;
    private String errorCode;
    private int status;
    private LocalDateTime timestamp;
}
