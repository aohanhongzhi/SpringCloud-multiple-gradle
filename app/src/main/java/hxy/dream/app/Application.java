package hxy.dream.app;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication(scanBasePackages = {"hxy.dream"})
@MapperScan("hxy.dream.dao.mapper")
@ServletComponentScan("hxy.dream")
@EnableAsync
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
