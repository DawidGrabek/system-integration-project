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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

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

    public ResponseEntity getAllInflations(@RequestHeader Map<String, String> headers) {
        try {
            UserDto credentials = auth.authenticateAdmin(headers);
            List<Inflation> inflations = repository.findAll();
            List<InflationDto> inflationsDto = new ArrayList<>();

            for (Inflation i : inflations) {
                inflationsDto.add(new InflationDto(i));
            }

            return new ResponseEntity(inflationsDto, HttpStatus.OK);
        } catch (UnauthorizedException e) {
              return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity getOneByYear(@RequestHeader Map<String, String> headers, Integer year) {
        try{
            UserDto credentials = auth.authenticate(headers);
            Optional<Inflation> inflation = repository.findByYear(year);
            if(inflation.isEmpty()) {
                return new ResponseEntity("no data with this year", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity(new InflationDto(inflation.get()), HttpStatus.OK);

        }catch (UnauthorizedException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity addInflation(Map<String, String> headers, InflationDto inflationDto) {

        try{
            UserDto credentials = auth.authenticate(headers);
            Inflation inflation = new Inflation();
            Optional<Country> countryOptional = countryRepository.findByName(inflationDto.getCountry());
            if(countryOptional.isEmpty()) {
                return new ResponseEntity("no country with this name", HttpStatus.BAD_REQUEST);
            }
            Country country = countryOptional.get();

            Optional<Unit> unitOptional = unitRepository.findByTitle(inflationDto.getTitle());
            if(unitOptional.isEmpty()) {
                return new ResponseEntity("no unit with this name", HttpStatus.BAD_REQUEST);
            }
            Unit unit = unitOptional.get();

            inflation.setYear(inflationDto.getYear());
            inflation.setValue(inflationDto.getValue());
            inflation.setCountry(country);
            inflation.setUnit(unit);
            repository.save(inflation);
            return new ResponseEntity("saved corectly", HttpStatus.OK);

        }catch (UnauthorizedException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
}
