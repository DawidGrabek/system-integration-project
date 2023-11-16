package com.project.system_integration.services;

import com.project.system_integration.entities.SocialExpense;
import com.project.system_integration.exceptions.UnauthorizedException;
import com.project.system_integration.models.SocialExpenseDto;
import com.project.system_integration.models.UserDto;
import com.project.system_integration.repositories.SocialExpensesRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SocialExpenseServiceTest {
    @Mock
    private SocialExpensesRepository socialExpensesRepository;
    @Mock
    private AuthService auth;

    @InjectMocks
    private SocialExpenseService tested;
    private UserDto userDto;

    @BeforeEach
    void setUp() throws Exception {
        userDto = new UserDto("Login", "USER");
        when(auth.authenticate(ArgumentMatchers.any())).thenReturn(userDto);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllExpenses_Success() {
        when(socialExpensesRepository.findAll()).thenReturn(new ArrayList<SocialExpense>());
        ResponseEntity response = tested.getAllExpenses(ArgumentMatchers.any());
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(response.getBody()).isInstanceOf(List.class);
    }

    @Test
    void getAllExpenses_Unauthorized() throws Exception {
        // Mock the AuthService to throw an UnauthorizedException
        when(auth.authenticate(ArgumentMatchers.any())).thenThrow(new UnauthorizedException("Unauthorized"));

        // Call the method under test
        ResponseEntity response = tested.getAllExpenses(ArgumentMatchers.any());

        // Assertions
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        System.out.println(response.getBody());
    }

    @Test
    void getAllExpenses_InternalServerError() {
        // Mock the repository to throw an exception
        when(socialExpensesRepository.findAll()).thenThrow(new RuntimeException("Internal server error"));

        // Call the method under test
        ResponseEntity response = tested.getAllExpenses(ArgumentMatchers.any());

        // Assertions
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void getAllExpenditure_Success() {
        // Mock the behavior of getAllByUnit to return an empty list of SocialExpenseDto
        when(socialExpensesRepository.findAllByUnit(ArgumentMatchers.any())).thenReturn(new ArrayList<SocialExpense>());

        // Call the method under test
        ResponseEntity response = tested.getAllExpenditure(ArgumentMatchers.any());

        // Assertions
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Check that the response body is a list
        Assertions.assertThat(response.getBody()).isInstanceOf(List.class);
    }

    @Test
    void getAllExpenditure_Unauthorized() throws Exception {
        // Mock the AuthService to throw an UnauthorizedException
        when(auth.authenticate(ArgumentMatchers.any())).thenThrow(new UnauthorizedException("Unauthorized"));

        // Call the method under test
        ResponseEntity response = tested.getAllExpenditure(ArgumentMatchers.any());

        // Assertions
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void getAllExpenditure_InternalServerError() {
        // Mock the behavior of getAllByUnit to throw an exception
        when(tested.getAllByUnit(ArgumentMatchers.any())).thenThrow(new RuntimeException("Internal server error"));

        // Call the method under test
        ResponseEntity response = tested.getAllExpenditure(ArgumentMatchers.any());

        // Assertions
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void getAllProduct_Success() {
        // Mock the behavior of getAllByUnit to return an empty list of SocialExpenseDto
        when(socialExpensesRepository.findAllByUnit(ArgumentMatchers.any())).thenReturn(new ArrayList<SocialExpense>());

        // Call the method under test
        ResponseEntity response = tested.getAllProduct(ArgumentMatchers.any());

        // Assertions
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Check that the response body is a list
        Assertions.assertThat(response.getBody()).isInstanceOf(List.class);
    }
    @Test
    void getAllProduct_Unauthorized() throws Exception {
        // Mock the AuthService to throw an UnauthorizedException
        when(auth.authenticate(ArgumentMatchers.any())).thenThrow(new UnauthorizedException("Unauthorized"));

        // Call the method under test
        ResponseEntity response = tested.getAllProduct(ArgumentMatchers.any());

        // Assertions
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void getAllProduct_InternalServerError() {
        // Mock the behavior of getAllByUnit to throw an exception
        when(tested.getAllByUnit(ArgumentMatchers.any())).thenThrow(new RuntimeException("Internal server error"));

        // Call the method under test
        ResponseEntity response = tested.getAllProduct(ArgumentMatchers.any());

        // Assertions
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}