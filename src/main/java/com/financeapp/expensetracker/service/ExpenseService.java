package com.financeapp.expensetracker.service;

import com.financeapp.expensetracker.dto.TransactionRequest;
import com.financeapp.expensetracker.entity.Category;
import com.financeapp.expensetracker.entity.Expense;
import com.financeapp.expensetracker.entity.User;
import com.financeapp.expensetracker.exception.ResourceNotFoundException;
import com.financeapp.expensetracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CurrentUserService currentUserService;

    public Expense addExpense(TransactionRequest request) {
        User user = currentUserService.getCurrentUser();
        Category category = categoryService.getById(request.getCategoryId());

        Expense expense = new Expense();
        expense.setAmount(request.getAmount());
        expense.setDescription(request.getDescription());
        expense.setDate(request.getDate());
        expense.setCategory(category);
        expense.setUser(user);

        return expenseRepository.save(expense);
    }

    public List<Expense> getMyExpenses() {
        User user = currentUserService.getCurrentUser();
        return expenseRepository.findByUser(user);
    }

    public Expense updateExpense(Long id, TransactionRequest request) {
        Expense expense = getOwnedExpense(id);
        Category category = categoryService.getById(request.getCategoryId());

        expense.setAmount(request.getAmount());
        expense.setDescription(request.getDescription());
        expense.setDate(request.getDate());
        expense.setCategory(category);

        return expenseRepository.save(expense);
    }

    public void deleteExpense(Long id) {
        Expense expense = getOwnedExpense(id);
        expenseRepository.delete(expense);
    }

    // Ensures a user can only access/modify their own expense records
    private Expense getOwnedExpense(Long id) {
        User user = currentUserService.getCurrentUser();
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));

        if (!expense.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException("Expense not found with id: " + id);
        }
        return expense;
    }
}
