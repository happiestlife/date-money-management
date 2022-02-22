package project1.dateMoneyManagement.controller.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import project1.dateMoneyManagement.exception.login.DuplicateIdException;
import project1.dateMoneyManagement.exception.login.WrongIdOrPasswordException;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class LoginExceptionController {

    @ExceptionHandler(WrongIdOrPasswordException.class)
    public String loginFailed(WrongIdOrPasswordException e, Model model, HttpServletRequest request) {
        log.info(e.getMessage());

        String loginId = request.getParameter("loginId");

        model.addAttribute("loginId", loginId);
        model.addAttribute("logFailed", true);

        return "login/loginForm";
    }

    @ExceptionHandler(DuplicateIdException.class)
    public String duplicateIdError(DuplicateIdException e, Model model) {
        String errorMsg = e.getMessage();
        log.info(errorMsg);

        model.addAttribute("errormsg", errorMsg);

        return "login/register";
    }
}
