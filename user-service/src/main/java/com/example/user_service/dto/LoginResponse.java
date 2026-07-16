package com.example.user_service.dto;

import com.example.user_service.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private Long userId;
    private String name;
}