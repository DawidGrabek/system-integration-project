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
    public ResponseEntity<String> loginUser(String login, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login, password
                )
        );
        try {
            User user = getUserByLogin(login);
            System.out.println("user->  " + user);
            return new ResponseEntity<>(jwtService.generateToken(authentication), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<String> registerUser(RegisterDto body) {
        try {
            Role role = roleRepository.findByRoleName(body.getRole()).orElseThrow(() -> new Exception("role doesnt exist"));
            System.out.println("TEST1@#");
            if(repository.existsByLogin(body.getLogin())) {
                return new ResponseEntity<>("user already exist", HttpStatus.CONFLICT);
            }
            User newUser = new User(body.getLogin(), body.getPassword(), role);
            repository.save(newUser);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }


}
