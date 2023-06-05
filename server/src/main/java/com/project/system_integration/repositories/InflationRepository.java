package com.project.system_integration.repositories;

import com.project.system_integration.entities.Inflation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InflationRepository extends JpaRepository<Inflation, Integer> {
    Optional<Inflation> findByYear(Integer year);
}
