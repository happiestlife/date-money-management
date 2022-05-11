package project1.dateMoneyManagement.DTO.login;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    @NotBlank
    private String id;
    @NotBlank
    private String password;
}
