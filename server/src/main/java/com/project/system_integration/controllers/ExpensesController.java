package com.project.system_integration.controllers;

import com.project.system_integration.services.SocialExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/expense")
@RequiredArgsConstructor
public class ExpensesController {

    private final SocialExpenseService service;
    @GetMapping("")
    public ResponseEntity getAllExpenses(@RequestHeader Map<String, String> headers) {
        return service.getAllExpenses(headers);
    }

    @GetMapping("/expenditure")
    public ResponseEntity getAllExpenditure(@RequestHeader Map<String, String> headers) {
        return service.getAllExpenditure(headers);
    }

}
