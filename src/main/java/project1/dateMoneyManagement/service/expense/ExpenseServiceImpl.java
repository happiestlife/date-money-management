package project1.dateMoneyManagement.service.expense;

import org.springframework.stereotype.Service;
import project1.dateMoneyManagement.DTO.expense.CalendarDTO;
import project1.dateMoneyManagement.model.Expense;
import project1.dateMoneyManagement.repository.expense.ExpenseRepository;

import java.util.*;

@Service
public class ExpenseServiceImpl implements ExpenseService{

    private ExpenseRepository expenseRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public CalendarDTO getThisMonthExpense(String id) {
        Calendar today = Calendar.getInstance();

        List<Expense> monthData = expenseRepository.getMonthData(today.get(Calendar.YEAR), today.get(Calendar.MONTH) + 1, id);
        List<Expense> result = createCalendar(monthData, today);

        return new CalendarDTO(today.get(Calendar.YEAR),
                today.get(Calendar.MONTH)+1,
                today.get(Calendar.DAY_OF_WEEK),
                result);
    }

    private List<Expense> createCalendar(List<Expense> list, Calendar today){
        int year = list.get(0).getYear();
        int month = list.get(0).getMonth();
        List<Expense> result = new ArrayList<>();

        for(int i = 1; i <= today.getActualMaximum(Calendar.DAY_OF_MONTH); i++)
            result.add(new Expense(null, year, month, i, null, null));

        for (Expense expense : list)
            result.set(expense.getDay(), expense);

        return result;
    }
}
