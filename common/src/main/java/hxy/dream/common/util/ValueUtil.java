package hxy.dream.common.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ValueUtil {

    private static String value;

    /**
     * @return 为Spring容器外的方法提供SpringBoot的配置文件信息
     */
    public static String getValue() {
        return value;
    }

    @Value("${server.port}")
    public void setValue(String value) {
        ValueUtil.value = value;
    }

}
