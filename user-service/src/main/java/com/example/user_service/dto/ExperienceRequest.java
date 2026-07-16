package com.example.user_service.dto;

import lombok.Data;

@Data
public class ExperienceRequest {

    private String company;
    private String role;
    private String duration;
    private String description;
}