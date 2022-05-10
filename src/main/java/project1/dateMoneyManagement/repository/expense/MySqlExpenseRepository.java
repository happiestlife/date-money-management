package project1.dateMoneyManagement.repository.expense;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import project1.dateMoneyManagement.DTO.expense.DateDTO;
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
    public List<Expense> readMonthExpense(int year, int month, String id) {
        String query = "SELECT * FROM %s WHERE id = '%s' AND year = %s AND month = %s";
        query = String.format(query, table, id, year, month);

        return jdbcTemplate.query(query, (ResultSet rs, int rowNum) -> {
            Expense expense = new Expense(
                    rs.getString("id"),
                    new DateDTO(rs.getInt("year"),
                            rs.getInt("month"),
                            rs.getInt("day")),
                    rs.getLong("cost"),
                    rs.getString("memo")
            );
            return expense;
        });
    }

    @Override
    public Expense readDetailExpense(DateDTO date, String id) {
        String query = "SELECT * FROM %s WHERE id = '%s' AND year = %s AND month = %s AND day = %s";
        query = String.format(query, table, id, date.getYear(), date.getMonth(), date.getDay());

        return jdbcTemplate.queryForObject(query, (ResultSet rs, int rowNum) -> {
            Expense expense =  new Expense(
                    rs.getString("id"),
                    new DateDTO(rs.getInt("year"),
                            rs.getInt("month"),
                            rs.getInt("day")),
                    rs.getLong("cost"),
                    rs.getString("memo")
            );
            return expense;
        });
    }

    @Override
    public boolean createExpense(Expense expense) {
        DateDTO date = expense.getDate();

        String query = "INSERT INTO %s VALUES ('%s', %d, %d, %d, %d, '%s')";
        query = String.format(query, table, expense.getId(), date.getYear(), date.getMonth(), date.getDay());

        Expense result = jdbcTemplate.queryForObject(query, (ResultSet rs, int rowNum) -> {
            Expense ex = new Expense(
                    rs.getString("id"),
                    new DateDTO(rs.getInt("year"),
                            rs.getInt("month"),
                            rs.getInt("day")),
                    rs.getLong("cost"),
                    rs.getString("memo")
            );
            return ex;
        });

        if(result != null) return true;
        else return false;
    }

    @Override
    public boolean updateExpense(Expense expense) {
        DateDTO date = expense.getDate();

        String query = "UPDATE %s SET cost = %s, memo = '%s' WHERE id = '%s' AND year = %s AND month = %s AND day = %s";
        query = String.format(query, table, expense.getCost(), expense.getMemo(), expense.getId(), date.getYear(), date.getMonth(), date.getDay());

        Expense result = jdbcTemplate.queryForObject(query, (ResultSet rs, int rowNum) -> {
            Expense ex = new Expense(
                    rs.getString("id"),
                    new DateDTO(rs.getInt("year"),
                            rs.getInt("month"),
                            rs.getInt("day")),
                    rs.getLong("cost"),
                    rs.getString("memo")
            );
            return ex;
        });

        if(result != null) return true;
        else return false;
    }
}
