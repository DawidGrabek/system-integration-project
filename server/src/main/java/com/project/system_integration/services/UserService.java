package com.project.system_integration.services;

import com.project.system_integration.config.JwtService;
import com.project.system_integration.entities.User;
import com.project.system_integration.models.UserDto;
import com.project.system_integration.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.javatuples.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final JwtService jwtService;

    public User getUserByLogin(String login) throws Exception {
        return repository.findByLogin(login).orElseThrow(() -> new Exception("no user found"));
    }

    //return token
    public ResponseEntity<String> loginUser(String login, String password) {
        try {
            User user = getUserByLogin(login);
            System.out.println("user:   " + user);
//            if (user == null) {
//            }
            if (user.getPassword().equals(password)) {
                return new ResponseEntity<>(jwtService.generateToken(login, "ADMIN"), HttpStatus.OK);

            }
            throw new Exception("wrong password");

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
    //return role of user
    public UserDto authenticate(Map<String, String> headers) throws Exception{
        String authHeader = null;
        if(headers.containsKey("authorization")) {
            authHeader = headers.get("authorization");
        }
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new Exception("no token in headers");
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

    public ResponseEntity<String> testAuthentication(String param1, String param2, Map<String, String> headers) {
        try {
            //get role from user
            UserDto credentials = authenticate(headers);
            String login = credentials.getLogin();
            String role = credentials.getRole();
            return new ResponseEntity<String>("udało się zautoryzować użytkownika; login = " + login + " role = " + role, HttpStatus.OK);
        } catch (Exception e) {
            //jezeli sie nie udalo to return rensponse forbidden
            System.out.println(e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
//            throw new RuntimeException(e);
        }
    }
}
