package com.expensetracker.expense_tracker.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expensetracker.expense_tracker.dto.ExpenseDto;
import com.expensetracker.expense_tracker.entity.Expense;
import com.expensetracker.expense_tracker.entity.User;
import com.expensetracker.expense_tracker.repository.ExpenseRepository;
import com.expensetracker.expense_tracker.repository.UserRepository;

@Service
public class ExpenseService {
    @Autowired
    ExpenseRepository exrepo;

    @Autowired
    UserRepository userrepo;

    public List<Expense> getAllExpenses() {
        return exrepo.findAll();
    }

    public Expense getExpensebyId(long id) {
        if(exrepo.existsById(id))
        return exrepo.findById(id).get();
        throw new RuntimeException("Expense Not found with given id");
    }

    public List<Expense> getFilteredExpense(String category, LocalDate startDate, LocalDate endDate, String title) {
        if (category != null && title != null) {
            return exrepo.findByCategoryAndTitle(category, title);
        }
        if (category != null) {
            return exrepo.findByCategory(category);
        }
        if (title != null) {
            return exrepo.findByTitle(title);
        }
        if (startDate != null && endDate != null) {
            return exrepo.findByDateBetween(startDate, endDate);
        }
        return exrepo.findAll();
    }

    public Page<Expense> getAllExpensesByPage(int page, int size, String sortField, String direction) {
        PageRequest p = PageRequest.of(page,
                size,
                Sort.by(Sort.Direction.fromString(direction), sortField));
        return exrepo.findAll(p);

    }

    public Expense addExpense(ExpenseDto exp, int userId) {
        Expense expense = new Expense();
        expense.setAmount(exp.getAmount());
        expense.setCategory(exp.getCategory());
        expense.setTitle(exp.getTitle());
        expense.setDate(exp.getDate());
        expense.setId(exp.getId());

        User user = userrepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found!!!"));
        expense.setUser(user);

        return exrepo.save(expense);

    }

    public Expense updateExpense(long id, Expense details) {
        Expense e = exrepo.findById(id).orElse(null);
        if (e != null) {
            e.setAmount(details.getAmount());
            e.setCategory(details.getCategory());
            e.setDate(details.getDate());
            e.setUser(userrepo.findById(details.getId()).get());
        }
        return exrepo.save(e);
    }

    public String deleteExpense(long id) {
        if (exrepo.existsById(id)) {
            exrepo.deleteById(id);
            return "Expense removed";
        } else {
            return "Expense does not exist!";
        }
    }

}
