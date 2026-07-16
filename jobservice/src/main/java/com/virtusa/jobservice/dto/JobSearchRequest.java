package com.virtusa.jobservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobSearchRequest {

    private String keyword;

    private String location;

    private String companyName;

    private String employmentType;
}