package com.financeapp.expensetracker.service;

import com.financeapp.expensetracker.dto.DashboardResponse;
import com.financeapp.expensetracker.entity.Expense;
import com.financeapp.expensetracker.entity.Income;
import com.financeapp.expensetracker.entity.User;
import com.financeapp.expensetracker.repository.ExpenseRepository;
import com.financeapp.expensetracker.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CurrentUserService currentUserService;

    /**
     * Returns a summary for the given month (defaults to current month if year/month not passed).
     */
    public DashboardResponse getMonthlySummary(Integer year, Integer month) {
        User user = currentUserService.getCurrentUser();

        YearMonth targetMonth = (year != null && month != null)
                ? YearMonth.of(year, month)
                : YearMonth.now();

        LocalDate start = targetMonth.atDay(1);
        LocalDate end = targetMonth.atEndOfMonth();

        List<Income> incomes = incomeRepository.findByUserAndDateBetween(user, start, end);
        List<Expense> expenses = expenseRepository.findByUserAndDateBetween(user, start, end);

        BigDecimal totalIncome = incomes.stream()
                .map(Income::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpense = expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal balance = totalIncome.subtract(totalExpense);

        Map<String, BigDecimal> expenseByCategory = expenses.stream()
                .collect(Collectors.groupingBy(
                        e -> e.getCategory() != null ? e.getCategory().getName() : "Uncategorized",
                        Collectors.reducing(BigDecimal.ZERO, Expense::getAmount, BigDecimal::add)
                ));

        return new DashboardResponse(totalIncome, totalExpense, balance, expenseByCategory);
    }
}
