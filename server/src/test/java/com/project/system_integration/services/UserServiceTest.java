//package com.project.system_integration.services;
//
//import com.project.system_integration.entities.Role;
//import com.project.system_integration.entities.User;
//import com.project.system_integration.models.UserDto;
//import com.project.system_integration.exceptions.UnauthorizedException;
//import com.project.system_integration.repositories.UserRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentMatchers;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class UserServiceTest {
//    @Mock
//    private UserRepository userRepository;
//    @Mock
//    private AuthService auth;
//
//    @InjectMocks
//    private UserService userService;
//    private UserDto userDto;
//
//    @BeforeEach
//    void setUp() throws Exception {
//        userDto = new UserDto("Login", "USER");
//        when(auth.authenticateAdmin(ArgumentMatchers.any())).thenReturn(userDto);
//    }
//    @Test
//    void getAllUsers_Success() {
//        // Mock the behavior of userRepository.findAll to return an empty list
//        when(userRepository.findAll()).thenReturn(new ArrayList<User>());
//
//        // Mock the headers with authorization information
//        Map<String, String> headers = new HashMap<>();
//        headers.put("authorization", "Bearer mockedJwtToken");
//
//        // Call the method under test
//        ResponseEntity response = userService.getAllUsers(headers);
//
//        // Assertions
//        Assertions.assertThat(response).isNotNull();
//        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        // Check that the response body is a list
//        Assertions.assertThat(response.getBody()).isInstanceOf(List.class);
//    }
//
//    @Test
//    void getAllUsers_Unauthorized() throws Exception {
//        // Mock the AuthService to throw an UnauthorizedException
//        when(auth.authenticateAdmin(ArgumentMatchers.any())).thenThrow(new UnauthorizedException("Unauthorized"));
//
//        // Mock the headers with authorization information
//        Map<String, String> headers = new HashMap<>();
//        headers.put("authorization", "Bearer mockedJwtToken");
//
//        // Call the method under test
//        ResponseEntity response = userService.getAllUsers(headers);
//
//        // Assertions
//        Assertions.assertThat(response).isNotNull();
//        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
//    }
//
//    @Test
//    void getAllUsers_InternalServerError() {
//        // Mock the behavior of userRepository.findAll to throw an exception
//        when(userRepository.findAll()).thenThrow(new RuntimeException("Internal server error"));
//
//        // Mock the headers with authorization information
//        Map<String, String> headers = new HashMap<>();
//        headers.put("authorization", "Bearer mockedJwtToken");
//
//        // Call the method under test
//        ResponseEntity response = userService.getAllUsers(headers);
//
//        // Assertions
//        Assertions.assertThat(response).isNotNull();
//        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//}