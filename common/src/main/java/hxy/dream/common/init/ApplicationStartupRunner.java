package hxy.dream.common.init;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @link https://blog.csdn.net/m0_37456570/article/details/83751401
 */
@Slf4j
//@Component
public class ApplicationStartupRunner implements CommandLineRunner {

    @Value("${spring.datasource.driver-class-name}")
    private String driver;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String userName;
    @Value("${spring.datasource.password}")
    private String password;

    @Override
    public void run(String... args) throws Exception {
        log.info("ApplicationStartupRunner run method Started !!");
        // 执行sql脚本语句
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, userName, password);
            // 设置不自动提交
            connection.setAutoCommit(false);
            ScriptRunner runner = new ScriptRunner(connection);
            // 设置不自动提交
            runner.setAutoCommit(false);
            /*
             * setStopOnError参数作用：遇见错误是否停止；
             * （1）false，遇见错误不会停止，会继续执行，会打印异常信息，并不会抛出异常，当前方法无法捕捉异常无法进行回滚操作，
             * 无法保证在一个事务内执行； （2）true，遇见错误会停止执行，打印并抛出异常，捕捉异常，并进行回滚，保证在一个事务内执行；
             */
            runner.setStopOnError(true);
            /*
             * 按照那种方式执行 方式一：true则获取整个脚本并执行； 方式二：false则按照自定义的分隔符每行执行；
             */
            runner.setSendFullScript(false);

            // 设置是否输出日志，null不输出日志，不设置自动将日志输出到控制台
            runner.setLogWriter(null);
            // 如果又多个sql文件，可以写多个runner.runScript(xxx),
            Resources.setCharset(Charset.forName("UTF8"));

            ScriptRunner scriptRunner = new ScriptRunner(connection);
            scriptRunner.runScript(Resources.getResourceAsReader("table.sql"));
            connection.commit();
        } catch (Exception e) {
            log.error("{}", e);
        } finally {
            if (connection != null) {
                connection.close();// 连接关闭
            }
        }
    }
}