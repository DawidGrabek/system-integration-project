package com.project.system_integration.services;

import com.project.system_integration.entities.User;
import com.project.system_integration.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthService authService;

    @InjectMocks
    private UserService userService;

    @Test
    void getAllUsers_ReturnsListOfUsers() {
        User user1 = new User(); // Assuming User has a default constructor
        User user2 = new User();
        // Setup mock response
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        // Create a mock headers map
        Map<String, String> headers = new HashMap<>();

        // Act
        List<User> users = userService.getAllUsers(headers);

        // Assert
        assertNotNull(users);
        assertEquals(2, users.size());
        verify(userRepository).findAll();
    }

}