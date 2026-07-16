package com.example.user_service.dto;

import lombok.Data;

@Data
public class ProjectRequest {
    private String title;
    private String technologies;
    private String description;
}