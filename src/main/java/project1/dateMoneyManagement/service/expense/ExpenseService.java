package project1.dateMoneyManagement.service.expense;

import project1.dateMoneyManagement.DTO.expense.CalendarDTO;
import project1.dateMoneyManagement.DTO.expense.DateDTO;
import project1.dateMoneyManagement.model.Expense;


public interface ExpenseService {
    public CalendarDTO getThisMonthExpense(String id);
    public Expense getDetailExpense(DateDTO date, String id);
    public boolean save(Expense expense);
    public boolean deleteMonth(int year, int month, String id);
    public boolean deleteDay(DateDTO date, String id);
}
