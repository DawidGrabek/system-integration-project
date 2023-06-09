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

    public ResponseEntity getAllExpenses(Map<String, String> headers) {
        try {
            UserDto user = auth.authenticateAdmin(headers);
            List<SocialExpense> expenses = repository.findAll();
            List<SocialExpenseDto> expensesDto = new ArrayList<>();
            for (SocialExpense e :
                    expenses) {
                expensesDto.add(new SocialExpenseDto(e));
            }
            return new ResponseEntity(expensesDto, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity getAllExpenditure(Map<String, String> headers) {
        try {
            UserDto user = auth.authenticateAdmin(headers);
            List<SocialExpenseDto> expensesDto = this.getAllByUnit(TOTAL_GENERAL_GGOVERNMENT_EXPENDITURE);
            return new ResponseEntity(expensesDto, HttpStatus.OK);
        }catch (UnauthorizedException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity getAllProduct(Map<String, String> headers) {
        try {
            UserDto user = auth.authenticateAdmin(headers);
            List<SocialExpenseDto> expensesDto = this.getAllByUnit(GROSS_DOMESTIC_PRODUCT);
            return new ResponseEntity(expensesDto, HttpStatus.OK);
        }catch (UnauthorizedException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<SocialExpenseDto> getAllByUnit(Unit u) {
        List<SocialExpense> expenses = repository.findAllByUnit(u);
        List<SocialExpenseDto> expensesDto = new ArrayList<>();
        for (SocialExpense e :
                expenses) {
            expensesDto.add(new SocialExpenseDto(e));
        }
        return expensesDto;
    }
}
