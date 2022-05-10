package project1.dateMoneyManagement.service.expense;

import org.springframework.stereotype.Service;
import project1.dateMoneyManagement.DTO.expense.CalendarDTO;
import project1.dateMoneyManagement.DTO.expense.DateDTO;
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
        List<Expense> monthData = expenseRepository.readMonthExpense(today.get(Calendar.YEAR),
                today.get(Calendar.MONTH) + 1, id);

        List<Expense> result = createCalendar(monthData, today);

        today.set(Calendar.DAY_OF_MONTH, 1);
        int dayOfWeek = today.get(Calendar.DAY_OF_WEEK);
        System.out.println(dayOfWeek);

        return new CalendarDTO(today.get(Calendar.YEAR),
                today.get(Calendar.MONTH) + 1,
                dayOfWeek,
                result);
    }

    @Override
    public Expense getDetailExpense(DateDTO date, String id) {
        Expense expense = expenseRepository.readDetailExpense(date, id);

        return expense;
    }

    @Override
    public boolean save(Expense expense) {
        if(expenseRepository.readDetailExpense(expense.getDate(), expense.getId()) != null)
            expenseRepository.updateExpense(expense);
        else
            expenseRepository.createExpense(expense);

        return true;
    }

    private List<Expense> createCalendar(List<Expense> list, Calendar today){
        int year = list.get(0).getDate().getYear();
        int month = list.get(0).getDate().getMonth();
        List<Expense> result = new ArrayList<>();

        for(int i = 1; i <= today.getActualMaximum(Calendar.DAY_OF_MONTH); i++)
            result.add(new Expense(null, new DateDTO(year, month, i), null, null));

        for (Expense expense : list) {
            result.set(expense.getDate().getDay()-1, expense);
        }

        return result;
    }
}
