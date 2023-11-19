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
    private final AuthService auth;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<User> getAllUsers(Map<String, String> headers) {
            return repository.findAll();
    }
}
