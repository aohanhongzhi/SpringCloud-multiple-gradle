package hxy.dream.app;


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
        log.info("{}是否为守护线程={}", Thread.currentThread().getName(), Thread.currentThread().isDaemon());
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        log.trace("方便打断点");
    }

}
