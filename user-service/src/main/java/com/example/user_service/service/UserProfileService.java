package com.example.user_service.service;

import com.example.user_service.dto.ExperienceRequest;
import com.example.user_service.dto.ProjectRequest;
import com.example.user_service.dto.UserProfileRequest;
import com.example.user_service.dto.UserProfileResponse;
import com.example.user_service.entity.Experience;
import com.example.user_service.entity.Project;
import com.example.user_service.entity.User;
import com.example.user_service.entity.UserProfile;
import com.example.user_service.exception.UserNotFoundException;
import com.example.user_service.repository.UserProfileRepository;
import com.example.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    public UserProfileResponse createProfile(Long userId, UserProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        UserProfile profile = UserProfile.builder()
                .userId(userId)
                .headline(request.getHeadline())
                .summary(request.getSummary())
                .skills(request.getSkills())
                .experiences(
                        request.getExperiences()
                                .stream()
                                .map(this::mapExperience)
                                .collect(Collectors.toList())
                )
                .projects(
                        request.getProjects()
                                .stream()
                                .map(this::mapProject)
                                .collect(Collectors.toList())
                )
                .build();

        return mapToResponse(userProfileRepository.save(profile));
    }

    public UserProfileResponse getProfile(Long userId) {
        UserProfile profile =
                userProfileRepository.findById(userId)
                        .orElseThrow(() -> new UserNotFoundException("Profile not found"));
        return mapToResponse(profile);
    }

    public UserProfileResponse updateProfile(
            Long userId,
            UserProfileRequest request) {

        UserProfile profile =
                userProfileRepository.findById(userId)
                        .orElseThrow(() ->
                                new UserNotFoundException("Profile not found"));

        profile.setHeadline(request.getHeadline());
        profile.setSummary(request.getSummary());
        profile.setSkills(request.getSkills());

        profile.getExperiences().clear();
        profile.getExperiences().addAll(
                request.getExperiences()
                        .stream()
                        .map(this::mapExperience)
                        .toList()
        );

        profile.getProjects().clear();
        profile.getProjects().addAll(
                request.getProjects()
                        .stream()
                        .map(this::mapProject)
                        .toList()
        );

        return mapToResponse(userProfileRepository.save(profile));
    }

    public void deleteProfile(Long userId) {

        UserProfile profile =
                userProfileRepository.findById(userId)
                        .orElseThrow(() -> new UserNotFoundException("Profile not found"));
        userProfileRepository.delete(profile);
    }

    private Experience mapExperience(
            ExperienceRequest request) {

        return Experience.builder()
                .company(request.getCompany())
                .role(request.getRole())
                .duration(request.getDuration())
                .description(request.getDescription())
                .build();
    }

    private Project mapProject(
            ProjectRequest request) {

        return Project.builder()
                .title(request.getTitle())
                .technologies(request.getTechnologies())
                .description(request.getDescription())
                .build();
    }

    private UserProfileResponse mapToResponse(
            UserProfile profile) {

        return UserProfileResponse.builder()
                .userId(profile.getUserId())
                .headline(profile.getHeadline())
                .summary(profile.getSummary())
                .skills(profile.getSkills())
                .experiences(profile.getExperiences())
                .projects(profile.getProjects())
                .build();
    }
}