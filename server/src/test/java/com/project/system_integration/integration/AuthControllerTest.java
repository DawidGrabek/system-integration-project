package com.project.system_integration.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.system_integration.config.LoginForm;
import com.project.system_integration.models.RegisterDto;
import com.project.system_integration.repositories.UserRepository;
import com.project.system_integration.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.naming.AuthenticationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc()
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:db;DB_CLOSE_DELAY=-1;NON_KEYWORDS=value,year",
        "spring.datasource.password=sa",
        "spring.datasource.username=sa",
        "spring.datasource.driver-class-name=org.h2.Driver"
})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthControllerTest {
    @Autowired
    private UserRepository repository;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @Test
    void login_Success() throws Exception {
        LoginForm loginForm = new LoginForm("user", "1234");

        this.mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(loginForm)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void registerUser_Failure() throws Exception {
        RegisterDto registerDto = new RegisterDto("user", "password", "wrongRole");

        this.mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(registerDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerUser_Success() throws Exception {
        String login = "userTest";
        RegisterDto registerDto = new RegisterDto(login, "password", "USER");

        this.mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(registerDto)))
                .andDo(print())
                .andExpect(status().isOk());
        Optional expected = repository.findByLogin(login);
        assertFalse(expected.isEmpty());
    }

    @Test
    void login_Failure() throws Exception {
        LoginForm loginForm = new LoginForm("user", "password");
        this.mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(loginForm)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
