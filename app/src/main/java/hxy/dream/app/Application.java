package hxy.dream.app;


import hxy.dream.app.util.AppVersion;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(scanBasePackages = {"hxy.dream"})
@MapperScan("hxy.dream.dao")
@ServletComponentScan("hxy.dream")
public class Application {

    public static void main(String[] args) {
        System.out.println("\n====>AppVersion"+AppVersion.getVersion());
        SpringApplication.run(Application.class, args);
    }

}
