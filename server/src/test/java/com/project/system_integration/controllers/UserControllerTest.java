//package com.project.system_integration.controllers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.project.system_integration.models.LoginForm;
//import com.project.system_integration.controllers.UserController;
//import com.project.system_integration.entities.User;
//import com.project.system_integration.models.RegisterDto;
//import com.project.system_integration.services.AuthService;
//import com.project.system_integration.services.UserService;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.ResultActions;
//
//import java.util.*;
//
//import static org.hamcrest.Matchers.containsString;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
////@SpringBootTest
////@AutoConfigureMockMvc
//@WebMvcTest(UserController.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//
//public class UserControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @MockBean
//    private UserService userService;
//    @MockBean
//    private AuthService authService;
//    private Map<String, String> headers;
//
////    @BeforeAll
////    private void init() throws Exception {
////        this.token = this.mockMvc.perform(post("/api/v1/users/login").contentType(MediaType.APPLICATION_JSON)
////                        .content(asJsonString(new LoginForm("admin", "1234"))))
////                .andDo(print())
////                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
////    }
//    @BeforeEach
//    private void init() throws Exception {
//        headers = new HashMap();
//    }
//    @Test
//    public void getAllUsers_403() throws Exception {
//        when(userService.getAllUsers(headers)).thenReturn(new ResponseEntity<>("error message", HttpStatus.FORBIDDEN));
//        this.mockMvc.perform(get("/api/v1/users/all")).andDo(print())
//                .andExpect(status().isForbidden());
//    }
//    @Test
//    public void getAllUsers_200() throws Exception {
//        headers.put("authorization", "Bearer 1234");
//        when(userService.getAllUsers(headers)).thenReturn(new ResponseEntity<>(new ArrayList< User >(), HttpStatus.OK));
//        this.mockMvc.perform(get("/api/v1/users/all").header("authorization", "Bearer 1234")).andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void loginUserNoUser() throws Exception {
//        when(authService.loginUser("login", "password")).thenReturn(new ResponseEntity<>("no user found", HttpStatus.FORBIDDEN));
//        this.mockMvc.perform(post("/api/v1/users/login").contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(new LoginForm("login", "password"))))
//                .andDo(print())
//                .andExpect(status().isForbidden())
//                .andExpect(content().string(containsString("no user found")));
//    }
//
//    @Test
//    public void loginUserSuccess() throws Exception {
//        when(authService.loginUser("login", "password")).thenReturn(new ResponseEntity<>("token ", HttpStatus.OK));
//
//        this.mockMvc.perform(post("/api/v1/users/login").contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(new LoginForm("login", "password"))))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void registerUserSuccess() throws Exception {
//        RegisterDto registerDto = new RegisterDto("username", "password", "USER");
//
//        when(authService.registerUser(registerDto)).thenReturn(new ResponseEntity<>("ok", HttpStatus.OK));
//        this.mockMvc.perform(post("/api/v1/users/register").contentType(MediaType.APPLICATION_JSON)
//                    .content(asJsonString(new RegisterDto("username", "password", "USER"))))
//                .andDo(print());
//    }
//
//    public static String asJsonString(final Object obj) {
//        try {
//            return new ObjectMapper().writeValueAsString(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//}




//package com.project.system_integration.controllers;
//
//import com.project.system_integration.controllers.UserController;
//import com.project.system_integration.entities.User;
//import com.project.system_integration.services.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.http.MediaType;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.Collections;
//
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class UserControllerTest {
//
//    @InjectMocks
//    private UserController userController;
//
//    @Mock
//    private UserService userService;
//
//    private Authentication mockAuthentication;
//    private GrantedAuthority mockGrantedAuthority;
//
//    @BeforeEach
//    public void setup() {
//        // Mock Authentication and GrantedAuthority
//        mockAuthentication = Mockito.mock(Authentication.class);
//        mockGrantedAuthority = Mockito.mock(GrantedAuthority.class);
//
//        // Stubbing the methods
//        when(mockGrantedAuthority.getAuthority()).thenReturn("SCOPE_ADMIN");
//        doReturn(Collections.singleton(mockGrantedAuthority)).when(mockAuthentication).getAuthorities();
//    }
//
//
//    @Test
//    void testGetAllUsers() throws Exception {
//        // Mocking the service call
//        when(userService.getAllUsers(anyMap())).thenReturn(Collections.singletonList(new User()));
//
//        // Setting up MockMvc
//        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
//
//        // Performing the GET request
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/all")
//                        .principal(mockAuthentication)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//        // Verifying that the service method is called
//        verify(userService, times(1)).getAllUsers(anyMap());
//    }
//}
