package hxy.dream.common.converter;

import hxy.dream.entity.enums.BaseEnum;
import lombok.extern.slf4j.Slf4j;
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
 * TODO 需要实际测试下，看看这个东西有木有生效。因为无论是code还是description都是走的StringToEnumConverter
 */
@Slf4j
public class IntegerToEnumConverter<T extends BaseEnum> implements Converter<Integer, T> {
    private Map<Integer, T> enumMap = new HashMap<>();

    public IntegerToEnumConverter(Class<T> enumType) {
        T[] enums = enumType.getEnumConstants();
        for (T e : enums) {
            enumMap.put(e.code(), e);
        }
    }

    @Override
    public T convert(Integer source) {
        T t = enumMap.get(source);
        if (ObjectUtils.isEmpty(t)) {
            throw new IllegalArgumentException("无法匹配对应的枚举类型");
        } else {

            log.info("\n====>IntegerCodeToEnumConverter转化[{}]成枚举[{}]", source, t);

        }
        return t;
    }
}