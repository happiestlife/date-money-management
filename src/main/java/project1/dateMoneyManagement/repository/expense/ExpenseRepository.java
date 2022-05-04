package project1.dateMoneyManagement.repository.expense;

import project1.dateMoneyManagement.model.Expense;

import java.util.List;

public interface ExpenseRepository {
    public List<Expense> getMonthData(int year, int month, String id);
}
