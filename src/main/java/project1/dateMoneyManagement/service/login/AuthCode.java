package project1.dateMoneyManagement.service.login;

import lombok.Getter;

import java.util.Date;

@Getter
public class AuthCode {
    private String code;
    private Date endDate;

    public AuthCode(String code) {
        this.code = code;

        Date date = new Date();
        long time = date.getTime();

        time += (60 * 10) * 1000;
        date.setTime(time);

        this.endDate = date;
    }
}
