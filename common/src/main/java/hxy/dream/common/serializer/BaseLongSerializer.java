package hxy.dream.common.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author eric
 * @date 2021/8/13
 */
public class BaseLongSerializer extends JsonSerializer<Long> {
    /**
     * 前端js内置的number类型最大是基于32位整数的，Number类型的最大安全整数为9007199254740991，当Java Long型的值大小超过JS Number的最大安全整数时，超出此范围的整数值可能会被破坏，丢失精度。
     *
     * @param value
     * @param gen
     * @param serializers
     * @throws IOException
     */
    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value > 9007199254740990L) {
            gen.writeNumber("\"" + value + "\"");
        } else {
            gen.writeNumber(value);
        }
    }
}
