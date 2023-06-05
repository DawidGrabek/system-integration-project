package com.project.system_integration.data;

import com.project.system_integration.entities.Country;
import com.project.system_integration.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryData implements CommandLineRunner {
    @Autowired
    private CountryRepository repository;

    @Override
    public void run(String... args) throws Exception {
        Country c1 = new Country(1, "Poland", "PL");
        Country c2 = new Country(2, "Germany", "GM");
        repository.saveAll(List.of(c1, c2));
    }
}
