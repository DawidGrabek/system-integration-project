package com.project.system_integration.repositories;

import com.project.system_integration.entities.Inflation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InflationRepository extends JpaRepository<Inflation, Integer> {
    Optional<Inflation> findByYear(Integer year);
    List<Inflation> findByValueGreaterThan(double value);
    List<Inflation> findByValueLessThan(double value);
    List<Inflation> findByYearGreaterThan(int year);
    List<Inflation> findByYearLessThan(int year);
    List<Inflation> findByYearBetween(int year1, int year2);
}
