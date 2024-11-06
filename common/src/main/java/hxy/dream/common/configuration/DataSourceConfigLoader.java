package hxy.dream.common.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * B站Up主 【极海channel】 的方案 https://b23.tv/OTa283B
 * 这个不需要 spring.factories 去配置生效
 */
@Component
public class DataSourceConfigLoader implements BeanPostProcessor, EnvironmentAware {

    private static final Logger log = LoggerFactory.getLogger(DataSourceConfigLoader.class);

    private ConfigurableEnvironment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = (ConfigurableEnvironment) environment;
    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        if (bean instanceof MybatisPlusAutoConfiguration) { //在mybatis初始化前搞定配置信息
            String currentProfile = environment.getProperty("spring.profiles.active");
            if (currentProfile.equalsIgnoreCase("dev")) {
                log.warn("本地调试，直接忽略读取本地配置文件信息");
            } else {

                Map<String, Object> systemProperties = environment.getSystemProperties();
                // 读取配置文件，从配置文件中加载这个变量。
                String database = System.getProperty("user.home") + "/.config/eric-config/database.json";
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    File file = new File(database);
                    if (file.exists()) {
                        JsonNode jsonObject = objectMapper.readTree(file);
                        if (!jsonObject.isEmpty()) {
                            String databaseUrl = jsonObject.get("spring.datasource.url").textValue();
                            String databaseUsername = jsonObject.get("spring.datasource.username").textValue();
                            String password = jsonObject.get("spring.datasource.password").textValue();
                            if (databaseUrl.contains("jdbc:p6spy:mysql")) {
                                systemProperties.put("spring.datasource.driver-class-name", "com.p6spy.engine.spy.P6SpyDriver");
                            } else {
                                systemProperties.put("spring.datasource.driver-class-name", "com.mysql.cj.jdbc.Driver");
                            }
                            systemProperties.put("spring.datasource.url", databaseUrl);
                            systemProperties.put("spring.datasource.username", databaseUsername);
                            systemProperties.put("spring.datasource.password", password);

                            String r2dbcDatabaseUrl = jsonObject.get("spring.r2dbc.url").textValue();
                            systemProperties.put("spring.r2dbc.url", r2dbcDatabaseUrl);
                            systemProperties.put("spring.r2dbc.username", databaseUsername);
                            systemProperties.put("spring.r2dbc.password", password);
                        } else {
                            log.warn("数据库配置文件没有信息 {}", database);
                        }
                    } else {
                        log.error("没有数据库配置文件，那就配置yaml文件吧 {}", database);
                    }

                } catch (JsonProcessingException e) {
                    log.error("{}", e.getMessage(), e);
                } catch (IOException e) {
                    log.error("{}", e.getMessage(), e);
                }
            }
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }
}
