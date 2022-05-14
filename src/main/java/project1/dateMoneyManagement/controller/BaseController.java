package project1.dateMoneyManagement.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project1.dateMoneyManagement.DTO.expense.CalendarDTO;
import project1.dateMoneyManagement.common.SessionKeys;
import project1.dateMoneyManagement.model.Member;
import project1.dateMoneyManagement.service.expense.ExpenseService;
import project1.dateMoneyManagement.service.member.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Slf4j
@Controller
@RequestMapping
public class BaseController {

    private MemberService memberService;
    private ExpenseService expenseService;

    public BaseController(MemberService memberService, ExpenseService expenseService) {
        this.memberService = memberService;
        this.expenseService = expenseService;
    }

    @GetMapping
    public String homepageForm(HttpSession session,
                            Model model) {
        log.trace("goto Homepage");

        String id = (String) session.getAttribute(SessionKeys.LOGIN_SESSION);
        Member findMember = memberService.findMemberById(id);
        CalendarDTO calendarDTO = expenseService.getThisMonthExpense(id);

        model.addAttribute("id", id);
        model.addAttribute("nickname", findMember.getNickname());
        model.addAttribute("calendarDTO", calendarDTO);

        return "index";
    }

    @PostMapping
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        log.trace("logout");

        request.getSession(false).invalidate();

        return "redirect:/";
    }

}
