package com.virtusa.applicationservice.openfeign;

import com.virtusa.applicationservice.entity.UserProfileResponse;
import com.virtusa.applicationservice.entity.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "UserService",url = "http://localhost:8064/api/users")
public interface UserClient {

    @GetMapping("/{userId}/profile")
    public UserProfileResponse getProfile(@PathVariable Long userId);

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id);
}
