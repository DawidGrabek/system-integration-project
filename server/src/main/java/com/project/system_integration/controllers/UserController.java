package com.project.system_integration.controllers;

import com.project.system_integration.config.LoginForm;
import com.project.system_integration.models.RegisterDto;
import com.project.system_integration.services.AuthService;
import com.project.system_integration.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService service;

    @GetMapping("/all")
    public ResponseEntity getAllUsers(@RequestHeader Map<String, String> headers) {
        return service.getAllUsers(headers);
    }



}
