package com.project.system_integration.data;

import com.project.system_integration.entities.Country;
import com.project.system_integration.entities.Inflation;
import com.project.system_integration.entities.Unit;
import com.project.system_integration.repositories.InflationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ZInflationData implements CommandLineRunner {

    private final InflationRepository repository;
    @Override
    public void run(String... args) throws Exception {
        String filePath = new File("").getAbsolutePath();
        filePath = filePath + "/src/main/java/com/project/system_integration/data/source/inflation.csv";
        Country c = new Country(0, "Poland", "PL");
        Unit u = new Unit(0, "Inflation", "Percent");
        List<Inflation> socialExpenses = new ArrayList<>();
//        int i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                socialExpenses.add(new Inflation( Integer.parseInt(values[0]), Double.parseDouble(values[1]),c, u));
            }
        }
        repository.saveAll(socialExpenses);
    }
}
