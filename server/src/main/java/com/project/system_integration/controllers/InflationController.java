package com.project.system_integration.controllers;

import com.project.system_integration.exceptions.BadRequestException;
import com.project.system_integration.models.InflationDto;
import com.project.system_integration.services.InflationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/inflation")
@RequiredArgsConstructor
@CrossOrigin
public class InflationController {
    private final InflationService service;

    @GetMapping("")
    public ResponseEntity<List<InflationDto>> getInflation(Authentication authentication) {
        List<InflationDto> inflation = service.getAllInflations();
        return new ResponseEntity<>(inflation, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<String> addInflation(Authentication authentication, @RequestBody InflationDto inflationDto)  {

        if(authentication.getAuthorities().iterator().next().getAuthority().equals("SCOPE_ADMIN")) {

            try {
                service.addInflation(inflationDto);
            } catch (BadRequestException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/{year}")
    public ResponseEntity<?> getInflationByYear( @PathVariable Integer year) {
        InflationDto inflation = null;
        try {
            inflation = service.getOneByYear(year);
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return new ResponseEntity<>(inflation, HttpStatus.OK);

    }

    @GetMapping("/xml/{year}")
    public ResponseEntity<String> getInflationByYearXml( @PathVariable Integer year) {
//        return service.getOneByYearXml(year);
        String inflation = null;
        try {
            inflation = service.getOneByYearXml(year);
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return new ResponseEntity<>(inflation, HttpStatus.OK);
    }

    @GetMapping("/before/{year}")
    public ResponseEntity<List<InflationDto>> getInflationFilteredYearBefore(@PathVariable Integer year) {
        return new ResponseEntity<>(service.getBefore(year), HttpStatus.OK);
    }

    @GetMapping("/after/{year}")
    public ResponseEntity<List<InflationDto>> getInflationFilteredYearAfter(@PathVariable Integer year) {
        return new ResponseEntity<>(service.getAfter(year), HttpStatus.OK);
    }

    @GetMapping("/between/{before}/{after}")
    public ResponseEntity<List<InflationDto>> getInflationFiltered(@PathVariable Integer before, @PathVariable Integer after) {
        return new ResponseEntity<>(service.getBetween(before, after), HttpStatus.OK);
    }

    @GetMapping("/value/below/{value}")
    public ResponseEntity<List<InflationDto>> getInflationFilteredValueBelow(@PathVariable Integer value) {
        return new ResponseEntity<>(service.getValueBelow(value), HttpStatus.OK);
    }

    @GetMapping("/value/above/{value}")
    public ResponseEntity<List<InflationDto>> getInflationFilteredValueAbove(@PathVariable Integer value) {
        return new ResponseEntity<>(service.getValueAbove(value), HttpStatus.OK);
    }
}
