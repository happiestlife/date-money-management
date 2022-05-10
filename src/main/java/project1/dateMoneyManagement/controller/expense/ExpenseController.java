package project1.dateMoneyManagement.controller.expense;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project1.dateMoneyManagement.DTO.expense.DateDTO;
import project1.dateMoneyManagement.common.SessionKeys;
import project1.dateMoneyManagement.model.Expense;
import project1.dateMoneyManagement.service.expense.ExpenseService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/data")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public String editExpenseForm(HttpSession session,
                           @RequestParam(value = "year") int year,
                           @RequestParam(value = "month")  int month,
                           @RequestParam(value = "day") int day,
                           Model model){
        String id = (String) session.getAttribute(SessionKeys.LOGIN_SESSION);
        Expense detailExpense = expenseService.getDetailExpense(new DateDTO(year, month, day), id);

        model.addAttribute("detailExpense", detailExpense);

        return "expense/editExpense";
    }

    @PostMapping
    public String editExpense(@ModelAttribute Expense expense){
        expenseService.save(expense);

        return "redirect:/";
    }
}
