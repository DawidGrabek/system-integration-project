package com.project.system_integration.data;

import com.project.system_integration.entities.Role;
import com.project.system_integration.entities.User;
import com.project.system_integration.repositories.RoleRepository;
import com.project.system_integration.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserData implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    private void addUsers() {
        User user = new User( 1, "user", "1234", new Role(2, "USER"));
        User admin = new User(2, "admin", "1234", new Role(1, "ADMIN"));
        userRepository.saveAll(List.of(admin, user));
        List<User> users = userRepository.findAll();
        System.out.println("All users: " + users);
    }

    @Override
    public void run(String... args) throws Exception {
        addUsers();
    }
}
