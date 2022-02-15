package project1.dateMoneyManagement.controller.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project1.dateMoneyManagement.Member;
import project1.dateMoneyManagement.repository.member.MemberRepository;

@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {

    private MemberRepository memberRepository;

    public LoginController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping
    public String loginForm() {
        log.info("logForm");
        return "login/loginForm";
    }

    @PostMapping
    public String login(@RequestParam String userId,
                        @RequestParam String pw) {

        Member findMember = memberRepository.findById(userId);
        log.info("process login!\n" + String.valueOf(findMember));
        if (findMember != null) return "homepage";
        else return "login/loginForm";
    }

    @GetMapping("/findid")
    public String findID() {
        return "login/find/id/findId";
    }

    @GetMapping("/findpw")
    public String findPw() {
        return "login/find/pw/findPw";
    }
}
