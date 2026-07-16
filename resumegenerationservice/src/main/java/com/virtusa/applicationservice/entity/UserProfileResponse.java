package com.virtusa.applicationservice.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {

    private Long userId;

    private String headline;

    private String summary;

    private List<String> skills;

    private List<Experience> experiences;

    private List<Project> projects;
}