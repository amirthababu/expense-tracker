package com.financeapp.expensetracker.repository;

import com.financeapp.expensetracker.entity.Income;
import com.financeapp.expensetracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> findByUser(User user);
    List<Income> findByUserAndDateBetween(User user, LocalDate start, LocalDate end);
}
