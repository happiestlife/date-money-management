package project1.dateMoneyManagement.repository.expense;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import project1.dateMoneyManagement.DTO.expense.DateDTO;
import project1.dateMoneyManagement.model.Expense;
import project1.dateMoneyManagement.model.Member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MySqlExpenseRepository implements ExpenseRepository{

    private JdbcTemplate jdbcTemplate;
    private final String table = "expense";
    private final RowMapper<Expense> rowMapper;

    private static class ExpenseRowMapper implements RowMapper<Expense> {
        @Override
        public Expense mapRow(ResultSet rs, int rowNum) throws SQLException {
            Expense expense = new Expense(
                    rs.getString("id"),
                    new DateDTO(rs.getInt("year"),
                            rs.getInt("month"),
                            rs.getInt("day")),
                    rs.getLong("cost"),
                    rs.getString("memo")
            );
            return expense;
        }
    }

    public MySqlExpenseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = new ExpenseRowMapper();
    }

    @Override
    public List<Expense> readMonthExpense(int year, int month, String id) {
        String query = "SELECT * FROM %s WHERE id = '%s' AND year = %s AND month = %s";
        query = String.format(query, table, id, year, month);


        return jdbcTemplate.query(query, rowMapper);
    }

    @Override
    public Expense readDetailExpense(DateDTO date, String id) {
        String query = "SELECT * FROM %s WHERE id = '%s' AND year = %s AND month = %s AND day = %s";
        query = String.format(query, table, id, date.getYear(), date.getMonth(), date.getDay());

        List<Expense> result = jdbcTemplate.query(query, rowMapper);

        if(result.size() == 1) return result.get(0);
        else return null;
    }

    @Override
    public boolean createExpense(Expense expense) {
        DateDTO date = expense.getDate();

        String query = "INSERT INTO %s VALUES ('%s', %d, %d, %d, %d, '%s')";
        query = String.format(query, table, expense.getId(), date.getYear(), date.getMonth(), date.getDay(), expense.getCost(), expense.getMemo());

        int rs = jdbcTemplate.update(query);

        if(rs == 1) return true;
        else return false;
    }

    @Override
    public boolean updateExpense(Expense expense) {
        DateDTO date = expense.getDate();

        String query = "UPDATE %s SET cost = %s, memo = '%s' WHERE id = '%s' AND year = %s AND month = %s AND day = %s";
        query = String.format(query, table, expense.getCost(), expense.getMemo(), expense.getId(), date.getYear(), date.getMonth(), date.getDay());

        int rs = jdbcTemplate.update(query);

        if(rs == 1) return true;
        else return false;
    }
}
