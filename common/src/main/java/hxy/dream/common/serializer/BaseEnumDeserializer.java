package hxy.dream.common.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import hxy.dream.entity.enums.BaseEnum;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * 面向接口的枚举反序列化器
 *
 * @author eric
 * @date 2020/9/2
 */
@Slf4j
public class BaseEnumDeserializer extends JsonDeserializer<BaseEnum> {
    @Override
    public BaseEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        try {
            // 前端输入的值
            String inputParameter = p.getText();
            if (inputParameter == null || inputParameter.length() == 0) {
                return null;
            }

            JsonStreamContext parsingContext = p.getParsingContext();
            // 字段名
            String currentName = parsingContext.getCurrentName();
            // 前端注入的对象(如：ResParam)
            Object currentValue = parsingContext.getCurrentValue();
            if (currentValue != null) {
                // 通过对象和属性名获取属性的类型
                Field field = ReflectionUtils.getField(currentValue.getClass(), currentName);
                // 获取对应得枚举类
                Class enumClass = field.getType();
                // 根据对应的值和枚举类获取相应的枚举值
                BaseEnum anEnum = DefaultInputJsonToEnum.getEnum(inputParameter, enumClass);
                if (log.isTraceEnabled()) {
                    log.trace("\n====>测试反序列化枚举[{}]==>[{}.{}]", inputParameter, anEnum.getClass(), anEnum);
                }
                return anEnum;
            } else {
                throw new RuntimeException("枚举反序列化失败,注意该属性不可以使用lombok的注解，如@NonNull等");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
