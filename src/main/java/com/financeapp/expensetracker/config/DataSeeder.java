package com.financeapp.expensetracker.config;

import com.financeapp.expensetracker.entity.Category;
import com.financeapp.expensetracker.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run(String... args) {
        if (categoryRepository.count() == 0) {
            categoryRepository.save(new Category(null, "Salary", Category.CategoryType.INCOME));
            categoryRepository.save(new Category(null, "Freelance", Category.CategoryType.INCOME));
            categoryRepository.save(new Category(null, "Other Income", Category.CategoryType.INCOME));

            categoryRepository.save(new Category(null, "Food", Category.CategoryType.EXPENSE));
            categoryRepository.save(new Category(null, "Travel", Category.CategoryType.EXPENSE));
            categoryRepository.save(new Category(null, "Rent", Category.CategoryType.EXPENSE));
            categoryRepository.save(new Category(null, "Utilities", Category.CategoryType.EXPENSE));
            categoryRepository.save(new Category(null, "Entertainment", Category.CategoryType.EXPENSE));
            categoryRepository.save(new Category(null, "Other Expense", Category.CategoryType.EXPENSE));

            System.out.println("Default categories seeded successfully.");
        }
    }
}
