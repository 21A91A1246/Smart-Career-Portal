package com.virtusa.jobservice.dto;

import com.virtusa.jobservice.entity.ApplicationStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApplicationResponse {

    private Long id;

    private Long jobId;

    private Long userId;

    private String resumeUrl;

    private String coverLetter;

    private ApplicationStatus status;

    private LocalDateTime appliedAt;
}