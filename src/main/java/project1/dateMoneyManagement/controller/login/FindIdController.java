package project1.dateMoneyManagement.controller.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project1.dateMoneyManagement.service.login.LoginService;

import java.util.NoSuchElementException;

@Slf4j
@Controller
@RequestMapping("/find/id")
public class FindIdController {

    private final LoginService loginService;

    public FindIdController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public String findIdForm() {
        log.trace("find Id - insert email Form");

        return "login/find/id/findid";
    }

    @PostMapping
    public String findId(@RequestParam String email, Model model) {
        log.trace("find Id - insert email");

        String id = loginService.findIdByEmail(email);

        model.addAttribute("email", email);
        model.addAttribute("id", id);

        return "login/find/id/showid";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String noSuchIdError(NoSuchElementException e, Model model) {
        String errorMsg = e.getMessage();
        log.info("message : " + e.getMessage() +  ", cause : " + e.getCause());

        model.addAttribute("errormsg", errorMsg);

        return "login/find/id/findid";
    }
}
