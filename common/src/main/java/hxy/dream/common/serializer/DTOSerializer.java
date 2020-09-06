package hxy.dream.common.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import hxy.dream.entity.dto.DTO;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class DTOSerializer  extends JsonSerializer<DTO> {

    @Override
    public void serialize(DTO value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

        log.info("\n====>当前是DTO序列化");
        gen.writeStartObject();
        gen.writeStringField("dto", value.dto());
        gen.writeEndObject();
    }
}
