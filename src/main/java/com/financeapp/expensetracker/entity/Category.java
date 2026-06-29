package com.financeapp.expensetracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // e.g. Food, Travel, Rent, Salary, Freelance

    @Enumerated(EnumType.STRING)
    private CategoryType type; // INCOME or EXPENSE

    public enum CategoryType {
        INCOME, EXPENSE
    }
}
