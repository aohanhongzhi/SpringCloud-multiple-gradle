package hxy.dream.common.serializer;

import hxy.dream.entity.enums.BaseEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 传入的参数中,去相应的枚举code中获取
 */
public class DefaultInputJsonToEnum {

    private static final Logger logger = LoggerFactory.getLogger(DefaultInputJsonToEnum.class);

    public static BaseEnum getEnum(String inputParameter, Class enumClass) {
        boolean debugEnabled = logger.isDebugEnabled();
        if (debugEnabled) {
            logger.debug("\n====>反序列化，当前输入的值[{}]对应的枚举类是[{}]", inputParameter, enumClass);
        }
        try {
//            values是默认的方法，必定存在
            Method valuesMethod = enumClass.getDeclaredMethod("values");
//          通过反射获取该枚举类的所有枚举值
            BaseEnum[] values = (BaseEnum[]) valuesMethod.invoke(null);
            BaseEnum baseEnum = null;

            for (BaseEnum value : values) {
                //因为inputParameter都是string类型的,code转成字符串才能比较
                if (inputParameter.equals(String.valueOf(value.code())) || inputParameter.equals(value.description())) {
                    baseEnum = value;
                    break;
                }
            }

            //如果都拿不到,那就直接抛出异常了
            if (baseEnum == null) {
                throw new RuntimeException(String.format("枚举反序列化错误，输入参数[%s]找不到对应的枚举值", inputParameter));
            }
            return baseEnum;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private BaseEnum getEnumByName(String inputParameter, Class enumClass) {
        try {
            Method valueOfMethod = enumClass.getDeclaredMethod("valueOf", String.class);
            return (BaseEnum) valueOfMethod.invoke(null, inputParameter);
        } catch (Exception e) {
            //这里异常无需抛出,因为这个枚举根据name获取不到就直接抛出异常了...
            //logger.warn("未获取枚举的name值,入参: " + inputParameter + "," + "class : " + enumClass);
            return null;
        }
    }

}