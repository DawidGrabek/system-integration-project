package com.project.system_integration.data;

import com.project.system_integration.entities.Country;
import com.project.system_integration.entities.Inflation;
import com.project.system_integration.entities.Unit;
import com.project.system_integration.repositories.InflationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class InflationData implements CommandLineRunner {

    private final InflationRepository repository;
    private final String INFLATION_PATH = "";
    @Override
    public void run(String... args) throws Exception {
        Country c = new Country(1, "Poland", "PL");
        Unit u = new Unit(1, "Inflation", "Percent");
        List<Inflation> socialExpenses = new ArrayList<>();
        int i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(INFLATION_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                socialExpenses.add(new Inflation(i, Integer.parseInt(values[0]), Double.parseDouble(values[1]),c, u));
                i++;
            }
        }
        repository.saveAll(socialExpenses);
    }
}
