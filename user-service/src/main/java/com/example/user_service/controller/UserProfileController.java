package com.example.user_service.controller;

import com.example.user_service.dto.UserProfileRequest;
import com.example.user_service.dto.UserProfileResponse;
import com.example.user_service.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/{userId}/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserProfileResponse createProfile(
            @PathVariable Long userId,
            @RequestBody UserProfileRequest request) {

        return userProfileService.createProfile(
                userId,
                request);
    }

    @GetMapping
    public UserProfileResponse getProfile(
            @PathVariable Long userId) {

        return userProfileService.getProfile(userId);
    }

    @PutMapping
    public UserProfileResponse updateProfile(
            @PathVariable Long userId,
            @RequestBody UserProfileRequest request) {

        return userProfileService.updateProfile(
                userId,
                request);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(
            @PathVariable Long userId) {

        userProfileService.deleteProfile(userId);
    }
}