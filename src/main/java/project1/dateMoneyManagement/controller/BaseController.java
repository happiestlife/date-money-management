package project1.dateMoneyManagement.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project1.dateMoneyManagement.Member;
import project1.dateMoneyManagement.repository.member.MemberRepository;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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
        log.info("homepage");

        if(cookie == null || session.getAttribute(cookie.getValue()) == null)
            return "redirect:/login";
        else {
            Member loginMember = (Member) session.getAttribute(cookie.getValue());
            model.addAttribute("member", loginMember);

            return "homepage";
        }
    }

    @PostMapping
    public String logout(HttpSession session,
                         @CookieValue(value = "loginId") Cookie cookie,
                         HttpServletResponse response) {
        log.info("logout");

        session.removeAttribute(cookie.getValue());

        cookie.setValue(null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/";
    }

    @PostConstruct
    public void init() {
        memberRepository.insert(new Member("chickenman10", "1234", "chickenman10@naver.com",
                "chickenman", null, "이", "최"));

        memberRepository.insert(new Member("ruri", "0820", "aaaa@naver.com",
                "ruri", null, "이", "최"));
    }
}
