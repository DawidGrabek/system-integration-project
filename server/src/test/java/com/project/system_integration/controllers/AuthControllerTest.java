package com.project.system_integration.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.system_integration.config.LoginForm;
import com.project.system_integration.models.RegisterDto;
import com.project.system_integration.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.naming.AuthenticationException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    void login_Success() throws Exception {
        LoginForm loginForm = new LoginForm("user", "1234");
        String mockToken = "mockedJwtToken";
        when(authService.loginUser(loginForm.getLogin(), loginForm.getPassword())).thenReturn(mockToken);

        this.mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(loginForm)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void registerUser_Success() throws Exception {
        RegisterDto registerDto = new RegisterDto("user", "password", "ROLE_USER");
        when(authService.registerUser(registerDto)).thenReturn(true);

        this.mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(registerDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void login_Failure() throws Exception {
        LoginForm loginForm = new LoginForm("user", "password");
        when(authService.loginUser(loginForm.getLogin(), loginForm.getPassword()))
                .thenThrow(new AuthenticationException());

        this.mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(loginForm)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void registerUser_Failure() throws Exception {
        RegisterDto registerDto = new RegisterDto("user", "password", "ROLE_USER");
        when(authService.registerUser(registerDto)).thenThrow(new Exception("Registration failed"));

        this.mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(registerDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}