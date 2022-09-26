package hxy.dream.jdbc;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcTest {

    @Test
    public void testConnection() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:mysql://mysql.cupb.top:3306/eric?useUnicode=true&serverTimezone=GMT%2b8&characterEncoding=UTF-8", "eric", "dream,1234..");
    }

}
