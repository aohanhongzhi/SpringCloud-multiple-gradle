package hxy.dream.common.serializer;

import hxy.dream.entity.enums.BaseEnum;

import java.lang.reflect.Method;
 
/**
 * 传入的参数中,去相应的枚举code中获取
 */
public class DefaultInputJsonToEnum{
 
  //  private static final Logger logger = LoggerFactory.getFrameworkLogger(DefaultInputJsonToEnum.class);
 
    public static BaseEnum getEnum(String inputParameter, Class enumClass) {
        try {
            Method valuesMethod = enumClass.getDeclaredMethod("values");
 
            BaseEnum[] values = (BaseEnum[]) valuesMethod.invoke(null);
            BaseEnum baseEnum = null;
 
            for (BaseEnum value : values) {
                //因为inputParameter都是string类型的,code + "" 转成字符串才能比较
                    if (inputParameter.equals(value.code() + "")) {
                        baseEnum = value;
                        break;
                    } else {
                        continue;
                    }
                }
 
            //如果都拿不到,那就直接抛出异常了
            if (baseEnum == null) {
                throw new RuntimeException("输入参数不符合预期");
            }
            return baseEnum;
        } catch (Exception e) {
           // logger.error("getEnum error: " + Throwables.getStackTraceAsString(e));
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