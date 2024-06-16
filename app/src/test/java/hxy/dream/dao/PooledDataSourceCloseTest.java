package hxy.dream.dao;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.junit.jupiter.api.Test;


import java.sql.Connection;

/**
 * @author HOX4SGH
 * @description
 * @date 2024/6/16
 */
public class PooledDataSourceCloseTest {

    @Test
    public void testPooledDataSourceClose() throws Exception {
        PooledDataSource pds = new PooledDataSource();
        pds.setDriver("com.mysql.cj.jdbc.Driver");
        pds.setUrl("jdbc:mysql://.217.230.42:3306/test");
        pds.setUsername("test");
        pds.setPassword("Newp0ss,2024!");

        while (true) {
            Connection connection = pds.getConnection();
            System.out.println(connection);
            Thread.sleep(1000);
            connection.close();
        }
    }

}
