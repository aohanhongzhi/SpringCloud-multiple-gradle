package hxy.dream.common.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
public class BaseWebMvcConfig extends WebMvcConfigurationSupport {

    @Autowired
    ObjectMapper objectMapper;

    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter<?> converter : converters) {
            // 解决controller返回普通文本中文乱码问题
            if (converter instanceof StringHttpMessageConverter) {
//                ContentType: text/html,Chaset=UTF-8
                ((StringHttpMessageConverter) converter).setDefaultCharset(StandardCharsets.UTF_8);
            }

            if (converter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = (MappingJackson2HttpMessageConverter) converter;
                // 把自己定义的序列化措施，放进去。
                mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
                // 解决controller返回json对象中文乱码问题
                mappingJackson2HttpMessageConverter.setDefaultCharset(StandardCharsets.UTF_8);
            }
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
