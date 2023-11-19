//package com.project.system_integration.controllers;
//
//import com.project.system_integration.models.SocialExpenseDto;
//import com.project.system_integration.services.SocialExpenseService;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(ExpensesController.class)
//class ExpensesControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//    @MockBean
//    SocialExpenseService service;
//    private Map<String, String> headers;
//    @BeforeEach
//    void setUp() {
//        headers = new HashMap();
//    }
//
//    @AfterEach
//    void tearDown() {
//    }
//
//    @Test
//    void getAllExpenses() throws Exception{
//        headers.put("authorization", "Bearer 1234");
//        when(service.getAllExpenses(headers)).thenReturn(new ResponseEntity(new ArrayList< SocialExpenseDto >(), HttpStatus.OK));
//        this.mockMvc.perform(get("/api/v1/expense").header("authorization", "Bearer 1234")).andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void getAllExpenditure() throws Exception{
//        headers.put("authorization", "Bearer 1234");
//        when(service.getAllExpenditure(headers)).thenReturn(new ResponseEntity(new ArrayList< SocialExpenseDto >(), HttpStatus.OK));
//        this.mockMvc.perform(get("/api/v1/expense/expenditure").header("authorization", "Bearer 1234")).andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void getAllProduct() throws Exception{
//        headers.put("authorization", "Bearer 1234");
//        when(service.getAllProduct(headers)).thenReturn(new ResponseEntity(new ArrayList< SocialExpenseDto >(), HttpStatus.OK));
//        this.mockMvc.perform(get("/api/v1/expense/product").header("authorization", "Bearer 1234")).andDo(print())
//                .andExpect(status().isOk());
//    }
//}