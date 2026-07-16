package com.virtusa.jobservice.dto;


import com.virtusa.jobservice.entity.JobStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobRequest {
    @NotBlank(message = "Job title can not be null")
    private String title;
    @NotBlank(message = "Job description can not be null")
    private String description;
    @NotBlank(message = "Job location can not be null")
    private String location;
    @NotBlank(message = "Employeement can not be null")
    private String employmentType;
    @NotNull(message = "Salary can not be null")
    @Positive(message = "Salary must be greater than 0")
    private Double salary;
    @NotBlank(message = "Company name can not be null")
    private String companyName;
    @NotNull(message = "Recruiter id can not be null")
    private Long createdBy;
    private JobStatus status;
}