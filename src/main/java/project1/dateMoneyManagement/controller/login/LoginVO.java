package project1.dateMoneyManagement.controller.login;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginVO {
    @NotBlank
    private String id;
    @NotBlank
    private String password;
}
