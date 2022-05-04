package project1.dateMoneyManagement.VO.expense;

import lombok.Data;

@Data
public class DayInfo {
    private final int day;
    private final int cost;
    private final String comment;
}
