package com.project.system_integration.controllers;

import com.project.system_integration.models.LoginForm;
import com.project.system_integration.models.RegisterDto;
import com.project.system_integration.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class AuthController {

    private final AuthService auth;
    @ExceptionHandler
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException error) {
        return new ResponseEntity<>(error.getMessage(), error.getStatusCode());
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginForm body) throws Exception {
        String token = auth.loginUser(body.getLogin(), body.getPassword());
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterDto body){
//        return auth.registerUser(body);
        auth.registerUser(body);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
