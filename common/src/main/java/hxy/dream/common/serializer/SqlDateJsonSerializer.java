package hxy.dream.common.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.sql.Date;

/**
 * 建议直接配置       objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
 * true 就是时间戳，false就是 正常日期
 */
@Deprecated
public class SqlDateJsonSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString("时间格式化" + date.toString());
    }

}