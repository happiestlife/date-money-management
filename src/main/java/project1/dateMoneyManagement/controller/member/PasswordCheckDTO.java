package project1.dateMoneyManagement.controller.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordCheckDTO {

    @NotBlank
    private String password;
    @NotBlank
    private String check;
}
