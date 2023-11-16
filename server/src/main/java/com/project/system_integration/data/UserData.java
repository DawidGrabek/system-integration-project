package com.project.system_integration.data;

import com.project.system_integration.entities.Role;
import com.project.system_integration.entities.User;
import com.project.system_integration.repositories.RoleRepository;
import com.project.system_integration.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserData implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    private void addUsers() {
        User user = new User( 0, "user", passwordEncoder.encode( "1234"), new Role(0, "USER"));
        User admin = new User(1, "admin", passwordEncoder.encode( "1234"), new Role(1, "ADMIN"));
        userRepository.saveAll(List.of(admin, user));
        List<User> users = userRepository.findAll();
        System.out.println("All users: " + users);
    }

    @Override
    public void run(String... args) throws Exception {
        addUsers();
    }
}
