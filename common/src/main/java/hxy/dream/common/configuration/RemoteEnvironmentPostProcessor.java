package hxy.dream.common.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author Raina
 * @description 远程存储环境变量修改本地
 * @date 2022/2/25
 */
@Component
@Profile(value = {"prod", "beta"})
public class RemoteEnvironmentPostProcessor implements EnvironmentPostProcessor {
    private static final Logger log = LoggerFactory.getLogger(RemoteEnvironmentPostProcessor.class);

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String currentProfile = environment.getProperty("spring.profiles.active");
        if (currentProfile == null || currentProfile.equalsIgnoreCase("dev")) {
            log.debug("本地调试，直接忽略");
            return;
        } else {
            String url = environment.getProperty("azure.keyvault.url");
            final Properties properties = new Properties();
            if (url == null) {
                // 从远程vault这种管理工具或者redis里面获取配置信息，然后再更新到本地的配置中
                properties.put("password", "vault获取的值");
                PropertiesPropertySource propertySource = new PropertiesPropertySource("prod", properties);
                environment.getPropertySources().addFirst(propertySource);
            }
        }
    }
}
