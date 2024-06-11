package hxy.dream.common.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.Date;

public class StringToDateConverterFactory  implements ConverterFactory<String, Date> {
    @Override
    public <T extends Date> Converter<String, T> getConverter(Class<T> targetType) {

        return new StringToDateConverter();
    }
}
