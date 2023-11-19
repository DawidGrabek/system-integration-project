package com.project.system_integration.controllers;

import com.project.system_integration.models.InflationDto;
import com.project.system_integration.services.InflationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/inflation")
@RequiredArgsConstructor
@CrossOrigin
public class InflationController {

    private final InflationService service;
    @GetMapping("")
    public ResponseEntity<InflationDto> getInflation(Authentication authentication) {
        return service.getAllInflations();
    }

    @PostMapping("")
    public ResponseEntity<String> addInflation(Authentication authentication, @RequestBody InflationDto inflationDto) {

        if(authentication.getAuthorities().iterator().next().getAuthority().equals("SCOPE_ADMIN")) {

            return service.addInflation(inflationDto);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/{year}")
    public ResponseEntity getInflationByYear( @PathVariable Integer year) {
        return service.getOneByYear(year);
    }

    @GetMapping("/xml/{year}")
    public ResponseEntity getInflationByYearXml( @PathVariable Integer year) {
        return service.getOneByYearXml(year);
    }
}
