package com.project.system_integration.data;

import com.project.system_integration.entities.Unit;
import com.project.system_integration.repositories.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnitData implements CommandLineRunner {

    private final UnitRepository repository;
    @Override
    public void run(String... args){
        Unit unit1 = new Unit(0, "Inflation", "Percent");
        Unit unit2 = new Unit(1, "Total General Government Expenditure", "Percent");
        Unit unit3 = new Unit(2, "Gross Domestic Product", "Percent");
        repository.saveAll(List.of(unit1, unit2, unit3));
    }
}
