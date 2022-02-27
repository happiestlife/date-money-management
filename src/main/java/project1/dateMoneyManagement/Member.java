package project1.dateMoneyManagement;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

@Setter
@Getter
@ToString
public class Member {

    private String id;
    private String password;
    private String email;
    private String nickname;
    private String boyName;
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
        Date now = new Date();

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println(date.format(now));
        return date.format(now);
    }
}
