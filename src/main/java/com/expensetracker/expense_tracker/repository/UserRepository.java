package com.expensetracker.expense_tracker.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expensetracker.expense_tracker.entity.User;

public interface UserRepository extends JpaRepository<User,Integer>{
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);
    
} 
