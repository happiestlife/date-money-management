package project1.dateMoneyManagement.controller.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project1.dateMoneyManagement.controller.SessionKeys;
import project1.dateMoneyManagement.model.Member;
import project1.dateMoneyManagement.service.member.MemberService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Configuration
@RequestMapping("/member")
public class MemberInquiryController {

    private MemberService memberService;

    public MemberInquiryController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String detailMemberInfoForm(HttpServletRequest request, Model model) {
        String id = (String) request.getSession().getAttribute(SessionKeys.LOGIN_SESSION);

        Member findMember = memberService.findMemberById(id);

        if(findMember == null)
            return "redirect:/";

        model.addAttribute("member", findMember);

        return "member/memberDetailForm";
    }

    @GetMapping("/edit")
    public String memberInfoEditForm(Model model) {
        model.addAttribute("member", new Member());

        return "member/modifyMemberForm";
    }

    @PostMapping("/edit")
    public String editMemberInfo(HttpServletRequest request,
                                 @Validated @ModelAttribute("member") ModifyMemberDTO member,
                                 BindingResult error) {
        if(error.hasErrors())
            return "member/modifyMemberForm";

        String id = (String) request.getSession().getAttribute(SessionKeys.LOGIN_SESSION);
        Member loginMember = memberService.findMemberById(id);
        loginMember.setPassword(member.getPassword());
        loginMember.setEmail(member.getEmail());
        loginMember.setNickname(member.getNickname());
        loginMember.setBoyName(member.getBoyName());
        loginMember.setGirlName(member.getGirlName());
        loginMember.setImage(member.getImage());

        memberService.updateMember(id, loginMember);

        return "redirect:/member";
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public String sameEmailException(DuplicateKeyException e, Model model) {
        String errorMsg = e.getMessage();

        log.info(errorMsg);

        model.addAttribute("errormsg", errorMsg);
        model.addAttribute("member", new Member());

        return "member/modifyMemberForm";
    }
}
