package project1.dateMoneyManagement;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

@Setter @Getter
@ToString
public class Member {

    @NotBlank
    private String id;

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

    private final String regDate;
    private Files image;

    public Member() {
        regDate = setDateConverter();
    }

    public Member(String id, String password, String email, String nickname, String boyName, String girlName) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.boyName = boyName;
        this.girlName = girlName;
        this.regDate = setDateConverter();
    }

    public Member(String id, String password, String email, String nickname, String boyName, String girlName, Files image) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.boyName = boyName;
        this.girlName = girlName;
        this.regDate = setDateConverter();
        this.image = image;
    }

    public String setDateConverter() {
        if(regDate != null || regDate == "")
            return regDate;

        Date now = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return date.format(now);
    }
}
