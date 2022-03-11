package project1.dateMoneyManagement.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project1.dateMoneyManagement.model.Member;
import project1.dateMoneyManagement.repository.member.MemberRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Slf4j
@Controller
@RequestMapping
public class BaseController {

    private MemberRepository memberRepository;

    public BaseController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping
    public String homepageForm(@CookieValue(value = "loginId", required = false) Cookie cookie,
                            HttpSession session,
                            Model model) {
        if(cookie == null || session.getAttribute(cookie.getValue()) == null) {
            log.trace("goto LoginPage");

            return "redirect:/login";
        }else {
            log.trace("goto Homepage");

            Member loginMember = (Member) session.getAttribute(cookie.getValue());
            model.addAttribute("member", loginMember);

            return "homepage";
        }
    }

    @PostMapping
    public String logout(HttpSession session,
                         @CookieValue(value = "loginId") Cookie cookie,
                         HttpServletResponse response) {
        log.trace("logout");

        session.removeAttribute(cookie.getValue());

        cookie.setValue(null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/";
    }
}
