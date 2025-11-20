package com.expensetracker.expense_tracker.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.expensetracker.expense_tracker.dto.ExpenseDto;
import com.expensetracker.expense_tracker.entity.Expense;
import com.expensetracker.expense_tracker.repository.UserRepository;
import com.expensetracker.expense_tracker.service.ExpenseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    ExpenseService exservice;
    
    @GetMapping
    public List<Expense> getAllExpenses(){
        return exservice.getAllExpenses();
    }

    @GetMapping("/paged")
    public Page<Expense> getAllExpensesByPage(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "5") int size,@RequestParam(defaultValue = "date") String sortField,@RequestParam(defaultValue = "asc") String direction){
        return exservice.getAllExpensesByPage(page,size,sortField,direction);
    }

    @GetMapping("/{id}")
    public Expense getExpensebyId(@PathVariable int id){
        return exservice.getExpensebyId(id);
    }

    @GetMapping("/filter")
    public List<Expense> getFilteredExpense(@RequestParam(required = false) String category,@RequestParam(required = false) LocalDate startDate,@RequestParam(required = false) LocalDate endDate,@RequestParam(required = false) String title){
        return exservice.getFilteredExpense(category,startDate,endDate,title);
    }

    @Autowired
    UserRepository userrepo;


    @PostMapping("/{userId}")
    public Expense addExpense(@Valid @RequestBody ExpenseDto exp,@PathVariable int userId){
        
        return exservice.addExpense(exp,userId);
    }

    @PutMapping("/{id}")
    public Expense updateExpense(@PathVariable long id,@RequestBody Expense details){
        return exservice.updateExpense(id,details);
    }

    @DeleteMapping("/{id}")
    public String deleteExpense(@PathVariable long id){
        return exservice.deleteExpense(id);
    }


}
