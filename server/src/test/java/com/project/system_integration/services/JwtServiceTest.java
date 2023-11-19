package com.project.system_integration.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @Mock
    private JwtEncoder jwtEncoder;

    @InjectMocks
    private JwtService jwtService;


    @Test
    void generateToken_Success() {
        // Arrange
        String username = "testUser";
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(username);
//        when(authentication.getAuthorities()).thenReturn(List.of(new SimpleGrantedAuthority("ROLE_USER")));

        Jwt jwt = mock(Jwt.class);
        when(jwt.getTokenValue()).thenReturn("mockedJwtToken");

        when(jwtEncoder.encode(any(JwtEncoderParameters.class))).thenReturn(jwt);

        // Act
        String token = jwtService.generateToken(authentication);

        // Assert
        assertNotNull(token);
        assertEquals("mockedJwtToken", token);
        verify(jwtEncoder).encode(any(JwtEncoderParameters.class));
    }

    // Additional tests can be written to cover more scenarios
}