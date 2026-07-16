package com.example.user_service.dto;

import com.example.user_service.entity.Experience;
import com.example.user_service.entity.Project;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserProfileResponse {

    private Long userId;

    private String headline;

    private String summary;

    private List<String> skills;

    private List<Experience> experiences;

    private List<Project> projects;
}