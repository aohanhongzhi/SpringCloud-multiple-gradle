package hxy.dream.app;


import hxy.dream.common.filter.ErrorHttpBodyRecorderFilter;
import hxy.dream.common.filter.HttpBodyRecorderFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;


/**
 * @author iris
 */
@SpringBootApplication(scanBasePackages = {"hxy.dream"})
@MapperScan("hxy.dream.dao.mapper")
@ServletComponentScan("hxy.dream")
@EnableAsync
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);


    public static void main(String[] args) {
        log.info("当前CPU核心={}，{}是否为守护线程={}", Runtime.getRuntime().availableProcessors(), Thread.currentThread().getName(), Thread.currentThread().isDaemon());
        log.info("\n<============ \uD83D\uDE80  JAVA版本:{}  CPU核心数:{}  \uD83D\uDE80 ============>", System.getProperty("java.version"), Runtime.getRuntime().availableProcessors());

        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        log.trace("方便打断点");
        ErrorHttpBodyRecorderFilter bean = context.getBean(ErrorHttpBodyRecorderFilter.class);
        if (bean != null) {
            log.info("bean{}", bean);
        } else {
            log.error("注入失败HttpBodyRecorderFilter");
        }
        shutdownHook();

    }


    /**
     * 程序关闭监听函数，程序正常关闭可以邮件通知
     */
    private static void shutdownHook() {
        Thread shutdownThread = new Thread(() -> {
            //  发送邮件,监听服务关闭
            log.error("\n====>后端服务正在关闭,当前程序启动时间");
        });
        shutdownThread.setName("app-shutdown@thread");
        Runtime.getRuntime().addShutdownHook(shutdownThread);
    }

}
