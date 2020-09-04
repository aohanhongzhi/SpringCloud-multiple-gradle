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
 */
@Slf4j
public class StringToEnumConverter<T extends BaseEnum> implements Converter<String, T> {
    private Map<String, T> enumMap = new HashMap<>();

    /**   前端传过来的无论是code还是description，都是当做String过来的。
     * @param enumType
     * @see IntegerToEnumConverter 这个可能一直用不上
     */
    public StringToEnumConverter(Class<T> enumType) {
        T[] enums = enumType.getEnumConstants();
        for (T e : enums) {
            enumMap.put(e.code().toString(), e);
//            增强版本，可以加上description的转换
            enumMap.put(e.description(), e);
        }
    }

    /**
     * 这个是不针对json的。是针对表单提交的
     *
     * @param source
     * @return
     */
    @Override
    public T convert(String source) {
        T t = enumMap.get(source);
        if (ObjectUtils.isEmpty(t)) {
            throw new IllegalArgumentException("StringCodeToEnumConverter无法匹配对应的枚举类型:" + source);
        } else {
            log.info("\n====>StringCodeToEnumConverter转化[{}]成枚举[{}]", source, t);
        }
        return t;
    }
}