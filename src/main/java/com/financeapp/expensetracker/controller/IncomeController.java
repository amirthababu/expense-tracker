package com.financeapp.expensetracker.controller;

import com.financeapp.expensetracker.dto.TransactionRequest;
import com.financeapp.expensetracker.entity.Income;
import com.financeapp.expensetracker.service.IncomeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incomes")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @PostMapping
    public ResponseEntity<Income> addIncome(@Valid @RequestBody TransactionRequest request) {
        return ResponseEntity.ok(incomeService.addIncome(request));
    }

    @GetMapping
    public ResponseEntity<List<Income>> getMyIncomes() {
        return ResponseEntity.ok(incomeService.getMyIncomes());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Income> updateIncome(@PathVariable Long id, @Valid @RequestBody TransactionRequest request) {
        return ResponseEntity.ok(incomeService.updateIncome(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable Long id) {
        incomeService.deleteIncome(id);
        return ResponseEntity.noContent().build();
    }
}
