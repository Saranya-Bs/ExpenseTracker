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

import com.expensetracker.expense_tracker.model.Expense;
import com.expensetracker.expense_tracker.service.ExpenseService;

@RestController
@RequestMapping("/api")
public class ExpenseController {

    @Autowired
    ExpenseService exservice;
    
    @GetMapping("/expenses")
    public List<Expense> getAllExpenses(){
        return exservice.getAllExpenses();
    }

    @GetMapping("/expenses/paged")
    public Page<Expense> getAllExpensesByPage(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "5") int size){
        return exservice.getAllExpensesByPage(page,size);
    }

    @GetMapping("/expenses/{id}")
    public Expense getExpensebyId(@PathVariable long id){
        return exservice.getExpensebyId(id);
    }

    @GetMapping("/expenses/filter")
    public List<Expense> getFilteredExpense(@RequestParam(required = false) String category,@RequestParam(required = false) LocalDate startDate,@RequestParam(required = false) LocalDate endDate,@RequestParam(required = false) String title){
        return exservice.getFilteredExpense(category,startDate,endDate,title);
    }

    @PostMapping("/expenses")
    public Expense addExpense(@RequestBody Expense exp){
        return exservice.addExpense(exp);
    }

    

    @PutMapping("/expenses/{id}")
    public Expense updateExpense(@PathVariable long id,@RequestBody Expense details){
        return exservice.updateExpense(id,details);
    }

    @DeleteMapping("/expenses/{id}")
    public String deleteExpense(@PathVariable long id){
        return exservice.deleteExpense(id);
    }


}
