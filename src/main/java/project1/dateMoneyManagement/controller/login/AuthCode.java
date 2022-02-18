package project1.dateMoneyManagement.controller.login;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthCode {
    private String id;
    private String code;

    public AuthCode(String id, String code) {
        this.id = id;
        this.code = code;
    }
}
