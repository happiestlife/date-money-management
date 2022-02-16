package project1.dateMoneyManagement.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project1.dateMoneyManagement.Member;
import project1.dateMoneyManagement.repository.member.MemberRepository;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
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
    public String gotoLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session.getAttribute("member") == null)
            return "redirect:/login";
        else
            return "homepage";
    }

    @PostConstruct
    public void init() {
        memberRepository.insert(new Member("chickenman10", "1234", "chickenman10@naver.com",
                "chickenman", null, "현성", "보민"));

        memberRepository.insert(new Member("ruri", "0820", "aaaa@naver.com",
                "ruri", null, "현성2", "보민2"));
    }
}
