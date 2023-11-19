package com.project.system_integration.controllers;

import com.project.system_integration.services.SocialExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/expense")
@RequiredArgsConstructor
@CrossOrigin
public class ExpensesController {

    private final SocialExpenseService service;
    @GetMapping("")
    public ResponseEntity getAllExpenses() {
        return service.getAllExpenses();
    }

    @GetMapping("/expenditure")
    public ResponseEntity getAllExpenditure() {
        return service.getAllExpenditure();
    }

    @GetMapping("/product")
    public ResponseEntity getAllProduct() {
        return service.getAllProduct();
    }

}
