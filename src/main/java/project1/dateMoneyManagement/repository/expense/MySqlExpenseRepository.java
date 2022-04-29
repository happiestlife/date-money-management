package project1.dateMoneyManagement.repository.expense;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import project1.dateMoneyManagement.model.Expense;

import java.sql.ResultSet;
import java.util.List;

@Repository
public class MySqlExpenseRepository implements ExpenseRepository{

    private JdbcTemplate jdbcTemplate;
    private final String table = "expense";

    public MySqlExpenseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Expense> getMonthData(int year, int month) {
        String dateFormat = "%s-%s-01";
        String startDate = String.format(dateFormat, year, month);
        String endDate = String.format(dateFormat, year, month+1);

        String query = "SELECT money FROM %s WHERE date >= str_to_date('%s', '%Y-%m-%d') AND date < str_to_date('%s', '%Y-%m-%d');";
        query = String.format(query, table, startDate, endDate);

        return jdbcTemplate.query(query, (ResultSet rs, int rowNum) -> {
            Expense expense = new Expense(
                    rs.getString("id"),
                    rs.getString("date"),
                    rs.getLong("cost"),
                    rs.getString("memo")
            );
            return expense;
        });
    }
}
