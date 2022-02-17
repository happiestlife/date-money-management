package project1.dateMoneyManagement.repository.member;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FindLoginInfoParam {
    private String id;
    private String password;

    public FindLoginInfoParam(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
