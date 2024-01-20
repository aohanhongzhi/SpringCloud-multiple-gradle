package hxy.dream.common.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * @description bean初始化之前，Spring环境变量修改 https://blog.csdn.net/weixin_43827985/article/details/114368232
 * 1. 可以读取Azue KeyValue
 * 2. 可以读取本机的配置文件
 * <p>
 * 这个类生效靠：app/src/main/resources/META-INF/spring.factories
 * @date 2022/2/25
 */
//@Profile(value = {"prod", "beta"}) // 貌似这个没啥用，其他环境变量的时候也跑了程序
public class RemoteEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static final Logger log = LoggerFactory.getLogger(RemoteEnvironmentPostProcessor.class);


    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String currentProfile = environment.getProperty("spring.profiles.active");
        if (currentProfile == null || currentProfile.equalsIgnoreCase("dev")) {
            log.warn("本地调试，直接忽略读取本地配置文件信息");
        } else {
            final Properties properties = new Properties();

            // 读取配置文件，从配置文件中加载这个变量。
            String database = System.getProperty("user.home") + "/.config/eric-config/database.json";
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode jsonObject = objectMapper.readTree(new File(database));
                String databaseUsername = jsonObject.get("spring.datasource.username").textValue();
                String databaseUrl = jsonObject.get("spring.datasource.url").textValue();
                String password = jsonObject.get("spring.datasource.password").textValue();
                properties.put("spring.datasource.url", databaseUrl);
                properties.put("spring.datasource.username", databaseUsername);
                properties.put("spring.datasource.password", password);
            } catch (JsonProcessingException e) {
                log.error("{}", e.getMessage(), e);
            } catch (IOException e) {
                log.error("{}", e.getMessage(), e);
            }

            String url = environment.getProperty("azure.keyvault.url");
            if (url == null) {
                // 从远程vault这种管理工具或者redis里面获取配置信息，然后再更新到本地的配置中 https://learn.microsoft.com/zh-cn/azure/developer/java/spring-framework/configure-spring-boot-starter-java-app-with-azure-key-vault
                properties.put("password", "vault获取的值");
            }
            PropertiesPropertySource propertySource = new PropertiesPropertySource(currentProfile, properties);
//            PropertiesPropertySource propertySource = new PropertiesPropertySource("prod", properties); // 只修改指定环境变量的配置信息
            environment.getPropertySources().addFirst(propertySource);
        }
    }
}
