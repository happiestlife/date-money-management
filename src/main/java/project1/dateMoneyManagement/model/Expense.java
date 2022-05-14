package project1.dateMoneyManagement.model;

import lombok.*;
import project1.dateMoneyManagement.DTO.expense.DateDTO;


@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Expense {
    private String id;
    private DateDTO date;
    private Long cost;
    private String memo;
}
