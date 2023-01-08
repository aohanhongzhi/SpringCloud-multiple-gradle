package hxy.dream.common.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * 或者直接yaml文件配置也是可以的 spring.jackson.date-format
 */
public class DateJsonSerializer extends JsonSerializer<Date> {

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    ZoneId zoneId = ZoneId.systemDefault();

    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        LocalDateTime localDateTime = date.toInstant().atZone(zoneId).toLocalDateTime();
        String yearMonth = localDateTime.format(dtf);
        jsonGenerator.writeString(yearMonth);
    }

}