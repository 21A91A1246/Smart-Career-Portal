package com.virtusa.jobservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "job_applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long jobId;
    private Long userId;
    private String resumeUrl;
    private String coverLetter;
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
    private LocalDateTime appliedAt;
}