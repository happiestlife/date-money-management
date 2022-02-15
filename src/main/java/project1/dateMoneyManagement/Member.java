package project1.dateMoneyManagement;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.awt.image.BufferedImage;
import java.util.Date;

@Setter
@Getter
@ToString
public class Member {

    private String id;
    private String password;
    private String email;
    private Date regDate;
    private String nickname;
    private BufferedImage image;
    private String boyName;
    private String girlName;

    public Member() { }

    public Member(String id, String password, String email, String nickname, BufferedImage image, String boyName, String girlName) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.image = image;
        this.boyName = boyName;
        this.girlName = girlName;
    }
}
