package com.virtusa.applicationservice.entity;



import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobResponse {

    private Long id;

    private String title;

    private String description;

    private String location;

    private String employmentType;

    private Double salary;

    private String companyName;

    private String requirements;

    private Long createdBy;

    private JobStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}