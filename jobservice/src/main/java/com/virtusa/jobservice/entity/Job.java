package com.virtusa.jobservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(length = 5000)
    private String description;
    private String location;
    private String employmentType; // FULL_TIME, PART_TIME, INTERN
    private Double salary;
    private String companyName;
    private Long createdBy; // recruiter userId from Auth Service

    @Enumerated(EnumType.STRING)
    private JobStatus status;

}