package project1.dateMoneyManagement.model;

import lombok.Data;
import org.springframework.lang.Nullable;
import project1.dateMoneyManagement.DTO.expense.DateDTO;

@Data
public class Expense {
    @Nullable
    private final String id;
    private final DateDTO date;
    private final Long cost;
    private final String memo;
}
