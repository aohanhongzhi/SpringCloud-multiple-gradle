package hxy.dream.common.configuration;

import hxy.dream.common.converter.IntegerCodeToEnumConverterFactory;
import hxy.dream.common.converter.StringCodeToEnumConverterFactory;
import hxy.dream.common.converter.StringToDateConverterFactory;
import hxy.dream.common.converter.StringToStringConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 * MVC通用配置
 * </p>
 *
 * @description: MVC通用配置
 * @version: V1.0
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 枚举类的转换器工厂 addConverterFactory
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToStringConverter());
        registry.addConverterFactory(new IntegerCodeToEnumConverterFactory());
        registry.addConverterFactory(new StringCodeToEnumConverterFactory());
        registry.addConverterFactory(new StringToDateConverterFactory());
    }
}