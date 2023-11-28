package com.project.system_integration.services;

import com.project.system_integration.entities.Country;
import com.project.system_integration.entities.Inflation;
import com.project.system_integration.entities.Unit;
import com.project.system_integration.exceptions.BadRequestException;
import com.project.system_integration.exceptions.UnauthorizedException;
import com.project.system_integration.models.InflationDto;
import com.project.system_integration.models.UserDto;
import com.project.system_integration.repositories.CountryRepository;
import com.project.system_integration.repositories.InflationRepository;
import com.project.system_integration.repositories.UnitRepository;
import jakarta.xml.bind.JAXB;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InflationService {
    private final InflationRepository repository;
    private final CountryRepository countryRepository;
    private final UnitRepository unitRepository;
    private final AuthService auth;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<InflationDto> getAllInflations() {

        List<Inflation> inflations = repository.findAll();
        List<InflationDto> inflationsDto = new ArrayList<>();

        for (Inflation i : inflations) {
            inflationsDto.add(new InflationDto(i));
        }

        return inflationsDto;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public InflationDto getOneByYear(Integer year) throws BadRequestException {
        Optional<Inflation> inflation = repository.findByYear(year);
        if(inflation.isEmpty()) {
            throw new BadRequestException("no data with this year");
        }
        return new InflationDto(inflation.get());
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public boolean addInflation(InflationDto inflationDto) throws BadRequestException {
        Inflation inflation = new Inflation();
        Optional<Country> countryOptional = countryRepository.findByName(inflationDto.getCountry());
        if(countryOptional.isEmpty()) {
            System.out.println("no country");
            throw new BadRequestException("no country with this name");
        }
        Country country = countryOptional.get();
        Optional<Unit> unitOptional = unitRepository.findByTitle(inflationDto.getTitle());
        if(unitOptional.isEmpty()) {
            throw new BadRequestException("no unit with this name");
        }
        Unit unit = unitOptional.get();

        inflation.setYear(inflationDto.getYear());
        inflation.setValue(inflationDto.getValue());
        inflation.setCountry(country);
        inflation.setUnit(unit);
        repository.save(inflation);
        return true;
    }

    public String getOneByYearXml(Integer year) throws BadRequestException {
        Optional<Inflation> inflation = repository.findByYear(year);
        if(inflation.isEmpty()) {
            throw new BadRequestException("no data with this year");
//                return new ResponseEntity("no data with this year", HttpStatus.BAD_REQUEST);
        }
        InflationDto dto = new InflationDto(inflation.get());
        StringWriter sw = new StringWriter();
        JAXB.marshal(dto, sw);
        String inflationXml = sw.toString();
        return inflationXml;
    }
}
