package project1.dateMoneyManagement.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project1.dateMoneyManagement.Member;
import project1.dateMoneyManagement.repository.member.MemberRepository;

import javax.annotation.PostConstruct;
import java.awt.image.BufferedImage;
import java.util.Date;

@Slf4j
@Controller
@RequestMapping
public class BaseController {

    private MemberRepository memberRepository;

    public BaseController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping
    public String gotoLogin(RedirectAttributes redirect) {
        log.info("goto loginPage");

        redirect.addAttribute("logFailed", false);

        return "redirect:/login";
    }

    @PostMapping
    public String login() {
        log.info("login");
        return "homepage";
    }

    @PostConstruct
    public void init() {
        memberRepository.insert(new Member("chickenman10", "1234", "chickenman10@naver.com",
                "chickenman", null, "현성", "보민"));

        memberRepository.insert(new Member("chickenman11", "4567", "aaaa@naver.com",
                "ruri", null, "현성2", "보민2"));

    }
}
