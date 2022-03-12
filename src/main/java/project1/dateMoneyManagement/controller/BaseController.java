package project1.dateMoneyManagement.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project1.dateMoneyManagement.model.Member;
import project1.dateMoneyManagement.repository.member.MemberRepository;
import project1.dateMoneyManagement.service.member.MemberService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Set;


@Slf4j
@Controller
@RequestMapping
public class BaseController {

    private MemberService memberService;

    public BaseController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String homepageForm(@CookieValue(value = "loginId", required = false) Cookie cookie,
                            HttpSession session,
                            Model model) {
        Set<String> loginMembers = (Set<String>) session.getAttribute("login");
        if(cookie == null || loginMembers == null || loginMembers.contains(cookie.getValue()) == false) {
            log.trace("goto LoginPage");

            return "redirect:/login";
        }else {
            log.trace("goto Homepage");

            String id = cookie.getValue();
            Member findMember = memberService.findMemberById(id);

            model.addAttribute("nickname", findMember.getNickname());

            return "homepage";
        }
    }

    @PostMapping
    public String logout(HttpSession session,
                         @CookieValue(value = "loginId") Cookie cookie,
                         HttpServletResponse response) {
        log.trace("logout");

        Member member = (Member) session.getAttribute(cookie.getValue());
        System.out.println(member);

        Set<String> loginMembers = (Set<String>) session.getAttribute("login");
        loginMembers.remove(cookie.getValue());

        cookie.setValue(null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/";
    }
}
