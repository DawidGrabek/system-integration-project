package com.project.system_integration.services;


import com.project.system_integration.entities.Role;
import com.project.system_integration.entities.User;
import com.project.system_integration.exceptions.UnauthorizedException;
import com.project.system_integration.models.RegisterDto;
import com.project.system_integration.models.UserDto;
import com.project.system_integration.repositories.RoleRepository;
import com.project.system_integration.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public User getUserByLogin(String login) throws Exception {
        return repository.findByLogin(login).orElseThrow(() -> new Exception("no user found"));
    }

    //return token
    public String loginUser(String login, String password) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login, password
                )
        );

        User user = getUserByLogin(login);
        System.out.println("user->  " + user);
        return jwtService.generateToken(authentication);
    }

    public boolean registerUser(RegisterDto body) throws Exception {
            Role role = roleRepository.findByRoleName(body.getRole()).orElseThrow(() -> new Exception("role doesnt exist"));
            System.out.println("TEST1@#");
            if(repository.existsByLogin(body.getLogin())) {
                throw new Exception("user already exists");
            }
            User newUser = new User(body.getLogin(), body.getPassword(), role);
            repository.save(newUser);
        return true;
    }


}
