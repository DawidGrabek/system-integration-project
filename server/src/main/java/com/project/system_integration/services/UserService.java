package com.project.system_integration.services;

import com.project.system_integration.entities.Role;
import com.project.system_integration.entities.User;
import com.project.system_integration.exceptions.UnauthorizedException;
import com.project.system_integration.models.UserDto;
import com.project.system_integration.repositories.UserRepository;
import jakarta.websocket.RemoteEndpoint;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthService auth;

    public ResponseEntity<String> testAuthentication(String param1, String param2, Map<String, String> headers) {
        try {
            //get role from user
            UserDto credentials = auth.authenticate(headers);
            String login = credentials.getLogin();
            String role = credentials.getRole();
            User u = new User(3, "test123", "dupa", new Role(1, "User"));
            repository.save(u);
            return new ResponseEntity<String>("udało się zautoryzować użytkownika; login = " + login + " role = " + role, HttpStatus.OK);
        } catch (Exception e) {
            //jezeli sie nie udalo to return rensponse forbidden
            System.out.println(e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
//            throw new RuntimeException(e);
        }
    }
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResponseEntity getAllUsers(Map<String, String> headers) {
        try {
            UserDto credentials = auth.authenticateAdmin(headers);
            return new ResponseEntity(repository.findAll(), HttpStatus.OK);
        }catch (UnauthorizedException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
