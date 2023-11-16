package com.project.system_integration.services;

import com.project.system_integration.entities.Country;
import com.project.system_integration.entities.Inflation;
import com.project.system_integration.entities.Unit;
import com.project.system_integration.exceptions.UnauthorizedException;
import com.project.system_integration.models.InflationDto;
import com.project.system_integration.models.UserDto;
import com.project.system_integration.repositories.CountryRepository;
import com.project.system_integration.repositories.InflationRepository;
import com.project.system_integration.repositories.UnitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InflationServiceTest {
    @Mock
    private InflationRepository inflationRepository;
    @Mock
    private CountryRepository countryRepository;
    @Mock
    private UnitRepository unitRepository;
    @Mock
    private AuthService authService;

    private UserDto userDto;
    private InflationDto inflationDto;
    private Country country;
    private Unit unit;
    private Inflation inflation;
    @InjectMocks
    private InflationService inflationService;

    @BeforeEach
    void setUp() {
        userDto = new UserDto("TestUser", "User");
        country = new Country(0, "Country", "CO");
        unit = new Unit(0, "Inflation", "Unit");
        inflation = new Inflation(2023, 2.5, country, unit);
        inflationDto = new InflationDto(inflation);
    }

    @Test
    void getAllInflations_Success() throws Exception {
        when(authService.authenticate(ArgumentMatchers.any())).thenReturn(userDto);
        when(inflationRepository.findAll()).thenReturn(Collections.singletonList(inflation));
        ResponseEntity response = inflationService.getAllInflations(new HashMap<>());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isInstanceOf(List.class);
    }

    @Test
    void getAllInflations_Unauthorized() throws Exception {
        when(authService.authenticate(ArgumentMatchers.any())).thenThrow(new UnauthorizedException("Unauthorized"));
        ResponseEntity response = inflationService.getAllInflations(new HashMap<>());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void getAllInflations_InternalServerError() throws Exception {
        when(authService.authenticate(ArgumentMatchers.any())).thenThrow(new RuntimeException("Internal server error"));
        ResponseEntity response = inflationService.getAllInflations(new HashMap<>());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void getOneByYear_Success() throws Exception {
        when(authService.authenticate(ArgumentMatchers.any())).thenReturn(new UserDto("TestUser", "USER"));
        when(inflationRepository.findByYear(2023)).thenReturn(Optional.of(new Inflation(2023, 2.5, new Country(), new Unit())));
        ResponseEntity response = inflationService.getOneByYear(new HashMap<>(), 2023);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isInstanceOf(InflationDto.class);
    }

    @Test
    void getOneByYear_NotFound() throws Exception {
        when(authService.authenticate(ArgumentMatchers.any())).thenReturn(new UserDto("TestUser", "USER"));
        when(inflationRepository.findByYear(2023)).thenReturn(Optional.empty());
        ResponseEntity response = inflationService.getOneByYear(new HashMap<>(), 2023);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void getOneByYear_Unauthorized() throws Exception {
        when(authService.authenticate(ArgumentMatchers.any())).thenThrow(new UnauthorizedException("Unauthorized"));
        ResponseEntity response = inflationService.getOneByYear(new HashMap<>(), 2023);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void getOneByYear_InternalServerError() throws Exception {
        when(authService.authenticate(ArgumentMatchers.any())).thenThrow(new RuntimeException("Internal server error"));
        ResponseEntity response = inflationService.getOneByYear(new HashMap<>(), 2023);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void addInflation_Success() throws Exception {
        when(authService.authenticateAdmin(ArgumentMatchers.any())).thenReturn(userDto);
        when(countryRepository.findByName(inflationDto.getCountry())).thenReturn(Optional.of(country));
        when(unitRepository.findByTitle(inflationDto.getTitle())).thenReturn(Optional.of(unit));
        when(inflationRepository.save(ArgumentMatchers.any())).thenReturn(inflation,unit);

        ResponseEntity response = inflationService.addInflation(new HashMap<>(), inflationDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void addInflation_NoCountryFound() throws Exception {
        when(authService.authenticateAdmin(ArgumentMatchers.any())).thenReturn(userDto);
        when(countryRepository.findByName(inflationDto.getCountry())).thenReturn(Optional.empty());



        ResponseEntity response = inflationService.addInflation(new HashMap<>(), inflationDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void addInflation_NoUnitFound() throws Exception {
        when(authService.authenticateAdmin(ArgumentMatchers.any())).thenReturn(userDto);
        when(countryRepository.findByName(inflationDto.getCountry())).thenReturn(Optional.of(country));
        when(unitRepository.findByTitle(inflationDto.getTitle())).thenReturn(Optional.empty());

        ResponseEntity response = inflationService.addInflation(new HashMap<>(), inflationDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void addInflation_Unauthorized() throws Exception {
        when(authService.authenticateAdmin(ArgumentMatchers.any())).thenThrow(new UnauthorizedException("Unauthorized"));
        ResponseEntity response = inflationService.addInflation(new HashMap<>(), inflationDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void addInflation_InternalServerError() throws Exception {
        when(authService.authenticateAdmin(ArgumentMatchers.any())).thenThrow(new RuntimeException("Internal server error"));
        ResponseEntity response = inflationService.addInflation(new HashMap<>(), inflationDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}