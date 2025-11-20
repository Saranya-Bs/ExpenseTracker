package com.expensetracker.expense_tracker.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExpenseDto {

    private int id;

    @NotBlank(message = "Enter a valid title.The title should not be empty")
    private String title;

    @NotNull(message = "Amount should not be empty")
    @Min(value = 1,message = "Amount should be greater than zero")
    private Double amount;

    @NotBlank(message = "Category should be specified")
    private String category;

    @NotNull(message="Date is required!")
    private LocalDate date;
    
}
