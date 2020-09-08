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

        String dto = value.dto();
        log.info("\n====>当前是DTO序列化[{}]", dto);
        gen.writeStartObject();
        gen.writeStringField("dto", dto);
        gen.writeEndObject();
    }
}
