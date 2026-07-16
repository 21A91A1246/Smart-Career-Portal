package com.example.user_service.service;

import com.example.user_service.dto.LoginRequest;
import com.example.user_service.dto.LoginResponse;
import com.example.user_service.dto.RegisterRequest;
import com.example.user_service.dto.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(RegisterRequest request);

    LoginResponse login(LoginRequest loginRequest);

    UserResponse getUserById(Long id);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(
            Long id,
            RegisterRequest request);

    void deleteUser(Long id);
}