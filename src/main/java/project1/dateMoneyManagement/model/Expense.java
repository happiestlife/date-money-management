package project1.dateMoneyManagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class Expense {

    private String id;
    private String Date;
    private Long cost;
    private String memo;
}
