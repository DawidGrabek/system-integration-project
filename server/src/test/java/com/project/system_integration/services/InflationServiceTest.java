package com.project.system_integration.services;

import com.project.system_integration.entities.Country;
import com.project.system_integration.entities.Inflation;
import com.project.system_integration.entities.Unit;
import com.project.system_integration.exceptions.BadRequestException;
import com.project.system_integration.models.InflationDto;
import com.project.system_integration.repositories.CountryRepository;
import com.project.system_integration.repositories.InflationRepository;
import com.project.system_integration.repositories.UnitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    @InjectMocks
    private InflationService inflationService;

    @Test
    void getAllInflations_Success() {
        int year = 2021;
        double value = 1.5;
        String title = "title";
        Unit unit = new Unit(0, "unitTitle", "unit");
        Country country = new Country(0, "Country", "CountryCode");
        Inflation inflation = new Inflation();
        inflation.setYear(year);
        inflation.setUnit(unit);
        inflation.setValue(value);
        inflation.setCountry(country);
        when(inflationRepository.findAll()).thenReturn(Collections.singletonList(inflation));

        List<InflationDto> result = inflationService.getAllInflations();

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void getOneByYear_Success() throws BadRequestException {
        int year = 2021;
        double value = 1.5;
        String title = "title";
        Unit unit = new Unit(0, "unitTitle", "unit");
        Country country = new Country(0, "Country", "CountryCode");
        Inflation inflation = new Inflation();
        inflation.setYear(year);
        inflation.setUnit(unit);
        inflation.setValue(value);
        inflation.setCountry(country);

        when(inflationRepository.findByYear(year)).thenReturn(Optional.of(inflation));

        InflationDto result = inflationService.getOneByYear(year);

        assertNotNull(result);
        assertEquals(year, result.getYear());
    }

    @Test
    void getOneByYear_NotFound() {
        int year = 2021;
        when(inflationRepository.findByYear(year)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> inflationService.getOneByYear(year));
    }

    @Test
    void addInflation_Success() throws BadRequestException {
        InflationDto inflationDto = new InflationDto();
        inflationDto.setYear(2021);
        inflationDto.setCountry("Country");
        inflationDto.setTitle("Unit");

        when(countryRepository.findByName("Country")).thenReturn(Optional.of(new Country()));
        when(unitRepository.findByTitle("Unit")).thenReturn(Optional.of(new Unit()));

        boolean result = inflationService.addInflation(inflationDto);

        assertTrue(result);
        verify(inflationRepository).save(any(Inflation.class));
    }

    @Test
    void addInflation_CountryNotFound() {
        InflationDto inflationDto = new InflationDto();
        inflationDto.setCountry("UnknownCountry");

        when(countryRepository.findByName("UnknownCountry")).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> inflationService.addInflation(inflationDto));
    }

    @Test
    void addInflation_UnitNotFound() {
        InflationDto inflationDto = new InflationDto();
        inflationDto.setCountry("Country");
        inflationDto.setTitle("UnknownUnit");

        when(countryRepository.findByName("Country")).thenReturn(Optional.of(new Country()));
        when(unitRepository.findByTitle("UnknownUnit")).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> inflationService.addInflation(inflationDto));
    }

    @Test
    void getOneByYearXml_Success() throws BadRequestException {
        int year = 2021;
        Inflation inflation = new Inflation();
        inflation.setYear(year);
        inflation.setValue(1.5);
        Unit unit = new Unit(1, "Unit", "unit");
        Country country = new Country(1, "Country", "CountryCode");
        inflation.setCountry(country);
        inflation.setUnit(unit);

        when(inflationRepository.findByYear(year)).thenReturn(Optional.of(inflation));

        String result = inflationService.getOneByYearXml(year);

        assertNotNull(result);
        assertTrue(result.contains("<year>2021</year>"));
    }

    @Test
    void getOneByYearXml_NotFound() {
        int year = 2021;
        when(inflationRepository.findByYear(year)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> inflationService.getOneByYearXml(year));
    }
}