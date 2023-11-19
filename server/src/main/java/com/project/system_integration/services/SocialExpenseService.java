package com.project.system_integration.services;

import com.project.system_integration.entities.SocialExpense;
import com.project.system_integration.entities.Unit;
import com.project.system_integration.exceptions.UnauthorizedException;
import com.project.system_integration.models.SocialExpenseDto;
import com.project.system_integration.models.UserDto;
import com.project.system_integration.repositories.SocialExpensesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SocialExpenseService {
    private final SocialExpensesRepository repository;
    private final AuthService auth;
    private final Unit TOTAL_GENERAL_GGOVERNMENT_EXPENDITURE = new Unit(1, "Total General Government Expenditure", "Percent");
    private final Unit GROSS_DOMESTIC_PRODUCT = new Unit(2, "Gross Domestic Producr", "Percent");

    public List<SocialExpenseDto> getAllExpenses() {

            List<SocialExpense> expenses = repository.findAll();
            List<SocialExpenseDto> expensesDto = new ArrayList<>();
            for (SocialExpense e :
                    expenses) {
                expensesDto.add(new SocialExpenseDto(e));
            }
            return expensesDto;

    }
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<SocialExpenseDto> getAllExpenditure() {
            List<SocialExpenseDto> expensesDto = this.getAllByUnit(TOTAL_GENERAL_GGOVERNMENT_EXPENDITURE);
            return expensesDto;
    }
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public List<SocialExpenseDto>  getAllProduct() {
            List<SocialExpenseDto> expensesDto = this.getAllByUnit(GROSS_DOMESTIC_PRODUCT);
            return expensesDto;
    }
    List<SocialExpenseDto> getAllByUnit(Unit u) {
        List<SocialExpense> expenses = repository.findAllByUnit(u);
        List<SocialExpenseDto> expensesDto = new ArrayList<>();
        for (SocialExpense e :
                expenses) {
            expensesDto.add(new SocialExpenseDto(e));
        }
        return expensesDto;
    }
}
