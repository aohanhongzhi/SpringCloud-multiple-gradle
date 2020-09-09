package hxy.dream.common.serializer;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.type.ClassKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleDeserializersWrapper extends SimpleDeserializers {
    static final Logger logger = LoggerFactory.getLogger(SimpleDeserializersWrapper.class);
    @Override
    public JsonDeserializer<?> findEnumDeserializer(Class<?> type, DeserializationConfig config, BeanDescription beanDesc) throws JsonMappingException {

        JsonDeserializer<?> enumDeserializer = super.findEnumDeserializer(type, config, beanDesc);
        if (enumDeserializer != null) {
            return enumDeserializer;
        }
        logger.info("\n重写枚举查找逻辑");
        for (Class<?> typeInterface : type.getInterfaces()) {
            enumDeserializer = this._classMappings.get(new ClassKey(typeInterface));
            if (enumDeserializer != null) {
                return enumDeserializer;
            }
        }
        return null;


//        return super.findEnumDeserializer(type, config, beanDesc);
    }
}
