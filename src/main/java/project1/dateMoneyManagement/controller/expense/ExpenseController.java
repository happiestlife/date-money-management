package project1.dateMoneyManagement.controller.expense;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project1.dateMoneyManagement.DTO.expense.DateDTO;
import project1.dateMoneyManagement.common.SessionKeys;
import project1.dateMoneyManagement.model.Expense;
import project1.dateMoneyManagement.service.expense.ExpenseService;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("/data")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public String editExpenseForm(HttpSession session,
                                  @ModelAttribute DateDTO date,
                                  Model model) {
        String id = (String) session.getAttribute(SessionKeys.LOGIN_SESSION);
        Expense detailExpense = expenseService.getDetailExpense(new DateDTO(date.getYear(), date.getMonth(), date.getDay()), id);

        model.addAttribute("detailExpense", detailExpense);

        return "expense/editExpense";
    }

    @PostMapping("/insert")
    public String editExpense(@ModelAttribute Expense expense) {
        expenseService.save(expense);

        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteExpense(@ModelAttribute DateDTO date,
                                @RequestParam String id) {
        expenseService.deleteDay(date, id);

        return "redirect:/";
    }
}
