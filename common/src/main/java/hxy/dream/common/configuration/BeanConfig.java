package hxy.dream.common.configuration;

/**
 * @author eric
 * @program eric-dream
 * @description
 * @date 2020/6/25
 */

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import hxy.dream.common.serializer.BaseEnumDeserializer;
import hxy.dream.common.serializer.BaseEnumSerializer;
import hxy.dream.common.serializer.DTOSerializer;
import hxy.dream.common.serializer.DateJsonSerializer;
import hxy.dream.common.serializer.SimpleDeserializersWrapper;
import hxy.dream.entity.dto.DTO;
import hxy.dream.entity.enums.BaseEnum;
import hxy.dream.entity.enums.GenderEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.Date;
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
            Map<Class<?>, JsonSerializer<?>> serializers = new LinkedHashMap<>();
            serializers.put(BaseEnum.class, new BaseEnumSerializer());
//            serializers.put(Enum.class, new BaseEnumSerializer());
//            serializers.put(GenderEnum.class, new BaseEnumSerializer());
//            serializers.put(Date.class, new DateJsonSerializer());
//            serializers.put(DTO.class, new DTOSerializer());
            jacksonObjectMapperBuilder.serializersByType(serializers);

//            自定义反序列化器注入
            Map<Class<?>, JsonDeserializer<?>> deserializers = new LinkedHashMap<>();
            deserializers.put(BaseEnum.class, new BaseEnumDeserializer());
//            deserializers.put(GenderEnum.class, new BaseEnumDeserializer());
            jacksonObjectMapperBuilder.deserializersByType(deserializers);

        };
    }

    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        SimpleDeserializersWrapper deserializers = new SimpleDeserializersWrapper();
        deserializers.addDeserializer(BaseEnum.class, new BaseEnumDeserializer());

        SimpleModule simpleModule = new SimpleModule();
        simpleModule.setDeserializers(deserializers);
        simpleModule.addSerializer(BaseEnum.class, new BaseEnumSerializer());

        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }
}
