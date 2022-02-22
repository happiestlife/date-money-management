package project1.dateMoneyManagement.controller.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project1.dateMoneyManagement.exception.login.NoEnoughInfoException;
import project1.dateMoneyManagement.exception.login.WrongMatchException;
import project1.dateMoneyManagement.service.login.LoginService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;

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
        log.trace("Find password - insert id and email Form");

        return "login/find/pw/findpw";
    }

    @PostMapping
    public String insertData(@RequestParam String id,
                             @RequestParam String email,
                             HttpServletResponse response) {
        log.trace("find password - insert id and email");

        loginService.findPwById(id, email);

        Cookie cookie = new Cookie("id", id);
        cookie.setMaxAge(60 * 10);
        response.addCookie(cookie);

        return "login/find/pw/authwithcode";
    }

    @GetMapping("/verifycode")
    public String verifyCodeForm() {
        log.trace("Find password - verify code Form");

        return "login/find/pw/authwithcode";
    }

    @PostMapping("/verifycode")
    public String verifyCode(@RequestParam String code,
                             @CookieValue(value = "id") Cookie cookie) {
        log.trace("Find password - verify code");

        String id = cookie.getValue();
        loginService.verifyCode(id, code);

        return "login/find/pw/newpwForm";
    }

    @PostMapping("/newpw")
    public String newPassword(@CookieValue(value = "id") Cookie cookie,
                              @RequestParam String pw,
                              @RequestParam String check,
                              HttpServletResponse response) {
        log.trace("Find password - update new password");
        String id = cookie.getValue();

        loginService.updatePw(id, pw, check);


        cookie.setValue(null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/login";
    }

    @ExceptionHandler({NoSuchElementException.class, NoEnoughInfoException.class})
    public String noMatchWithIdOrEmail(RuntimeException e, Model model) {
        String errorMsg = e.getMessage();
        log.info(errorMsg);

        model.addAttribute("errormsg", errorMsg);

        return "login/find/pw/findpw";
    }

    @ExceptionHandler(WrongMatchException.class)
    public String wrongCodeError(WrongMatchException e, Model model) {
        String errorMsg = e.getMessage();
        log.info(errorMsg);

        model.addAttribute("errormsg", errorMsg);

        return "login/find/pw/newpwForm";
    }
}
