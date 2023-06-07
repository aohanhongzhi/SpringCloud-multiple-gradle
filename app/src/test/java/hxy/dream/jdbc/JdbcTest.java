package hxy.dream.jdbc;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcTest {

    private static final Logger log = LoggerFactory.getLogger(JdbcTest.class);


    @Test
    public void testJdbcInsert() throws ClassNotFoundException, SQLException {
        // 1. 注册驱动
        // 使用java.sql.DriverManager类的静态方法registerDriver(Driver driver)
        // Driver是一个接口,参数传递:MySQL驱动程序的实现类
        // DriverManager.registerDriver(new Driver());
        // 查看驱动类源码,注册两次驱动,浪费资源
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 2. 获得连接
        // uri:数据库地址 jdbc:mysql://连接主机ip:端口号//数据库名字=
        String url = "jdbc:mysql://mysql.cupb.top:3306/eric";
        // static Connection getConnection(String url, String user, String password)
        // 返回值是java.sql.Connection接口的实现类,在MySQL驱动程序中
        Connection conn = DriverManager.getConnection(url, "eric", "dream,1234..");
        System.out.println(conn);// com.mysql.jdbc.JDBC4Connection@10d1f30
        // 3. 获得语句执行平台,通过数据库连接对象,获取到SQL语句的执行者对象
        //conn对象,调用方法 Statement createStatement() 获取Statement对象,将SQL语句发送到数据库
        //返回的是Statement接口的实现类对象,在MySQL驱动程序中
        Statement stat = conn.createStatement();
        System.out.println(stat);//com.mysql.jdbc.StatementImpl@137bc9
        // 4. 执行sql语句
        //通过执行者对象调用方法执行SQL语句,获取结果
        //int executeUpdate(String sql)  执行数据库中的SQL语句,仅限于insert,update,delete
        //返回值int,操作成功数据库的行数
        int row = stat.executeUpdate("INSERT INTO user_model(name,age,address) VALUES('汽车用品',50,'疯狂涨价')");
        log.info("affect row {}", row);
        // 5. 释放资源
        stat.close();
        conn.close();
//        原文链接：https://blog.csdn.net/qq_35537301/article/details/81154182
    }


    @Test
    public void testJdbcSelect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 2. 获得连接
        // uri:数据库地址 jdbc:mysql://连接主机ip:端口号//数据库名字
        String url = "jdbc:mysql://mysql.cupb.top:3306/eric";
        // static Connection getConnection(String url, String user, String password)
        // 返回值是java.sql.Connection接口的实现类,在MySQL驱动程序中
        Connection conn = DriverManager.getConnection(url, "eric", "dream,1234..");

        String id = "1";
        String name = "汽车用品";

        // 拼写SQL语句
        String sql = "select * from user_model where id = ? or name = ? ";
        // 3.获取执行SQL语句
        //Connection接口
        PreparedStatement pst = conn.prepareStatement(sql);
        //调用pst对象的setXXX方法设置问号占位符的参数 下面参数的设置具体是由 mysql驱动实现的。（面向接口编程，只管申明，具体执行由接口实现）
        pst.setObject(1, id);
        pst.setObject(2, name);
        System.out.println(sql);
        // 4.调用执行者对象方法,执行SQL语句获取结果集
        ResultSet rs = pst.executeQuery();
        // 5.处理结果集
        while (rs.next()) {
            log.info(rs.getString("name") + "\t" + rs.getString("address"));
        }
        // 6.关闭资源
        rs.close();
        pst.close();
        conn.close();
//        原文链接：https://blog.csdn.net/qq_35537301/article/details/81154182
    }

}
