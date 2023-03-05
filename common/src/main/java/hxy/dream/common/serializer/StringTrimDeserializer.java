package hxy.dream.common.serializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;

import java.io.IOException;

/**
 * jackson全局去掉 前后空格,特别是接受前端参数
 */
public class StringTrimDeserializer extends StdScalarDeserializer<String> {

    /**
     * 子类可以重写父类中的protected方法，并把它的可见性改为public，但是子类不能削弱父类中定义的方法的可访问性。
     * 例如：如果一个方法在父类中被定义为public，在子类中也必须为public。子类方法的可访问性必须大于等于父类方法的可访问性。
     *
     * @param vc
     */
    public StringTrimDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {

        String valueAsString = p.getValueAsString();

        if (valueAsString != null) {
            valueAsString = valueAsString.trim();
        }

        return valueAsString;
    }
}
