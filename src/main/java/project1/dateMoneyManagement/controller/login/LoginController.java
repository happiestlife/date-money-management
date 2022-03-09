package project1.dateMoneyManagement.controller.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project1.dateMoneyManagement.Member;
import project1.dateMoneyManagement.exception.login.DuplicateIdException;
import project1.dateMoneyManagement.exception.login.NoEnoughInfoException;
import project1.dateMoneyManagement.exception.login.WrongIdOrPasswordException;
import project1.dateMoneyManagement.service.login.LoginService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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
    public String loginForm(Model model) {
        log.trace("loginForm");
        model.addAttribute("login", new LoginVO());

        return "login/loginForm";
    }

    @PostMapping
    public String login(@Validated @ModelAttribute("login") LoginVO login,
                        BindingResult error,
                        Model model,
                        HttpSession session,
                        HttpServletResponse response) {
        if (error.hasErrors()) {
            if(login != null && login.getId() != null)
                model.addAttribute("id", login.getId());

            return "login/loginForm";
        }

        log.trace("login");
        String id = login.getId();
        String pw = login.getPassword();

        Member loginMember = loginService.login(id, pw);
        // 로그인 성공시 아래로 코드 진행, 익셉션(WrongIdOrPassword) 발생 시 LoginExceptionController에서 에러 처리 메서드 실행

        session.setAttribute(id, loginMember);

        Cookie cookie = new Cookie("loginId", id);
        cookie.setMaxAge(60 * 60 * 24);
        response.addCookie(cookie);

        return "redirect:/";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        log.trace("registerForm");
        model.addAttribute("member", new Member());

        return "login/register";
    }

    @PostMapping("/register")
    public String register(@Validated @ModelAttribute("member") Member member, BindingResult error) {
        log.trace("register");
        if(error.hasErrors())
            return "login/register";

        loginService.register(member);
        member.setDateConverter();

        return "redirect:/login";
    }

    // login Exception Handler
    @ExceptionHandler(WrongIdOrPasswordException.class)
    public String loginFailed(WrongIdOrPasswordException e, Model model, HttpServletRequest request) {
        String errorMsg = e.getMessage();
        log.info("message : " + errorMsg +  ", cause : " + e.getCause());

        String id = request.getParameter("id");

        model.addAttribute("id", id);
        model.addAttribute("errormsg", errorMsg);
        model.addAttribute("login", new LoginVO());

        return "login/loginForm";
    }

    // register Exception Handler
    @ExceptionHandler(DuplicateIdException.class)
    public String duplicateIdError(DuplicateIdException e, Model model) {
        String errorMsg = e.getMessage();
        log.info("message : " + e.getMessage() +  ", cause : " + e.getCause());

        model.addAttribute("errormsg", errorMsg);
        model.addAttribute("member", new Member());

        return "login/register";
    }
}
