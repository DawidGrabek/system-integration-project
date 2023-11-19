package com.project.system_integration.controllers;

import com.project.system_integration.config.LoginForm;
import com.project.system_integration.models.RegisterDto;
import com.project.system_integration.services.AuthService;
import com.project.system_integration.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.GrantedAuthority;
import java.time.Instant;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final AuthService auth;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginForm body) {
        System.out.println(body.getLogin() + "   test   " + body.getPassword());
        return auth.loginUser(body.getLogin(), body.getPassword());
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody RegisterDto body) {
        return auth.registerUser(body);
    }
}
