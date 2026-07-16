package com.example.user_service.service;



import com.example.user_service.dto.LoginRequest;
import com.example.user_service.dto.LoginResponse;
import com.example.user_service.dto.RegisterRequest;
import com.example.user_service.dto.UserResponse;
import com.example.user_service.entity.Role;
import com.example.user_service.entity.User;
import com.example.user_service.exception.DuplicateUserException;
import com.example.user_service.exception.UserNotFoundException;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.security.JwtUtil;
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
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser_Success() {

        RegisterRequest request = new RegisterRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john@gmail.com");
        request.setPassword("123");
        request.setRole(Role.USER);

        User savedUser = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@gmail.com")
                .password("123")
                .role(Role.USER)
                .build();

        when(userRepository.existsByEmail(request.getEmail()))
                .thenReturn(false);

        when(userRepository.save(any(User.class)))
                .thenReturn(savedUser);

        UserResponse response = userService.createUser(request);

        assertNotNull(response);
        assertEquals("John", response.getFirstName());
        assertEquals("john@gmail.com", response.getEmail());

        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_DuplicateEmail() {

        RegisterRequest request = new RegisterRequest();
        request.setEmail("john@gmail.com");

        when(userRepository.existsByEmail("john@gmail.com"))
                .thenReturn(true);

        assertThrows(
                DuplicateUserException.class,
                () -> userService.createUser(request)
        );

        verify(userRepository, never()).save(any());
    }

    @Test
    void login_Success() {
        LoginRequest request = new LoginRequest();
        request.setEmail("john@gmail.com");
        User user = User.builder()
                .id(1L)
                .firstName("John")
                .email("john@gmail.com")
                .role(Role.USER)
                .build();

        when(userRepository.findByEmail("john@gmail.com"))
                .thenReturn(Optional.of(user));

        when(jwtUtil.generateToken(user))
                .thenReturn("jwt-token");

        LoginResponse response = userService.login(request);

        assertEquals("jwt-token", response.getToken());
        assertEquals(1L, response.getUserId());
        assertEquals("John", response.getName());
    }

    @Test
    void getUserById_Success() {

        User user = User.builder()
                .id(1L)
                .firstName("John")
                .email("john@gmail.com")
                .role(Role.USER)
                .build();

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        UserResponse response = userService.getUserById(1L);

        assertEquals("John", response.getFirstName());
    }

    @Test
    void getUserById_NotFound() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> userService.getUserById(1L)
        );
    }

    @Test
    void getAllUsers() {

        User user1 = User.builder()
                .id(1L)
                .firstName("John")
                .role(Role.USER)
                .build();

        User user2 = User.builder()
                .id(2L)
                .firstName("Jane")
                .role(Role.RECRUITER)
                .build();

        when(userRepository.findAll())
                .thenReturn(List.of(user1, user2));

        List<UserResponse> responses =
                userService.getAllUsers();

        assertEquals(2, responses.size());
    }

    @Test
    void updateUser_Success() {

        RegisterRequest request = new RegisterRequest();
        request.setFirstName("Updated");
        request.setLastName("User");
        request.setEmail("updated@gmail.com");
        request.setPassword("123");
        request.setRole(Role.USER);

        User user = User.builder()
                .id(1L)
                .firstName("John")
                .build();

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(userRepository.save(any(User.class)))
                .thenAnswer(i -> i.getArgument(0));

        UserResponse response =
                userService.updateUser(1L, request);

        assertEquals("Updated", response.getFirstName());
    }

    @Test
    void updateUser_NotFound() {

        RegisterRequest request = new RegisterRequest();

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> userService.updateUser(1L, request)
        );
    }

    @Test
    void deleteUser_Success() {

        User user = User.builder()
                .id(1L)
                .build();

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository).delete(user);
    }

    @Test
    void deleteUser_NotFound() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> userService.deleteUser(1L)
        );
    }
}

