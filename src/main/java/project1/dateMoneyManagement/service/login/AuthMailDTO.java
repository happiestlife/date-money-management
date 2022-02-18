package project1.dateMoneyManagement.service.login;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class AuthMailDTO {
    private String addr;
    private String title;
    private String msg;
    private MultipartFile file;

    public AuthMailDTO(String addr, String title, String msg, MultipartFile file) {
        this.addr = addr;
        this.title = title;
        this.msg = msg;
        this.file = file;
    }
}
