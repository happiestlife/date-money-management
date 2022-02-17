package project1.dateMoneyManagement.controller.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project1.dateMoneyManagement.Member;
import project1.dateMoneyManagement.service.LoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {

    private LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public String loginForm(Model model) {
        return "login/loginForm";
    }

    @PostMapping
    public String login(@RequestParam String userId,
                        @RequestParam String pw,
                        HttpServletRequest request) {
        /**
         * 로그인 실패 시 LoginExceptionController를 통해서 다시 로그인 창으로 이동할 때,
         * id 입력 창을 유지 시켜주기 위한 userId를 세션에 입력
         */
        HttpSession session = request.getSession();
        session.setAttribute("userId", userId);

        Member loginMember = loginService.login(userId, pw);
        // 로그인 성공시 아래로 코드 진행, 익셉션(WrongIdOrPassword) 발생 시 LoginExceptionController에서 에러 처리 메서드 실행

        session.removeAttribute("userId");
        session.setAttribute("member", loginMember);

        return "redirect:/";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "login/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Member member) {
        log.info("register member\n");
//        log.info(String.valueOf(member));

        loginService.register(member);

        log.info("register success");

        return "redirect:/login";
    }

    @GetMapping("/findid")
    public String findIdForm() {
        return "login/find/id/findid";
    }

    @PostMapping("/findid")
    public String findId(@RequestParam String email, Model model) {
        String id = loginService.findIdWithEmail(email);

        model.addAttribute("email", email);
        model.addAttribute("id", id);

        log.info(email + ", " + id);

        return "login/find/id/showid";
    }

    @GetMapping("/findpw")
    public String findPwForm() {
        return "login/find/pw/findpw";
    }
}
