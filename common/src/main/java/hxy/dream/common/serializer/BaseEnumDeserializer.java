package hxy.dream.common.serializer;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.util.CompactStringObjectMap;
import hxy.dream.entity.enums.BaseEnum;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * @author eric
 * @date 2020/9/2
 */
@Slf4j
public class BaseEnumDeserializer extends JsonDeserializer<BaseEnum> {
    @Override
    public BaseEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        try {
            //前端输入的值
            String inputParameter = p.getText();
            if (StringUtils.isBlank(inputParameter)) {
                return null;
            }


            JsonStreamContext parsingContext = p.getParsingContext();
            String currentName = parsingContext.getCurrentName();//字段名
            Object currentValue = parsingContext.getCurrentValue();//前端注入的对象(ResDTO)
            Field field = ReflectionUtils.getField(currentValue.getClass(), currentName); // 通过对象和属性名获取属性的类型
            Class enumClass = field.getType();

            BaseEnum anEnum = DefaultInputJsonToEnum.getEnum(inputParameter, enumClass);
            log.info("\n====>测试反序列化枚举[{}]==>[{}.{}]", inputParameter, anEnum.getClass(),anEnum);
            return anEnum;
        } catch (Exception e) {
            // logger.error("JsonEnumDeserializer deserialize error: " + Throwables.getStackTraceAsString(e));
//            System.out.println("JsonEnumDeserializer deserialize error: " + Throwables.getStackTraceAsString(e));
            throw new RuntimeException(e);
        }


    }
}
