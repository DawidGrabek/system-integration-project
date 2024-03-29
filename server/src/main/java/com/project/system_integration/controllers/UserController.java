package com.project.system_integration.controllers;

import com.project.system_integration.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService service;

    @GetMapping("/all")
    public ResponseEntity<List<?>> getAllUsers(@RequestHeader Map<String, String> headers) {
        return new ResponseEntity<>(service.getAllUsers(headers), HttpStatus.OK);
    }
}
