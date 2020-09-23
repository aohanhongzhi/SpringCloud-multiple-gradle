package hxy.dream.app;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(scanBasePackages = {"hxy.dream"})
@MapperScan("hxy.dream.dao")
@ServletComponentScan("hxy.dream")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
