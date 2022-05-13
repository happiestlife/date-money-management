package project1.dateMoneyManagement.repository.expense;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import project1.dateMoneyManagement.DTO.expense.DateDTO;
import project1.dateMoneyManagement.model.Expense;
import project1.dateMoneyManagement.repository.CommonRepositoryTest;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

class MySQLExpenseRepositoryTest {

    private static final String ID = "chickenman10";
    private static final int YEAR = 2022;
    private static final int MONTH = 4;
    private static final int[] DAYS = {1, 2, 3, 4};
    private static final long[] COSTS = {100, 200, 300, 400};
    private static final String[] MEMOS = {"test1", "test2", "test3", "test4"};

    private static Expense expense1 = new Expense(ID, new DateDTO(YEAR, MONTH, DAYS[0]), COSTS[0], MEMOS[0]);
    private static Expense expense2 = new Expense(ID, new DateDTO(YEAR, MONTH, DAYS[1]), COSTS[1], MEMOS[1]);
    private static Expense expense3 = new Expense(ID, new DateDTO(YEAR, MONTH, DAYS[2]), COSTS[2], MEMOS[2]);
    private static ArrayList<Expense> expenses = new ArrayList<>();

    private static MySQLExpenseRepository repository;

    @BeforeAll
    static public void init() {
        expenses.add(expense1);
        expenses.add(expense2);
        expenses.add(expense3);

        DataSource ds = CommonRepositoryTest.getDatasource();

        repository = new MySQLExpenseRepository(new JdbcTemplate(ds));
    }

    @BeforeEach
    public void inputData(){
        repository.deleteExpense(YEAR, MONTH, ID);
        for (Expense expense : expenses) {
            repository.createExpense(expense);
        }
    }

    private boolean compareExpense(Expense expense1, Expense expense2) {
        assertThat(expense1.getId()).isEqualTo(expense2.getId());
        assertThat(expense1.getDate().getYear()).isEqualTo(expense2.getDate().getYear());
        assertThat(expense1.getDate().getMonth()).isEqualTo(expense2.getDate().getMonth());
        assertThat(expense1.getDate().getDay()).isEqualTo(expense2.getDate().getDay());
        assertThat(expense1.getCost()).isEqualTo(expense2.getCost());
        assertThat(expense1.getMemo()).isEqualTo(expense2.getMemo());

        return true;
    }

    @Test
    public void readMonthExpense_Success() {
        List<Expense> expenses = repository.readMonthExpense(YEAR, MONTH, ID);

        int i = 0;
        for (Expense expense : expenses)
            compareExpense(expense, expenses.get(i++));

    }

    @Test
    public void readMonthExpense_FailWithWrongID() {
        Assertions.assertThrows(NoSuchElementException.class, () -> repository.readMonthExpense(YEAR, MONTH, "wrongId"));
    }

    @Test
    public void readMonthExpense_FailWithNoData() {
        List<Expense> result = repository.readMonthExpense(YEAR, MONTH + 100, ID);

        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    public void readDetailExpense_Success() {
        Expense result = repository.readDetailExpense(new DateDTO(YEAR, MONTH, DAYS[0]), ID);

        compareExpense(result, expense1);
    }

    @Test
    public void readDetailExpense_FailWithWrongID() {
        Assertions.assertThrows(NoSuchElementException.class, () -> repository.readDetailExpense(new DateDTO(YEAR, MONTH, DAYS[0]), "wrongId"));
    }

    @Test
    public void readDetailExpense_FailWithNoData() {
        Expense result = repository.readDetailExpense(new DateDTO(YEAR, MONTH + 100, DAYS[0]), ID);

        assertThat(result).isEqualTo(null);
    }

    @Test
    public void createExpense_Success() {
        Expense expense4 = new Expense(ID, new DateDTO(YEAR, MONTH, DAYS[3]), COSTS[3], MEMOS[3]);
        repository.createExpense(expense4);

        List<Expense> result = repository.readMonthExpense(YEAR, MONTH, ID);
        assertThat(result.size()).isEqualTo(4);

        compareExpense(result.get(3), expense4);
    }

    @Test
    public void createExpense_FailWithNoMatchedId() {
        Expense wrongExpense = new Expense("wrongId", new DateDTO(YEAR, MONTH, DAYS[3]), COSTS[3], MEMOS[3]);

        Assertions.assertThrows(NoSuchElementException.class, () -> repository.createExpense(wrongExpense));
    }

    @Test
    public void updateExpense_Success() {
        Expense updateExpense = new Expense(ID, new DateDTO(YEAR, MONTH, DAYS[0]), Long.valueOf(1000), "update test");

        repository.updateExpense(updateExpense);

        List<Expense> result = repository.readMonthExpense(YEAR, MONTH, ID);
        assertThat(result.size()).isEqualTo(3);

        compareExpense(result.get(0), updateExpense);
    }

    @Test
    public void updateExpense_FailWithNoMatchedId() {
        Expense wrongExpense = new Expense("wrongId", new DateDTO(YEAR, MONTH, DAYS[3]), COSTS[3], MEMOS[3]);

        Assertions.assertThrows(NoSuchElementException.class, () -> repository.updateExpense(wrongExpense));
    }

    @Test
    public void deleteExpense_SuccessWithMonth() {
        int affectedData1 = repository.deleteExpense(YEAR, MONTH, ID);
        assertThat(affectedData1).isEqualTo(3);

        int affectedData2 = repository.deleteExpense(YEAR, MONTH + 100, ID);
        assertThat(affectedData2).isEqualTo(0);
    }

    @Test
    public void deleteExpense_SuccessWithDay() {
        int affectedData1 = repository.deleteExpense(new DateDTO(YEAR, MONTH, DAYS[0]), ID);
        assertThat(affectedData1).isEqualTo(1);

        Expense result = repository.readDetailExpense(new DateDTO(YEAR, MONTH, DAYS[0]), ID);
        assertThat(result).isEqualTo(null);

        int affectedData2 = repository.deleteExpense(YEAR, MONTH + 100, ID);
        assertThat(affectedData2).isEqualTo(0);
    }

    @Test
    public void deleteExpense_FailWithNoMatchId_Month() {
        Assertions.assertThrows(NoSuchElementException.class, () -> repository.deleteExpense(YEAR, MONTH, "wrongId"));
    }

    @Test
    public void deleteExpense_FailWithNoMatchId_Day() {
        Assertions.assertThrows(NoSuchElementException.class, () -> repository.deleteExpense(new DateDTO(YEAR, MONTH, DAYS[0]), "wrongId"));
    }
}