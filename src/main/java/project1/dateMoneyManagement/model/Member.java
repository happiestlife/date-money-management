package project1.dateMoneyManagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

@Setter @Getter
@ToString
@AllArgsConstructor
public class Member {

    @Size(min = 1, max = 30)
    private String id;

    @Size(min = 8, max = 30)
//    @Pattern(regexp = "^.*(?=^.{8,30}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$")
    private String password;

    @Size(min = 4, max = 30)
//    @Pattern(regexp="^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$")
    private String email;

    @Size(min = 1, max = 15)
    private String nickname;

    @Size(min = 1, max = 5)
    private String boyName;

    @Size(min = 1, max = 5)
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
        this.image = null;
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
        if(regDate != null)
            return regDate;

        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return dateFormat.format(now);
    }
}
