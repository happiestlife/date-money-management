package project1.dateMoneyManagement.DTO.expense;

import lombok.Data;
import project1.dateMoneyManagement.model.Expense;

import java.util.List;

@Data
public class CalendarDTO {
    private final int year;
    private final int month;
    private final int startDay;
    private final List<Expense> expenses;
}
