package project1.dateMoneyManagement.controller.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project1.dateMoneyManagement.Member;
import project1.dateMoneyManagement.exception.login.NoEnoughInfoException;
import project1.dateMoneyManagement.service.login.LoginService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public String loginForm() {
        log.trace("loginForm");

        return "login/loginForm";
    }

    @PostMapping
    public String login(@RequestParam String loginId,
                        @RequestParam String pw,
                        HttpSession session,
                        HttpServletResponse response) {
        log.trace("login");

        Member loginMember = loginService.login(loginId, pw);
        // 로그인 성공시 아래로 코드 진행, 익셉션(WrongIdOrPassword) 발생 시 LoginExceptionController에서 에러 처리 메서드 실행

        session.setAttribute(loginId, loginMember);

        Cookie cookie = new Cookie("loginId", loginId);
        cookie.setMaxAge(60 * 60 * 24);
        response.addCookie(cookie);

        return "redirect:/";
    }

    @GetMapping("/register")
    public String registerForm() {
        log.trace("registerForm");

        return "login/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Member member) {
        log.trace("register");

        loginService.register(member);

        return "redirect:/login";
    }

    @ExceptionHandler(NoEnoughInfoException.class)
    public String registerError(NoEnoughInfoException e, Model model) {
        String errorMsg = e.getMessage();

        log.info(errorMsg);

        model.addAttribute("errormsg", errorMsg);

        return "login/register";
    }
}
