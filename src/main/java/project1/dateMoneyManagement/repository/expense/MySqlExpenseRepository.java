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
    public List<Expense> getMonthData(int year, int month, String id) {
        String query = "SELECT * FROM %s WHERE id = '%s' AND year = %s AND month = %s";
        query = String.format(query, table, id, year, month);
        System.out.println(year + query);
        return jdbcTemplate.query(query, (ResultSet rs, int rowNum) -> {
            Expense expense = new Expense(
                    rs.getString("id"),
                    rs.getInt("year"),
                    rs.getInt("month"),
                    rs.getInt("day"),
                    rs.getLong("cost"),
                    rs.getString("memo")
            );
            return expense;
        });
    }
}
