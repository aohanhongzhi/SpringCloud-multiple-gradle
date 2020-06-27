package hxy.dream.app.util.configuration;

/**
 * @author eric
 * @program eric-dream
 * @description
 * @date 2020/6/25
 */

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import hxy.dream.entity.enums.BaseEnum;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class BeanConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer enumCustomizer() {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.serializerByType(BaseEnum.class, new JsonSerializer<BaseEnum>() {
            @Override
            public void serialize(BaseEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeStartObject();
                gen.writeNumberField("code", value.code());
                gen.writeStringField("description", value.description());
                gen.writeEndObject();


            }
        });
    }
}
