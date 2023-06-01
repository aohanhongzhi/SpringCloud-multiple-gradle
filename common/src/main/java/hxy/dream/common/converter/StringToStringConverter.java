package hxy.dream.common.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

/**
 * 这个转换器，可以去掉前端参数里的"null"值。
 * 【腾讯文档】SpringMVC学习
 * https://docs.qq.com/doc/DeXNEanBlc09MWG9C
 * <p>
 * GET {{host}}/file/list?pageNum=1&pageSize=20&searchValue=null
 * 等于下面这个
 * GET {{host}}/file/list?pageNum=1&pageSize=20&searchValue=
 */
public class StringToStringConverter implements Converter<String, String> {
    private static final Logger log = LoggerFactory.getLogger(StringToStringConverter.class);

    @Override
    public String convert(String source) {
        log.info("convert source: {}", source);
        if (source != null && "null".equals(source.toLowerCase())) {
            return "";
        }
        return source;
    }
}
