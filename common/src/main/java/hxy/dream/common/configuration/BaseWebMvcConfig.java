package hxy.dream.common.configuration;

import tools.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverters;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;

@Configuration
public class BaseWebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private JsonMapper jsonMapper;

    @Override
    public void configureMessageConverters(HttpMessageConverters.ServerBuilder builder) {
        JacksonJsonHttpMessageConverter jsonConverter = new JacksonJsonHttpMessageConverter(jsonMapper);
        jsonConverter.setDefaultCharset(StandardCharsets.UTF_8);

        builder.registerDefaults()
                .withStringConverter(new StringHttpMessageConverter(StandardCharsets.UTF_8))
                .withJsonConverter(jsonConverter);
    }
}