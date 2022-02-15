package project1.dateMoneyManagement.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public String gotoLogin() {
        log.info("loginPage");
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
    private String id;
    private String password;
    private String email;
    private Date regDate;
    private String nickname;
    private BufferedImage image;
    private String boyName;
    private String girlName;

}
