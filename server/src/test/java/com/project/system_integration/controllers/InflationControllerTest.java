package com.project.system_integration.controllers;//package com.project.system_integration.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.system_integration.models.InflationDto;
import com.project.system_integration.services.InflationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import java.util.Collections;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@WebMvcTest(InflationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class InflationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InflationService service;

    @Test
    public void testAddInflationAsAdmin() throws Exception {
        // Mock Authentication and GrantedAuthority
        Authentication mockAuthentication = Mockito.mock(Authentication.class);
        GrantedAuthority mockGrantedAuthority = Mockito.mock(GrantedAuthority.class);

        // Stubbing the methods
//        when(mockAuthentication.getAuthorities()).thenReturn(Collections.singleton(mockGrantedAuthority));
        when(mockGrantedAuthority.getAuthority()).thenReturn("SCOPE_ADMIN");
        doReturn(Collections.singleton(mockGrantedAuthority)).when(mockAuthentication).getAuthorities();

        // Mocking the service call
        doReturn(true).when(service).addInflation(any(InflationDto.class));

        // Constructing the request body
        InflationDto inflationDto = new InflationDto(); // Populate it as necessary

        // Perform the POST request
        mockMvc.perform(post("/api/v1/inflation")
                        .principal(mockAuthentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inflationDto)))
                .andExpect(status().isOk());

        // Additional assertions and verifications
    }

    @Test
    public void testAddInflationAsNonAdmin() throws Exception {
        // Mock Authentication and GrantedAuthority
        Authentication mockAuthentication = Mockito.mock(Authentication.class);
        GrantedAuthority mockGrantedAuthority = Mockito.mock(GrantedAuthority.class);

        // Stubbing the methods
//        when(mockAuthentication.getAuthorities()).thenReturn(Collections.singleton(mockGrantedAuthority));
        when(mockGrantedAuthority.getAuthority()).thenReturn("SCOPE_USER");
        doReturn(Collections.singleton(mockGrantedAuthority)).when(mockAuthentication).getAuthorities();

        // Mocking the service call
        doReturn(true).when(service).addInflation(any(InflationDto.class));

        // Constructing the request body
        InflationDto inflationDto = new InflationDto(); // Populate it as necessary

        // Perform the POST request
        mockMvc.perform(post("/api/v1/inflation")
                        .principal(mockAuthentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inflationDto)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}