package com.project.system_integration.integration;

import com.project.system_integration.models.SocialExpenseDto;
import com.project.system_integration.repositories.SocialExpensesRepository;
import com.project.system_integration.services.SocialExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Arrays;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:db;DB_CLOSE_DELAY=-1;NON_KEYWORDS=value,year",
        "spring.datasource.password=sa",
        "spring.datasource.username=sa",
        "spring.datasource.driver-class-name=org.h2.Driver"
})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SocialExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SocialExpensesRepository repository;

    @BeforeEach
    void setUp() {
        // Set up any required configurations or test data here
    }

    @Test
    void getAllExpenses_ReturnsOkStatus() throws Exception {

        this.mockMvc.perform(get("/api/v1/expense"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1)))) ;

    }

    @Test
    void getAllExpenditure_ReturnsOkStatus() throws Exception {

        this.mockMvc.perform(get("/api/v1/expense/expenditure"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1)))) ;
    }

    @Test
    void getAllProduct_ReturnsOkStatus() throws Exception {

        this.mockMvc.perform(get("/api/v1/expense/product"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1)))) ;
    }
}