package project1.dateMoneyManagement.controller.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.nio.file.Files;

@Setter @Getter
@AllArgsConstructor
public class ModifyMemberVO {

    @Size(min = 8, max = 30)
    @Pattern(regexp = "^.*(?=^.{8,30}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$")
    private String password;

    @Email
    @Size(min = 4, max = 30)
    @Pattern(regexp="^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$")
    private String email;

    @Size(min = 1, max = 15)
    private String nickname;

    @Size(min = 1, max = 5)
    private String boyName;

    @Size(min = 1, max = 5)
    private String girlName;

    private Files image;
}
