package project1.dateMoneyManagement.controller.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project1.dateMoneyManagement.controller.SessionKeys;
import project1.dateMoneyManagement.exception.login.WrongMatchException;
import project1.dateMoneyManagement.service.member.MemberService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Set;

@Slf4j
@Configuration
@RequestMapping("/member/delete")
public class DeleteMemberController {

    private MemberService memberService;

    public DeleteMemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String leaveMemberForm(Model model) {
        model.addAttribute("passwordCheck", new PasswordCheckDTO());

        return "member/memberDeleteForm";
    }

    @PostMapping
    public String leaveMember(HttpServletRequest request,
                              @Validated @ModelAttribute("passwordCheck") PasswordCheckDTO passwordCheck,
                              BindingResult error,
                              HttpSession session,
                              HttpServletResponse response) {
        if(error.hasErrors())
            return "member/memberDeleteForm";

        String id = (String) request.getSession().getAttribute(SessionKeys.LOGIN_SESSION);
        memberService.deleteMember(id, passwordCheck);

        Set<String> loginMembers = (Set<String>) session.getAttribute("login");
        loginMembers.remove(id);
        session.setAttribute("login", loginMembers);

        Cookie loginCookie = new Cookie("loginId", null);
        loginCookie.setMaxAge(0);
        response.addCookie(loginCookie);

        return "redirect:/";
    }

    @ExceptionHandler(WrongMatchException.class)
    public String wrongPasswordOrNotMatch(WrongMatchException e, Model model) {
        String errorMsg = e.getMessage();

        log.info(errorMsg);

        model.addAttribute("errormsg", errorMsg);
        model.addAttribute("passwordCheck", new PasswordCheckDTO());

        return "member/memberDeleteForm";
    }

}
