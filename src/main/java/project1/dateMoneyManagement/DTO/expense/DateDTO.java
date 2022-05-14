package project1.dateMoneyManagement.DTO.expense;

import lombok.*;

@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DateDTO {
    private int year;
    private int month;
    private int day;
}
