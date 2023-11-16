package com.project.system_integration.services;

import com.project.system_integration.entities.Role;
import com.project.system_integration.entities.User;
import com.project.system_integration.exceptions.UnauthorizedException;
import com.project.system_integration.models.RegisterDto;
import com.project.system_integration.models.UserDto;
import com.project.system_integration.repositories.RoleRepository;
import com.project.system_integration.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;
    private User user;
    private Role role;

    @BeforeEach
    void setUp() {
        Role user_r = new Role(0, "USER");
        Role admin_r = new Role(1, "ADMIN");
        role = user_r;
        user = new User("TestUser", "TestPassword", user_r);
    }

    @Test
    void getUserByLogin_UserExists() throws Exception {
        // Mock the behavior of the userRepository to return a user
        when(userRepository.findByLogin("TestUser")).thenReturn(Optional.of(user));

        // Call the method under test
        User result = authService.getUserByLogin("TestUser");

        // Assertions
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(user);
    }

    @Test
    void getUserByLogin_UserNotFound() {
        String login = "NonExistentUser";
        // Mock the behavior of the userRepository to return an empty Optional
        when(userRepository.findByLogin(login)).thenReturn(Optional.empty());

        // Call the method under test and expect an exception
        Assertions.assertThatExceptionOfType(Exception.class)
                .isThrownBy(() -> authService.getUserByLogin(login))
                .withMessage("no user found");
    }

    @Test
    void loginUser_Success() throws Exception {
        // Mock the behavior of getUserByLogin to return a user
//        when(authService.getUserByLogin("TestUser")).thenReturn(user);
        when(userRepository.findByLogin("TestUser")).thenReturn(Optional.of(user));
        when(jwtService.generateToken("TestUser", "USER")).thenReturn("mockedJwtToken");

        // Call the method under test
        ResponseEntity<String> response = authService.loginUser("TestUser", "TestPassword");

        // Assertions
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo("mockedJwtToken");
    }

    @Test
    void loginUser_WrongPassword() throws Exception {
        // Mock the behavior of getUserByLogin to return a user
//        when(authService.getUserByLogin("TestUser")).thenReturn(user);
        when(userRepository.findByLogin("TestUser")).thenReturn(Optional.of(user));
        ResponseEntity<String> response = authService.loginUser("TestUser", "WrongPassword");
        // Call the method under test with a wrong password and expect an exception
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        Assertions.assertThat(response.getBody()).isEqualTo("wrong password");
    }

    @Test
    void loginUser_UserNotFound() {
        // Mock the behavior of getUserByLogin to return an empty Optional
        String login = "NonExistentUser";
        when(userRepository.findByLogin(login)).thenReturn(Optional.empty());
        ResponseEntity<String> response = authService.loginUser(login, "TestPassword");
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        Assertions.assertThat(response.getBody()).isEqualTo("no user found");
    }

    @Test
    void authenticate_Success() throws Exception {
        // Mock the JWT extraction and validation
        when(jwtService.extractLogin("mockedJwtToken")).thenReturn("TestUser");
        when(jwtService.extractRole("mockedJwtToken")).thenReturn("USER");
        when(jwtService.isTokenValid("mockedJwtToken", "TestUser")).thenReturn(true);
//        when(authService.getUserByLogin("TestUser")).thenReturn(user);
        when(userRepository.findByLogin("TestUser")).thenReturn(Optional.of(user));
        // Call the method under test
        UserDto userDto = authService.authenticate(Map.of("authorization", "Bearer mockedJwtToken"));

        // Assertions
        Assertions.assertThat(userDto).isNotNull();
        Assertions.assertThat(userDto.getLogin()).isEqualTo("TestUser");
        Assertions.assertThat(userDto.getRole()).isEqualTo("USER");
    }

    @Test
    void authenticate_NoTokenInHeaders() {
        // Call the method under test without the "authorization" header
        Assertions.assertThatExceptionOfType(UnauthorizedException.class)
                .isThrownBy(() -> authService.authenticate(Map.of()))
                .withMessage("no token in headers");
    }

    @Test
    void authenticate_InvalidToken() {
        // Mock the JWT extraction and validation to return false
        when(jwtService.extractLogin("mockedJwtToken")).thenReturn("TestUser");
        when(jwtService.extractRole("mockedJwtToken")).thenReturn("USER");

        // Call the method under test and expect an exception
        Assertions.assertThatExceptionOfType(Exception.class)
                .isThrownBy(() -> authService.authenticate(Map.of("authorization", "Bearer mockedJwtToken")))
                .withMessage("no user found");
    }

    @Test
    void authenticate_UserNotFound() {
        // Mock the JWT extraction
        when(jwtService.extractLogin("mockedJwtToken")).thenReturn("NonExistentUser");

        // Call the method under test and expect an exception
        Assertions.assertThatExceptionOfType(Exception.class)
                .isThrownBy(() -> authService.authenticate(Map.of("authorization", "Bearer mockedJwtToken")))
                .withMessage("no user found");
    }

    @Test
    void authenticateAdmin_Success() throws Exception {
        // Mock the JWT extraction and validation
        when(jwtService.extractLogin("mockedJwtToken")).thenReturn("TestUser");
        when(jwtService.extractRole("mockedJwtToken")).thenReturn("ADMIN");
        when(jwtService.isTokenValid("mockedJwtToken", "TestUser")).thenReturn(true);
//        when(authService.getUserByLogin("TestUser")).thenReturn(user);
        when(userRepository.findByLogin("TestUser")).thenReturn(Optional.of(user));

        // Call the method under test
        UserDto userDto = authService.authenticateAdmin(Map.of("authorization", "Bearer mockedJwtToken"));

        // Assertions
        Assertions.assertThat(userDto).isNotNull();
        Assertions.assertThat(userDto.getLogin()).isEqualTo("TestUser");
        Assertions.assertThat(userDto.getRole()).isEqualTo("ADMIN");
    }

    @Test
    void authenticateAdmin_UserIsNotAdmin() throws Exception {
        // Mock the JWT extraction and validation to return "USER" role
        when(jwtService.extractLogin("mockedJwtToken")).thenReturn("TestUser");
        when(jwtService.extractRole("mockedJwtToken")).thenReturn("USER");
        when(jwtService.isTokenValid("mockedJwtToken", "TestUser")).thenReturn(true);
//        when(authService.getUserByLogin("TestUser")).thenReturn(user);

        when(userRepository.findByLogin("TestUser")).thenReturn(Optional.of(user));
        // Call the method under test and expect an exception
        Assertions.assertThatExceptionOfType(UnauthorizedException.class)
                .isThrownBy(() -> authService.authenticateAdmin(Map.of("authorization", "Bearer mockedJwtToken")))
                .withMessage("No role required");
    }

    @Test
    void registerUser_Success() {
        RegisterDto registerDto = new RegisterDto("NewUser", "NewPassword", "USER");

        // Mock the behavior of repositories
        when(roleRepository.findByRoleName("USER")).thenReturn(Optional.of(role));
        when(userRepository.existsByLogin("NewUser")).thenReturn(false);

        // Call the method under test
        ResponseEntity<String> response = authService.registerUser(registerDto);

        // Assertions
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void registerUser_UserAlreadyExists() {
        RegisterDto registerDto = new RegisterDto("ExistingUser", "NewPassword", "USER");

        // Mock the behavior of repositories to return true
        when(roleRepository.findByRoleName("USER")).thenReturn(Optional.of(role));
        when(userRepository.existsByLogin("ExistingUser")).thenReturn(true);

        // Call the method under test and expect an exception
        ResponseEntity response = authService.registerUser(new RegisterDto("ExistingUser", "password", "USER"));
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        Assertions.assertThat(response.getBody()).isEqualTo("user already exist");
    }
    @Test
    void registerUser_RoleDoesNotExist() {
        RegisterDto registerDto = new RegisterDto("NewUser", "NewPassword", "NonExistentRole");

        // Mock the behavior of repositories to return false (role doesn't exist)
        when(roleRepository.findByRoleName("NonExistentRole")).thenReturn(Optional.empty());

        // Call the method under test and expect an exception
       ResponseEntity response = authService.registerUser(new RegisterDto("login", "password", "NonExistentRole"));
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(response.getBody()).isEqualTo("role doesnt exist");
    }
}