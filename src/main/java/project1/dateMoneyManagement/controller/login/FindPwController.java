package project1.dateMoneyManagement.controller.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project1.dateMoneyManagement.exception.login.NoEnoughInfoException;
import project1.dateMoneyManagement.exception.login.WrongAuthCodeException;
import project1.dateMoneyManagement.exception.login.WrongMatchException;
import project1.dateMoneyManagement.service.login.LoginService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;

@Slf4j
@Controller
@RequestMapping("/login/find/pw")
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

        loginService.sendAuthCode(id, email);

        Cookie cookie = new Cookie("id", id);
        cookie.setMaxAge(60 * 10);
        response.addCookie(cookie);

        return "redirect:/login/find/pw/verifycode";
    }

    @GetMapping("/verifycode")
    public String verifyCodeForm(@CookieValue(value = "id", required = false) Cookie cookie) {
        log.trace("Find password redirect - verify code Form");

        if(cookie == null)
            return "redirect:/login/find/pw";

        return "login/find/pw/verifycode";
    }

    @PostMapping("/verifycode")
    public String verifyCode(@RequestParam String code,
                             @CookieValue(value = "id") Cookie cookie,
                             HttpServletResponse response) {
        log.trace("Find password - verify code");

        String id = cookie.getValue();
        loginService.verifyCode(id, code);

        Cookie c = new Cookie("auth", "true");
        c.setMaxAge(60 * 2);
        response.addCookie(c);

        return "redirect:/login/find/pw/newpw";
    }

    @GetMapping("/newpw")
    public String returnToFirstFindPasswordForm(@CookieValue(value = "auth", required = false) Cookie cookie) {
        log.trace("Find password redirect - new password Form");

        if(cookie == null)
            return "redirect:/login/find/pw";

        return "login/find/pw/newpwForm";
    }

    @PostMapping("/newpw")
    public String newPassword(@CookieValue(value = "id") Cookie idCookie,
                              @CookieValue(value = "auth") Cookie authCookie,
                              @RequestParam String pw,
                              @RequestParam String check,
                              HttpServletResponse response) {
        log.trace("Find password - update new password");
        String id = idCookie.getValue();

        loginService.updatePw(id, pw, check);


        Cookie c1 = new Cookie("id", null);
        c1.setMaxAge(0);
        response.addCookie(c1);

        Cookie c2 = new Cookie("auth", null);
        c2.setMaxAge(0);
        response.addCookie(c2);

        return "redirect:/login";
    }

    @ExceptionHandler({NoSuchElementException.class, NoEnoughInfoException.class})
    public String noMatchWithIdOrEmail(RuntimeException e, Model model) {
        String errorMsg = e.getMessage();
        log.info("message : " + e.getMessage() +  ", cause : " + e.getCause());

        model.addAttribute("errormsg", errorMsg);

        return "login/find/pw/findpw";
    }

    @ExceptionHandler(WrongMatchException.class)
    public String wrongMatchWithPasswordAndCheck(WrongMatchException e, Model model) {
        String errorMsg = e.getMessage();
        log.info("message : " + e.getMessage() +  ", cause : " + e.getCause());

        model.addAttribute("errormsg", errorMsg);

        return "login/find/pw/newpwForm";
    }

    @ExceptionHandler(WrongAuthCodeException.class)
    public String wrongAuthCode(WrongAuthCodeException e, Model model) {
        String errorMsg = e.getMessage();
        log.info("message : " + e.getMessage() +  ", cause : " + e.getCause());

        model.addAttribute("errormsg", errorMsg);

        return "login/find/pw/verifycode";
    }
}
