package hxy.dream.common.util;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author eric
 */
@Component
public class EnvironmentUtils implements EnvironmentAware {

    private static Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    /**
     * 或者直接@Value("${spring.profiles.active}")即可
     *
     * @return 当前选择的环境变量
     */
    public static String getActiveProfile() {
        if (environment != null) {
            if (environment.getActiveProfiles().length > 0) {
                return environment.getActiveProfiles()[0];
            } else {
                return environment.getDefaultProfiles()[0];
            }
        } else {
            return null;
        }
    }
}
