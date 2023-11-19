//package com.project.system_integration.controllers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.project.system_integration.models.InflationDto;
//import com.project.system_integration.models.SocialExpenseDto;
//import com.project.system_integration.services.InflationService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(InflationController.class)
//class InflationControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @MockBean
//    private InflationService service;
//    private Map<String, String> headers;
//    @BeforeEach
//    void setUp() {
//        headers = new HashMap();
//    }
//    @Test
//    void getInflation() throws Exception {
//        headers.put("authorization", "Bearer 1234");
//        when(service.getAllInflations(headers)).thenReturn(new ResponseEntity(new ArrayList<SocialExpenseDto>(), HttpStatus.OK));
//        this.mockMvc.perform(get("/api/v1/inflation").header("authorization", "Bearer 1234")).andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void addInflation() throws Exception {
//        headers.put("authorization", "Bearer 1234");
//        InflationDto inflationDto = new InflationDto(2020, 10.0, "Title", "unit", "country");
//        when(service.addInflation(headers, inflationDto)).thenReturn(new ResponseEntity(new ArrayList<SocialExpenseDto>(), HttpStatus.OK));
//        this.mockMvc.perform(post("/api/v1/inflation").header("authorization", "Bearer 1234").contentType(MediaType.APPLICATION_JSON).content(asJsonString(inflationDto))).andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void getInflationByYear() throws Exception {
//        int year = 2020;
//        headers.put("authorization", "Bearer 1234");
//        when(service.getOneByYear(headers, year)).thenReturn(new ResponseEntity(new ArrayList<SocialExpenseDto>(), HttpStatus.OK));
//        this.mockMvc.perform(get("/api/v1/inflation/"+year).header("authorization", "Bearer 1234")).andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void getInflationByYearXml() throws Exception {
//        int year = 2020;
//        headers.put("authorization", "Bearer 1234");
//        when(service.getOneByYearXml(headers, year)).thenReturn(new ResponseEntity(new ArrayList<SocialExpenseDto>(), HttpStatus.OK));
//        this.mockMvc.perform(get("/api/v1/inflation/xml/"+year).header("authorization", "Bearer 1234")).andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    public static String asJsonString(final Object obj) {
//        try {
//            return new ObjectMapper().writeValueAsString(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//}