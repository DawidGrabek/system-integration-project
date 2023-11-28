package com.project.system_integration.controllers;

import com.project.system_integration.models.SocialExpenseDto;
import com.project.system_integration.repositories.UserRepository;
import com.project.system_integration.services.SocialExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ExpensesController.class)
@AutoConfigureMockMvc(addFilters = false)
class ExpensesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SocialExpenseService socialExpenseService;
    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        // Set up any required configurations or test data here
    }

    @Test
    void getAllExpenses_ReturnsOkStatus() throws Exception {
        when(socialExpenseService.getAllExpenses()).thenReturn(Arrays.asList(new SocialExpenseDto()));

        this.mockMvc.perform(get("/api/v1/expense"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getAllExpenditure_ReturnsOkStatus() throws Exception {
        when(socialExpenseService.getAllExpenditure()).thenReturn(Arrays.asList(new SocialExpenseDto()));

        this.mockMvc.perform(get("/api/v1/expense/expenditure"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getAllProduct_ReturnsOkStatus() throws Exception {
        when(socialExpenseService.getAllProduct()).thenReturn(Arrays.asList(new SocialExpenseDto()));

        this.mockMvc.perform(get("/api/v1/expense/product"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}