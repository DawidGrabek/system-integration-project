package com.project.system_integration.services;

import com.project.system_integration.entities.Country;
import com.project.system_integration.entities.SocialExpense;
import com.project.system_integration.entities.Unit;
import com.project.system_integration.models.SocialExpenseDto;
import com.project.system_integration.repositories.SocialExpensesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SocialExpenseServiceTest {

    @Mock
    private SocialExpensesRepository socialExpensesRepository;
    @InjectMocks
    private SocialExpenseService socialExpenseService;

    private Unit totalGeneralGovernmentExpenditure;
    private Unit grossDomesticProduct;
    private SocialExpense socialExpense;
    @BeforeEach
    void setUp() {
        totalGeneralGovernmentExpenditure = new Unit(1, "Total General Government Expenditure", "Percent");
        grossDomesticProduct = new Unit(2, "Gross Domestic Product", "Percent");
        socialExpense = new SocialExpense(2020, 1.0, new Country(1, "Test country", "CountryCode"), grossDomesticProduct);
    }

    @Test
    void getAllExpenses_Success() {
        when(socialExpensesRepository.findAll()).thenReturn(Collections.singletonList(socialExpense));

        List<SocialExpenseDto> result = socialExpenseService.getAllExpenses();

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void getAllExpenditure_Success() {
        when(socialExpensesRepository.findAllByUnit(totalGeneralGovernmentExpenditure))
                .thenReturn(Collections.singletonList(socialExpense));

        List<SocialExpenseDto> result = socialExpenseService.getAllExpenditure();

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void getAllProduct_Success() {
        when(socialExpensesRepository.findAllByUnit(grossDomesticProduct))
                .thenReturn(Collections.singletonList(socialExpense));

        List<SocialExpenseDto> result = socialExpenseService.getAllProduct();

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void getAllByUnit_WithValidUnit_ReturnsNonEmptyList() {
        SocialExpense expense1 = new SocialExpense(2020, 1.0, new Country(1, "Test country", "CountryCode"), grossDomesticProduct);

        SocialExpense expense2 = new SocialExpense(2029, 1.0, new Country(1, "Test country", "CountryCode"), grossDomesticProduct);
        // Mock expenses data
        when(socialExpensesRepository.findAllByUnit(grossDomesticProduct))
                .thenReturn(Arrays.asList(expense1, expense2));

        List<SocialExpenseDto> result = socialExpenseService.getAllByUnit(grossDomesticProduct);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void getAllByUnit_WithValidUnit_ReturnsEmptyList() {
        // Mock repository to return an empty list
        when(socialExpensesRepository.findAllByUnit(grossDomesticProduct))
                .thenReturn(Arrays.asList());

        List<SocialExpenseDto> result = socialExpenseService.getAllByUnit(grossDomesticProduct);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}