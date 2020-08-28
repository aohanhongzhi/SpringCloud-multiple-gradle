package hxy.dream.common.converter;

import hxy.dream.entity.enums.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 枚举编码 -> 枚举 转化器
 * </p>
 *
 * @description: 枚举编码 -> 枚举 转化器

 */
public class StringToEnumConverter<T extends BaseEnum> implements Converter<String, T> {
    private Map<String, T> enumMap = new HashMap<>();

    public StringToEnumConverter(Class<T> enumType) {
        T[] enums = enumType.getEnumConstants();
        for (T e : enums) {
            enumMap.put(e.code().toString(), e);
//            增强版本，可以加上description的转换
            enumMap.put(e.description(),e);
        }
    }

    @Override
    public T convert(String source) {
        T t = enumMap.get(source);
        if (ObjectUtils.isEmpty(t)) {
            throw new IllegalArgumentException("无法匹配对应的枚举类型");
        }
        return t;
    }
}