package project1.dateMoneyManagement;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;

@Setter
@Getter
@ToString
public class Member {

    private String id;
    private String password;
    private String email;
    private String regDate;
    private String nickname;
    private BufferedImage image;
    private String boyName;
    private String girlName;

    public Member() {}

    public Member(String id, String password, String email, String nickname, String boyName, String girlName) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.image = null;
        this.boyName = boyName;
        this.girlName = girlName;
        this.regDate = dateConverter();
    }

    public Member(String id, String password, String email, String nickname, BufferedImage image, String boyName, String girlName) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.image = image;
        this.boyName = boyName;
        this.girlName = girlName;
        this.regDate = dateConverter();
    }

    private String dateConverter() {
        Date now = new Date();

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");

        System.out.println(date.format(now));
        return date.format(now);
    }
}
