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
        for (Class<?> typeInterface : type.getInterfaces()) {

            /*
             import com.fasterxml.jackson.databind.type.ClassKey; // 正确
             import com.fasterxml.classmate.util.ClassKey; // 这个是错误的
             注意jackson里面有两个相同的类名，如果使用错误，可能得不到自己想要的结果。
             */
            enumDeserializer = this._classMappings.get(new ClassKey(typeInterface));
            if (enumDeserializer != null) {
                logger.info("\n====>重写枚举查找逻辑[{}]", enumDeserializer);
                return enumDeserializer;
            }
        }
        return null;

    }

    /**
     * 用于调试ClassKey问题的,生产使用可以忽略。
     *
     * @param typeInterface
     * @return
     */
//    @Deprecated
//    private JsonDeserializer<?> getJsonDeserializer(Class<?> typeInterface) {
//        JsonDeserializer<?> enumDeserializer;
//        final ClassKey key = new ClassKey(typeInterface);
//        final int keyHashCode = key.hashCode();
//        enumDeserializer = this._classMappings.get(key);
//        final com.fasterxml.classmate.util.ClassKey key1 = new com.fasterxml.classmate.util.ClassKey(typeInterface);
//        final int keyHashCode1 = key1.hashCode();
//        if (keyHashCode == keyHashCode1) {
//            logger.info("\n====>hashCode相同,{}", keyHashCode);
//        }
//
//        JsonDeserializer<?> enumDeserializerNull = this._classMappings.get(key1);
//        logger.info("\n====>{}", enumDeserializerNull);
//        if (enumDeserializer != null) {
//            logger.info("\n重写枚举查找逻辑[{}]", enumDeserializer);
//            return enumDeserializer;
//        }
//        return null;
//    }
}
