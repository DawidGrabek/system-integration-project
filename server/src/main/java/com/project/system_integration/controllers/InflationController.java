package com.project.system_integration.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/inflation")
@RequiredArgsConstructor
public class InflationController {

    @GetMapping("")
    public int getInflation() {

        //TODO: finish
        return 0;
    }

    @PostMapping("/")
    public void addInflation() {
        //TODO: finish
    }

}
