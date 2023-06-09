package com.project.system_integration.data;

import com.project.system_integration.entities.Country;
import com.project.system_integration.entities.SocialExpense;
import com.project.system_integration.entities.Unit;
import com.project.system_integration.repositories.SocialExpensesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ZSocialExpenseData implements CommandLineRunner {
    private final String SOCIAL_EXPENSE_ADDRESS1 = "/expenses1.csv";
    private final String SOCIAL_EXPENSE_ADDRESS2 = "/expenses2.csv";
    private final SocialExpensesRepository repository;
    @Override
    public void run(String... args) throws Exception {
        String filePath = new File("").getAbsolutePath();
        filePath = filePath + "/src/main/java/com/project/system_integration/data/source";
//        List<List<String>> records = new ArrayList<>();
        List<SocialExpense> socialExpenses = new ArrayList<>();
//        int i = 0;
        Country c = new Country(0, "Poland", "PL");
        Unit u = new Unit(1, "Total General Government Expenditure", "Percent");
        try (BufferedReader br = new BufferedReader(new FileReader(filePath + SOCIAL_EXPENSE_ADDRESS1))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
//                records.add(Arrays.asList(values));
                socialExpenses.add(new SocialExpense(Integer.parseInt(values[0]), Double.parseDouble(values[1]),c, u));
//                i++;
            }
        }

        u = new Unit(2, "Gross Domestic Product", "Percent");
        try (BufferedReader br = new BufferedReader(new FileReader(filePath + SOCIAL_EXPENSE_ADDRESS2))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
//                records.add(Arrays.asList(values));
                socialExpenses.add(new SocialExpense( Integer.parseInt(values[0]), Double.parseDouble(values[1]),c, u));
//                i++;
            }
        }

        repository.saveAll(socialExpenses);

    }
}
