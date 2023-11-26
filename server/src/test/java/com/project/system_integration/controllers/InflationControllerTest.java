package com.project.system_integration.controllers;//package com.project.system_integration.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.system_integration.exceptions.BadRequestException;
import com.project.system_integration.models.InflationDto;
import com.project.system_integration.services.InflationService;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(InflationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class InflationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InflationService service;

    private Authentication mockAuthentication;
    private GrantedAuthority mockGrantedAuthority;

    @BeforeEach
    public void setup() {
        // Mock Authentication and GrantedAuthority
        mockAuthentication = Mockito.mock(Authentication.class);
        mockGrantedAuthority = Mockito.mock(GrantedAuthority.class);

        // Stubbing the methods
        when(mockGrantedAuthority.getAuthority()).thenReturn("SCOPE_ADMIN");
        doReturn(Collections.singleton(mockGrantedAuthority)).when(mockAuthentication).getAuthorities();
    }

    @Test
    public void testAddInflationAsAdmin() throws Exception {
        // Mocking the service call
        doReturn(true).when(service).addInflation(any(InflationDto.class));

        // Constructing the request body
        InflationDto inflationDto = new InflationDto();

        // Perform the POST request
        mockMvc.perform(post("/api/v1/inflation")
                        .principal(mockAuthentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inflationDto)))
                .andExpect(status().isOk());


        // Additional assertions and verifications
        verify(service, times(1)).addInflation(any(InflationDto.class));
    }

    @Test
    public void testAddInflationAsNonAdmin() throws Exception {
        // override beforeEach
        when(mockGrantedAuthority.getAuthority()).thenReturn("SCOPE_USER");

        // Mocking the service call
        doReturn(true).when(service).addInflation(any(InflationDto.class));

        // Constructing the request body
        InflationDto inflationDto = new InflationDto();

        // Perform the POST request
        mockMvc.perform(post("/api/v1/inflation")
                        .principal(mockAuthentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inflationDto)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetInflationByYear() throws Exception {
        // Mocking the service call
        doReturn(new InflationDto()).when(service).getOneByYear(anyInt());

        // Perform the GET request
        mockMvc.perform(get("/api/v1/inflation/2022")
                        .principal(mockAuthentication))
                .andExpect(status().isOk());

        // Additional assertions and verifications
        verify(service, times(1)).getOneByYear(eq(2022));
    }

    @Test
    public void testGetInflationByYearNotFound() throws Exception {
        // Mocking the service call to throw BadRequestException
        doThrow(new BadRequestException("Bad Request")).when(service).getOneByYear(anyInt());

        // Perform the GET request
        mockMvc.perform(get("/api/v1/inflation/2022")
                        .principal(mockAuthentication))
                .andExpect(status().isBadRequest());

        // Additional assertions and verifications
        verify(service, times(1)).getOneByYear(eq(2022));
    }

    @Test
    public void testGetInflationByYearXml() throws Exception {
        // Mocking the service call
        doReturn("<xml>data</xml>").when(service).getOneByYearXml(anyInt());

        // Perform the GET request
        mockMvc.perform(get("/api/v1/inflation/xml/2022")
                        .principal(mockAuthentication))
                .andExpect(status().isOk());

        // Additional assertions and verifications
        verify(service, times(1)).getOneByYearXml(eq(2022));
    }

    @Test
    public void testGetInflationByYearXmlNotFound() throws Exception {
        // Mocking the service call to throw BadRequestException
        doThrow(new BadRequestException("Bad Request")).when(service).getOneByYearXml(anyInt());

        // Perform the GET request
        mockMvc.perform(get("/api/v1/inflation/xml/2022")
                        .principal(mockAuthentication))
                .andExpect(status().isBadRequest());

        // Additional assertions and verifications
        verify(service, times(1)).getOneByYearXml(eq(2022));
    }

    @Test
    public void testGetInflationSuccess() throws Exception {
        // Mocking the service call
        List<InflationDto> mockInflations = Arrays.asList(new InflationDto(), new InflationDto());
        doReturn(mockInflations).when(service).getAllInflations();

        // Perform the GET request
        mockMvc.perform(get("/api/v1/inflation")
                        .principal(mockAuthentication))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Additional assertions and verifications
        verify(service, times(1)).getAllInflations();
    }
}