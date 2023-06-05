package com.project.system_integration.controllers;

import com.project.system_integration.config.LoginForm;
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
public class UserController {

    private final UserService service;
    private final AuthService auth;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginForm body) {
        System.out.println(body.getLogin() + "   test   " + body.getPassword());
        return auth.loginUser(body.getLogin(), body.getPassword());
    }

    @GetMapping("/data")
    public ResponseEntity<String> showData(@RequestHeader Map<String, String> headers) {
        System.out.println("headers");
        System.out.println(headers);
       return service.testAuthentication("param1", "param2", headers);
    }

    @GetMapping("/all")
    public ResponseEntity getAllUsers(@RequestHeader Map<String, String> headers) {
        return service.getAllUsers(headers);
    }

}
