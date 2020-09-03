package hxy.dream.common.configuration;

/**
 * @author eric
 * @program eric-dream
 * @description
 * @date 2020/6/25
 */

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import hxy.dream.common.serializer.BaseEnumDeserializer;
import hxy.dream.common.serializer.BaseEnumSerializer;
import hxy.dream.entity.enums.BaseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Configuration
public class BeanConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer enumCustomizer() {
//        将枚举转成json返回给前端
        return jacksonObjectMapperBuilder -> {
//            自定义序列化器注入
            Map<Class<?>, JsonSerializer<?>> serializers = new LinkedHashMap<>(1<<4);
            serializers.put(BaseEnum.class, new BaseEnumSerializer());
            jacksonObjectMapperBuilder.serializersByType(serializers);

//            自定义反序列化器注入
            Map<Class<?>, JsonDeserializer<?>> deserializers = new LinkedHashMap<>(1<<4);
            deserializers.put(BaseEnum.class, new BaseEnumDeserializer());
//            deserializers.put(GenderEnum.class, new BaseEnumDeserializer());
            jacksonObjectMapperBuilder.deserializersByType(deserializers);

        };
    }
}
