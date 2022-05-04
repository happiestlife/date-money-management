package project1.dateMoneyManagement.service.expense;

import project1.dateMoneyManagement.DTO.expense.CalendarDTO;
import project1.dateMoneyManagement.model.Expense;

import java.util.List;

public interface ExpenseService {
    public CalendarDTO getThisMonthExpense(String id);
}
