package com.project.system_integration.services;

import com.project.system_integration.entities.Role;
import com.project.system_integration.entities.User;
import com.project.system_integration.models.RegisterDto;
import com.project.system_integration.repositories.RoleRepository;
import com.project.system_integration.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @Test
    void loginUser_Successful() throws Exception {
        // Arrange
        String login = "testUser";
        String password = "testPassword";
        String expectedToken = "mockedJwtToken";
        User mockUser = new User(login, password, new Role(0, "USER"));

        Authentication auth = mock(Authentication.class);
        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password)))
                .thenReturn(auth);
        when(userRepository.findByLogin(login)).thenReturn(Optional.of(mockUser));
        when(jwtService.generateToken(auth)).thenReturn(expectedToken);

        // Act
        String actualToken = authService.loginUser(login, password);

        // Assert
        assertEquals(expectedToken, actualToken);
    }

    @Test
    void loginUser_Failed_UserNotFound() {
        // Arrange
        String login = "nonExistingUser";
        String password = "testPassword";

        when(userRepository.findByLogin(login)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(Exception.class, () -> authService.loginUser(login, password));
    }

    @Test
    void registerUser_Successful() throws Exception {
        // Arrange
        RegisterDto registerDto = new RegisterDto("newUser", "password", "USER_ROLE");
        Role role = new Role(0, "USER");

        when(roleRepository.findByRoleName(registerDto.getRole())).thenReturn(Optional.of(role));
        when(userRepository.existsByLogin(registerDto.getLogin())).thenReturn(false);

        // Act
        boolean result = authService.registerUser(registerDto);

        // Assert
        assertTrue(result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void registerUser_Failed_UserAlreadyExists() {
        // Arrange
        Role role = new Role(0, "USER");
        RegisterDto registerDto = new RegisterDto("existingUser", "password", "USER");
        when(roleRepository.findByRoleName(registerDto.getRole())).thenReturn(Optional.of(role));
        when(userRepository.existsByLogin(registerDto.getLogin())).thenReturn(true);

        // Act & Assert
        assertThrows(Exception.class, () -> authService.registerUser(registerDto));
    }

    @Test
    void registerUser_Failed_RoleDoesNotExist() {
        // Arrange
        RegisterDto registerDto = new RegisterDto("newUser", "password", "NON_EXISTENT_ROLE");

        when(roleRepository.findByRoleName(registerDto.getRole())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(Exception.class, () -> authService.registerUser(registerDto));
    }
}