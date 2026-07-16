package com.example.user_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserProfileRequest {
    private String headline;
    private String summary;
    private List<String> skills;
    private List<ExperienceRequest> experiences;
    private List<ProjectRequest> projects;
}