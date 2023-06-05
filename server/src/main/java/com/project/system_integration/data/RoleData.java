package com.project.system_integration.data;


import com.project.system_integration.entities.Role;
import com.project.system_integration.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleData implements CommandLineRunner {
    @Autowired
    private RoleRepository repository;

    private void createRoles() {
        Role admin = new Role(1, "USER");
        Role user = new Role(2, "ADMIN");
        repository.saveAll(List.of(admin, user));
        long count = repository.count();
        System.out.println("Count of roles: " + count);
    }

    @Override
    public void run(String... args) throws Exception {
        createRoles();
    }
}
