package hxy.dream.common.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
public class BaseWebMvcConfig extends WebMvcConfigurationSupport {
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter<?> converter : converters) {
            // 解决controller返回普通文本中文乱码问题
            if (converter instanceof StringHttpMessageConverter) {
//                ContentType: text/html,Chaset=UTF-8
                ((StringHttpMessageConverter) converter).setDefaultCharset(StandardCharsets.UTF_8);
            }
            // 解决controller返回json对象中文乱码问题
//            if (converter instanceof MappingJackson2HttpMessageConverter) {
//                ((MappingJackson2HttpMessageConverter) converter).setDefaultCharset(StandardCharsets.UTF_8);
//            }
        }
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        //setUseSuffixPatternMatch 后缀模式匹配
        configurer.setUseSuffixPatternMatch(true);

        //setUseTrailingSlashMatch 自动后缀路径模式匹配。尾斜杠默认不被支持，如果没有显式指定将返回404。
        configurer.setUseTrailingSlashMatch(true);
    }
}
