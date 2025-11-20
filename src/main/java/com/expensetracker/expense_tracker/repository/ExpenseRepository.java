package com.expensetracker.expense_tracker.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expensetracker.expense_tracker.entity.Expense;

public interface ExpenseRepository extends JpaRepository<Expense,Long>{

    List<Expense> findByCategory(String category);

    List<Expense> findByTitle(String title);

    List<Expense> findByDateBetween(LocalDate start,LocalDate end);

    List<Expense> findByCategoryAndTitle(String category,String title);
    
    List<Expense> findByUserId(Integer userid);
    
    
} 
