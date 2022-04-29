package project1.dateMoneyManagement.DTO.login;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FindLoginInfoDTO {
    private String id;
    private String password;

    public FindLoginInfoDTO(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
