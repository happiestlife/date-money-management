package project1.dateMoneyManagement.controller.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project1.dateMoneyManagement.model.Member;
import project1.dateMoneyManagement.service.login.LoginService;

@Slf4j
@Controller
@RequestMapping("/login/register")
public class RegisterController {

    private final LoginService loginService;

    public RegisterController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public String registerForm(Model model) {
        log.trace("registerForm");
        model.addAttribute("member", new Member());

        return "login/register";
    }

    @PostMapping
    public String register(@Validated @ModelAttribute("member") Member member, BindingResult error) {
        log.trace("register");
        if(error.hasErrors())
            return "login/register";

        loginService.register(member);
        member.setDateConverter();

        return "redirect:/login";
    }

    // register Exception Handler
    @ExceptionHandler(DuplicateKeyException.class)
    public String duplicateIdError(DuplicateKeyException e, Model model) {
        String errorMsg = e.getMessage();
        log.info("message : " + e.getMessage() +  ", cause : " + e.getCause());

        model.addAttribute("errormsg", errorMsg);
        model.addAttribute("member", new Member());

        return "login/register";
    }
}
