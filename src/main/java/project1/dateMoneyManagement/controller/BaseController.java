package project1.dateMoneyManagement.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project1.dateMoneyManagement.model.Member;
import project1.dateMoneyManagement.service.member.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Slf4j
@Controller
@RequestMapping
public class BaseController {

    private MemberService memberService;

    public BaseController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String homepageForm(HttpServletRequest request,
                            Model model) {
        HttpSession session = request.getSession(false);
        if(session == null) {
            log.trace("goto LoginPage");

            return "redirect:/login";
        }else {
            log.trace("goto Homepage");

            String id = (String) session.getAttribute(SessionKeys.LOGIN_SESSION);
            log.info(id);
            Member findMember = memberService.findMemberById(id);
            model.addAttribute("nickname", findMember.getNickname());

            return "index";
        }
    }

    @PostMapping
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        log.trace("logout");

        request.getSession(false).invalidate();

        return "redirect:/";
    }
}
