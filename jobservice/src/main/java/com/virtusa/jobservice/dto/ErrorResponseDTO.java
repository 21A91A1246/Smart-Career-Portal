package com.virtusa.jobservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ErrorResponseDTO {

    private LocalDateTime timestamp;
    private String message;
    private String errorCode;
    private int status;
    private String path;
}