package com.project.system_integration.services;

import com.project.system_integration.entities.Inflation;
import com.project.system_integration.exceptions.UnauthorizedException;
import com.project.system_integration.models.InflationDto;
import com.project.system_integration.models.UserDto;
import com.project.system_integration.repositories.InflationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InflationService {
    private final InflationRepository repository;
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
}
