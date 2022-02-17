package project1.dateMoneyManagement.controller.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import project1.dateMoneyManagement.exception.login.DuplicateIdException;
import project1.dateMoneyManagement.exception.login.WrongIdOrPassword;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice
public class LoginExceptionController {

    @ExceptionHandler(WrongIdOrPassword.class)
    public String loginFailed(WrongIdOrPassword e, Model model, HttpServletRequest request) {
        log.info(e.getMessage());

        HttpSession session = request.getSession();
        String userId = (String)session.getAttribute("userId");

        model.addAttribute("userId", userId);
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

    @ExceptionHandler(NoSuchElementException.class)
    public String noSuchIdError(NoSuchElementException e, Model model) {
        String errorMsg = e.getMessage();
        log.info(errorMsg);

        model.addAttribute("errormsg", errorMsg);

        return "login/find/id/findid";
    }
}
