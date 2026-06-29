package com.financeapp.expensetracker.service;

import com.financeapp.expensetracker.dto.TransactionRequest;
import com.financeapp.expensetracker.entity.Category;
import com.financeapp.expensetracker.entity.Income;
import com.financeapp.expensetracker.entity.User;
import com.financeapp.expensetracker.exception.ResourceNotFoundException;
import com.financeapp.expensetracker.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CurrentUserService currentUserService;

    public Income addIncome(TransactionRequest request) {
        User user = currentUserService.getCurrentUser();
        Category category = categoryService.getById(request.getCategoryId());

        Income income = new Income();
        income.setAmount(request.getAmount());
        income.setDescription(request.getDescription());
        income.setDate(request.getDate());
        income.setCategory(category);
        income.setUser(user);

        return incomeRepository.save(income);
    }

    public List<Income> getMyIncomes() {
        User user = currentUserService.getCurrentUser();
        return incomeRepository.findByUser(user);
    }

    public Income updateIncome(Long id, TransactionRequest request) {
        Income income = getOwnedIncome(id);
        Category category = categoryService.getById(request.getCategoryId());

        income.setAmount(request.getAmount());
        income.setDescription(request.getDescription());
        income.setDate(request.getDate());
        income.setCategory(category);

        return incomeRepository.save(income);
    }

    public void deleteIncome(Long id) {
        Income income = getOwnedIncome(id);
        incomeRepository.delete(income);
    }

    // Ensures a user can only access/modify their own income records
    private Income getOwnedIncome(Long id) {
        User user = currentUserService.getCurrentUser();
        Income income = incomeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Income not found with id: " + id));

        if (!income.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException("Income not found with id: " + id);
        }
        return income;
    }
}
