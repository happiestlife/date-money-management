package project1.dateMoneyManagement.model;

import lombok.Data;

@Data
public class Expense {
    private final String id;
    private final int year;
    private final int month;
    private final int day;
    private final Long cost;
    private final String memo;
}
