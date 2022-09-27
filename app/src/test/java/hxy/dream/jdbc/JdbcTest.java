package hxy.dream.jdbc;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.Assert.assertTrue;

public class JdbcTest {
    private static final Logger log = LoggerFactory.getLogger(JdbcTest.class);


    /**
     * 获取数据库连接
     *
     * @throws Exception
     */
    @Test
    public void testConnection() throws Exception {
        // 三个必须的参数！
        Connection connection = DriverManager.getConnection("jdbc:mysql://mysql.cupb.top:3306/eric?useUnicode=true&serverTimezone=GMT%2b8&characterEncoding=UTF-8", "eric", "dream,1234..");
        boolean valid = connection.isValid(5);
        log.info("Connection is valid: " + valid);
        assertTrue(valid);
    }

}
