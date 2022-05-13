package project1.dateMoneyManagement.repository;

import org.apache.tomcat.jdbc.pool.DataSource;
public class CommonRepositoryTest {

    private static DataSource ds = null;

    private CommonRepositoryTest() {}

    public static DataSource getDatasource() {
        if(ds == null) {
            ds = new DataSource();
            ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
            ds.setUrl("jdbc:mysql://localhost:3306/dmmdb");
            ds.setUsername("root");
            ds.setPassword("@dlgustjd1234");
        }

        return ds;
    }
}
