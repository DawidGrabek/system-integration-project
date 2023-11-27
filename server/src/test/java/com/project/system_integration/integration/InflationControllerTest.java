package com.project.system_integration.integration;//package com.project.system_integration.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.system_integration.SystemIntegrationApplication;
import com.project.system_integration.entities.Country;
import com.project.system_integration.entities.Unit;
import com.project.system_integration.exceptions.BadRequestException;
import com.project.system_integration.models.InflationDto;
import com.project.system_integration.repositories.InflationRepository;
import com.project.system_integration.repositories.UserRepository;
import com.project.system_integration.services.InflationService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:db;DB_CLOSE_DELAY=-1;NON_KEYWORDS=value,year",
        "spring.datasource.password=sa",
        "spring.datasource.username=sa",
        "spring.datasource.driver-class-name=org.h2.Driver"
})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InflationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private InflationRepository repository;

    private Authentication mockAuthentication;
    private Country mockCountry;
    private Unit mockUnit;

    @BeforeAll
    public void startupSetup() {
        this.mockCountry = new Country(0, "Poland", "PL");
        this.mockUnit =  new Unit(0, "Inflation", "Percent");
    }
    @BeforeEach
    public void setup() {
        mockAuthentication = new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Collections.singletonList(new SimpleGrantedAuthority("SCOPE_ADMIN"));

            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return "user";
            }
        };
    }
//    @AfterEach
//    public void tearDown() {
//        repository.deleteAll();
//    }
    @Test
    public void testAddInflationAsAdmin() throws Exception {
        // Constructing the request body
        InflationDto inflationDto = new InflationDto(2024, 30.0, "Inflation","Percent", "Poland");

        // Perform the POST request
        mockMvc.perform(post("/api/v1/inflation")
                        .principal(mockAuthentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inflationDto)))
                .andExpect(status().isOk());
        Optional expected = repository.findByYear(2024);
        assertFalse(expected.isEmpty());

    }


    @Test
    public void testAddInflationAsNonAdmin() throws Exception {
        // Constructing the request body
        InflationDto inflationDto = new InflationDto();
        mockAuthentication = new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Collections.singletonList(new SimpleGrantedAuthority("SCOPE_USER"));

            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return null;
            }
        };

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
        // Perform the GET request
        mockMvc.perform(get("/api/v1/inflation/2022")
                        .principal(mockAuthentication))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void testGetInflationByYearNotFound() throws Exception {
        // Perform the GET request
        mockMvc.perform(get("/api/v1/inflation/2026")
                        .principal(mockAuthentication))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetInflationByYearXml() throws Exception {
        // Perform the GET request

        mockMvc.perform(get("/api/v1/inflation/xml/2020")
                        .principal(mockAuthentication))
                .andExpect(status().isOk());

        // Additional assertions and verifications

    }

    @Test
    public void testGetInflationByYearXmlNotFound() throws Exception {
        // Perform the GET request
        mockMvc.perform(get("/api/v1/inflation/xml/2026")
                        .principal(mockAuthentication))
                .andExpect(status().isBadRequest());

        // Additional assertions and verifications

    }

    @Test
    public void testGetInflationSuccess() throws Exception {
        // Perform the GET request
        mockMvc.perform(get("/api/v1/inflation")
                        .principal(mockAuthentication))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Additional assertions and verifications

    }
}