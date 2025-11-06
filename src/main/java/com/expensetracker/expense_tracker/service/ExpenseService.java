package com.expensetracker.expense_tracker.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expensetracker.expense_tracker.model.Expense;
import com.expensetracker.expense_tracker.repository.ExpenseRepository;

@Service
public class ExpenseService {
    @Autowired
    ExpenseRepository exrepo;

    public List<Expense> getAllExpenses(){
        return exrepo.findAll();
    }

    public Expense addExpense(Expense exp) {
        return exrepo.save(exp);
    }

    public Expense getExpensebyId(long id) {
       return exrepo.findById(id).orElse(new Expense());
    }

    public Expense updateExpense(long id,Expense details) {
        Expense e= exrepo.findById(id).orElse(null);
        if(e!=null){
            e.setAmount(details.getAmount());
            e.setCategory(details.getCategory());
            e.setDate(details.getDate());
        }
        return exrepo.save(e);
    }

    public String deleteExpense(long id) {
        if(exrepo.existsById(id)){
        exrepo.deleteById(id);
        return "Expense removed";
        }
        else{
            return "Expense does not exist!";
        }
    }

    public List<Expense> getFilteredExpense(String category, LocalDate startDate, LocalDate endDate, String title) {
        if(category!=null && title!=null){
            return exrepo.findByCategoryAndTitle(category,title);
        }
        if(category!=null){
            return exrepo.findByCategory(category);
        }
        if(title!=null){
            return exrepo.findByTitle(title);
        }
        if(startDate!=null&&endDate!=null){
            return exrepo.findByDateBetween(startDate,endDate);
        }
        return exrepo.findAll();
    }

    public Page<Expense> getAllExpensesByPage(int page, int size) {
        PageRequest p = PageRequest.of(page, size);
        return exrepo.findAll(p);

    }


}
