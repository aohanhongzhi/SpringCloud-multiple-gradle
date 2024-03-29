package com.dianping.weaver.admin.web.spring;

import com.dianping.weaver.admin.web.util.serializer.WeaverLongSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * 这个只是配置了 SpringMVC那一层，此外还有对外的MQ消息啥的，其他使用jackson序列化redis啥的
 */
@EnableWebMvc
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        MappingJackson2HttpMessageConverter jacksonMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = jacksonMessageConverter.getObjectMapper();

        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, new WeaverLongSerializer());
        simpleModule.addSerializer(Long.TYPE, new WeaverLongSerializer());
        objectMapper.registerModule(simpleModule);

        jacksonMessageConverter.setObjectMapper(objectMapper);

        converters.add(jacksonMessageConverter);
    }
}