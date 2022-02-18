package project1.dateMoneyManagement.controller.login;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project1.dateMoneyManagement.service.login.LoginService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("/login/findpw")
public class FindPwController {

    private final LoginService loginService;

    public FindPwController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public String findPwForm() {
        return "login/find/pw/findpw";
    }

    @PostMapping
    public String insertData(@RequestParam String id,
                             @RequestParam String email,
                             HttpServletResponse response) {
        loginService.findPwWithIdAndEmail(id, email);

        Cookie cookie = new Cookie("id", id);
        cookie.setMaxAge(60*10);
        response.addCookie(cookie);

        return "login/find/pw/authwithcode";
    }

    @GetMapping("/verifycode")
    public String verifyCodeForm(){
        return "login/find/pw/authwithcode";
    }

    @PostMapping("/verifycode")
    public String verifyCode(@RequestParam String code,
                                 @CookieValue(value = "id", required = false) Cookie cookie) {
        String id = cookie.getValue();
        loginService.verifyCode(id, code);

        return "login/find/pw/newpwForm";
    }

}
