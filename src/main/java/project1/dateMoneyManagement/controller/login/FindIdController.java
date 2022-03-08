package project1.dateMoneyManagement.controller.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project1.dateMoneyManagement.service.login.LoginService;

import javax.validation.constraints.NotBlank;
import java.util.NoSuchElementException;

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
        log.trace("find Id - insert email Form");

        return "login/find/id/findid";
    }

    @PostMapping
    public String findId(@RequestParam @NotBlank(message = "notEnoughInfo") String email, BindingResult error, Model model) {
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
