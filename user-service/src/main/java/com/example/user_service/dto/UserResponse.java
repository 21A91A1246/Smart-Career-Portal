package com.example.user_service.dto;

import com.example.user_service.entity.Role;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private String companyName;
}