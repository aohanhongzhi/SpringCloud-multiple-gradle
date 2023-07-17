package hxy.dream.common.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import hxy.dream.entity.enums.BaseEnum;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class BaseEnumSerializer extends JsonSerializer<BaseEnum> {


    @Override
    public void serialize(BaseEnum value, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        if (log.isTraceEnabled()) {
            log.trace("\n====>开始序列化[{}]", value);
        }
        if (value != null) {
            gen.writeStartObject();
            Integer code = value.code();
            if (code != null) {
                gen.writeNumberField("code", code);
            } else {
                log.warn("\n====>该枚举[{}]的code为空", value);
            }
            gen.writeStringField("description", value.description());
            gen.writeEndObject();
        }
    }

}