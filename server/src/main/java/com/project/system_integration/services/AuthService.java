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
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;

    public User getUserByLogin(String login) throws Exception {
        return repository.findByLogin(login).orElseThrow(() -> new Exception("no user found"));
    }

    //return token
    public ResponseEntity<String> loginUser(String login, String password) {
        try {
            User user = getUserByLogin(login);
            System.out.println("user->  " + user);
//            if (user == null) {
//            }
            if (user.getPassword().equals(password)) {
                return new ResponseEntity<>(jwtService.generateToken(login, user.getRole().getRoleName()), HttpStatus.OK);

            }
            throw new Exception("wrong password");

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
    //return role of user
    public UserDto authenticate(Map<String, String> headers) throws Exception, UnauthorizedException {
        String authHeader = null;
        if(headers.containsKey("authorization")) {
            authHeader = headers.get("authorization");
        }
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("no token in headers");
        }
        final String jwt = authHeader.substring(7);
        final String login = jwtService.extractLogin(jwt);
        final String role = jwtService.extractRole(jwt);
        if(login != null) {
            User dbUser = getUserByLogin(login);
            if(jwtService.isTokenValid(jwt, dbUser.getLogin())) {
                return new UserDto(login, role);
            }
        }
        throw new Exception("user with this login doesnt exist");
    }

    public UserDto authenticateAdmin(Map<String, String> headers) throws Exception, UnauthorizedException{
        UserDto user = authenticate(headers);
        System.out.println(user.getRole());
        if(user.getRole().equals("ADMIN")) {
            return user;
        }
        throw new UnauthorizedException("No role required");
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
