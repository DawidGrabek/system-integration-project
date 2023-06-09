package com.project.system_integration.controllers;

import com.project.system_integration.models.InflationDto;
import com.project.system_integration.services.InflationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/inflation")
@RequiredArgsConstructor
@CrossOrigin
public class InflationController {

    private final InflationService service;
    @GetMapping("")
    public ResponseEntity<InflationDto> getInflation(@RequestHeader Map<String, String> headers) {
        return service.getAllInflations(headers);
    }

    @PostMapping("")
    public ResponseEntity<String> addInflation(@RequestHeader Map<String, String> headers, @RequestBody InflationDto inflationDto) {
        //TODO: finish
        return service.addInflation(headers , inflationDto);
    }

    @GetMapping("/{year}")
    public ResponseEntity getInflationByYear(@RequestHeader Map<String, String> headers, @PathVariable Integer year) {
        return service.getOneByYear(headers, year);
    }

}
