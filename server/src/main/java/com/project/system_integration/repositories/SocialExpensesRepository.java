package com.project.system_integration.repositories;

import com.project.system_integration.entities.SocialExpense;
import com.project.system_integration.entities.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SocialExpensesRepository extends JpaRepository<SocialExpense, Integer> {
    Optional<SocialExpense> findByYear(Integer year);
    List<SocialExpense> findAllByUnit(Unit unit);
}
