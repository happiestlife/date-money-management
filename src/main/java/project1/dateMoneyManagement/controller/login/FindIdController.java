package project1.dateMoneyManagement.controller.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project1.dateMoneyManagement.service.login.LoginService;

@Slf4j
@Controller
@RequestMapping("/login/findid")
public class FindIdController {

    private final LoginService loginService;

    public FindIdController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public String findIdForm() {
        return "login/find/id/findid";
    }

    @PostMapping
    public String findId(@RequestParam String email, Model model) {
        String id = loginService.findIdWithEmail(email);

        model.addAttribute("email", email);
        model.addAttribute("id", id);

        log.info(email + ", " + id);

        return "login/find/id/showid";
    }
}
