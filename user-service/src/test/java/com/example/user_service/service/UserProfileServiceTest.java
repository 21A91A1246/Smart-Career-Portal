package com.example.user_service.service;

import com.example.user_service.dto.*;
import com.example.user_service.entity.*;
import com.example.user_service.exception.UserNotFoundException;
import com.example.user_service.repository.UserProfileRepository;
import com.example.user_service.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private UserProfileService userProfileService;

    @Test
    void createProfile_Success() {

        Long userId = 1L;

        User user = User.builder()
                .id(userId)
                .firstName("John")
                .email("john@gmail.com")
                .role(Role.USER)
                .build();

        ExperienceRequest experience = new ExperienceRequest();
        experience.setCompany("Virtusa");
        experience.setRole("Developer");
        experience.setDuration("2 years");
        experience.setDescription("Spring Boot Development");

        ProjectRequest project = new ProjectRequest();
        project.setTitle("Job Portal");
        project.setTechnologies("Java, Spring Boot");
        project.setDescription("Recruitment Platform");

        UserProfileRequest request = new UserProfileRequest();
        request.setHeadline("Java Developer");
        request.setSummary("Experienced Java Developer");
        request.setSkills(List.of("Java", "Spring Boot"));
        request.setExperiences(List.of(experience));
        request.setProjects(List.of(project));

        UserProfile savedProfile = UserProfile.builder()
                .userId(userId)
                .headline(request.getHeadline())
                .summary(request.getSummary())
                .skills(request.getSkills())
                .experiences(List.of(
                        Experience.builder()
                                .company("Virtusa")
                                .role("Developer")
                                .duration("2 years")
                                .description("Spring Boot Development")
                                .build()
                ))
                .projects(List.of(
                        Project.builder()
                                .title("Job Portal")
                                .technologies("Java, Spring Boot")
                                .description("Recruitment Platform")
                                .build()
                ))
                .build();

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(userProfileRepository.save(any(UserProfile.class)))
                .thenReturn(savedProfile);

        UserProfileResponse response =
                userProfileService.createProfile(userId, request);

        assertNotNull(response);
        assertEquals(userId, response.getUserId());
        assertEquals("Java Developer", response.getHeadline());

        verify(userProfileRepository).save(any(UserProfile.class));
    }

    @Test
    void createProfile_UserNotFound() {

        Long userId = 1L;

        UserProfileRequest request = new UserProfileRequest();

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> userProfileService.createProfile(userId, request)
        );

        verify(userProfileRepository, never())
                .save(any());
    }

    @Test
    void getProfile_Success() {

        Long userId = 1L;

        UserProfile profile = UserProfile.builder()
                .userId(userId)
                .headline("Java Developer")
                .summary("Spring Boot Developer")
                .skills(List.of("Java", "Spring"))
                .experiences(List.of())
                .projects(List.of())
                .build();

        when(userProfileRepository.findById(userId))
                .thenReturn(Optional.of(profile));

        UserProfileResponse response =
                userProfileService.getProfile(userId);

        assertNotNull(response);
        assertEquals(userId, response.getUserId());
        assertEquals("Java Developer", response.getHeadline());
    }

    @Test
    void getProfile_NotFound() {

        when(userProfileRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> userProfileService.getProfile(1L)
        );
    }

    @Test
    void deleteProfile_Success() {

        Long userId = 1L;

        UserProfile profile = UserProfile.builder()
                .userId(userId)
                .build();

        when(userProfileRepository.findById(userId))
                .thenReturn(Optional.of(profile));

        userProfileService.deleteProfile(userId);

        verify(userProfileRepository).delete(profile);
    }

    @Test
    void deleteProfile_NotFound() {

        when(userProfileRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> userProfileService.deleteProfile(1L)
        );
    }
}
