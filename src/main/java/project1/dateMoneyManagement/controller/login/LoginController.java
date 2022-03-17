package project1.dateMoneyManagement.controller.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project1.dateMoneyManagement.model.Member;
import project1.dateMoneyManagement.exception.login.WrongIdOrPasswordException;
import project1.dateMoneyManagement.service.login.LoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;
    private final String LOGIN_SESSION = "login";

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public String loginForm(Model model) {
        log.trace("loginForm");
        model.addAttribute("login", new LoginDTO());

        return "login/loginForm";
    }

    @PostMapping
    public String login(@Validated @ModelAttribute("login") LoginDTO login,
                        BindingResult error,
                        Model model,
                        HttpSession session,
                        HttpServletResponse response) throws NoSuchAlgorithmException {
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

//        MessageDigest md = MessageDigest.getInstance("SHA-512");
//        md.update(loginMember.getId().getBytes());
//        String hashKey = String.format("%064x", md.digest());

        session.setAttribute(LOGIN_SESSION, loginMember.getId());

        return "redirect:/";
    }

    // login Exception Handler
    @ExceptionHandler(WrongIdOrPasswordException.class)
    public String loginFailed(WrongIdOrPasswordException e, Model model, HttpServletRequest request) {
        String errorMsg = e.getMessage();
        log.info("message : " + errorMsg +  ", cause : " + e.getCause());

        String id = request.getParameter("id");

        model.addAttribute("id", id);
        model.addAttribute("errormsg", errorMsg);
        model.addAttribute("login", new LoginDTO());

        return "login/loginForm";
    }
}
