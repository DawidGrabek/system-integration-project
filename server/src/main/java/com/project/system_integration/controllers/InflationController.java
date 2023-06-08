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
public class InflationController {

    private final InflationService service;
    @GetMapping("")
    public ResponseEntity<InflationDto> getInflation(@RequestHeader Map<String, String> headers) {
        return service.getAllInflations(headers);
    }

    @PostMapping("/")
    public ResponseEntity<String> addInflation() {
        //TODO: finish
        return new ResponseEntity<>("TODO", HttpStatus.BAD_GATEWAY);
    }

}
