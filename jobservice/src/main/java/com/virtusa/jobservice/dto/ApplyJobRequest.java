package com.virtusa.jobservice.dto;

import lombok.Data;

@Data
public class ApplyJobRequest {

    private Long jobId;

    private Long userId;

    private String resumeUrl;

    private String coverLetter;
}