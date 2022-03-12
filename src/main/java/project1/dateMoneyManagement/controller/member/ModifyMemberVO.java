package project1.dateMoneyManagement.controller.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.nio.file.Files;

@Setter @Getter
@AllArgsConstructor
public class ModifyMemberVO {
    @NotBlank
    @Pattern(regexp = "^.*(?=^.{8,30}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$")
    private String password;

    @NotBlank
    @Email
    @Pattern(regexp="^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$")
    private String email;

    @NotBlank
    private String nickname;

    @NotBlank
    private String boyName;

    @NotBlank
    private String girlName;

    private Files image;

}
