package com.project.system_integration.controllers;

import com.project.system_integration.models.SocialExpenseDto;
import com.project.system_integration.services.SocialExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/expense")
@RequiredArgsConstructor
@CrossOrigin
public class ExpensesController {

    private final SocialExpenseService service;
    @GetMapping("")
    public ResponseEntity<List<SocialExpenseDto>> getAllExpenses() {
        return new ResponseEntity<>(service.getAllExpenses(), HttpStatus.OK);
    }

    @GetMapping("/expenditure")
    public ResponseEntity<List<SocialExpenseDto>> getAllExpenditure() {
        return new ResponseEntity<>(service.getAllExpenditure(), HttpStatus.OK);
    }

    @GetMapping("/product")
    public ResponseEntity<List<SocialExpenseDto>> getAllProduct() {
        return new ResponseEntity<>(service.getAllProduct(), HttpStatus.OK);
    }

}
