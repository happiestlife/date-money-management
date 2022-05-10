package project1.dateMoneyManagement.repository.expense;

import project1.dateMoneyManagement.DTO.expense.DateDTO;
import project1.dateMoneyManagement.model.Expense;

import java.util.List;

public interface ExpenseRepository {
    public List<Expense> readMonthExpense(int year, int month, String id);
    public Expense readDetailExpense(DateDTO date, String id);
    public boolean updateExpense(Expense expense);
    public boolean createExpense(Expense expense);
}
