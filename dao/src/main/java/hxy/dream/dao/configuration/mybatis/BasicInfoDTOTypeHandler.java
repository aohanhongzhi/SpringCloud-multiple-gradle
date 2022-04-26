package hxy.dream.dao.configuration.mybatis;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import hxy.dream.entity.dto.BasicInfoDTO;

import java.io.IOException;
import java.util.List;

/**
 * 自定义复杂类型处理器<br/>
 * 不要问我为什么要重写 parse 因为顶层父类是无法获取到准确的待转换复杂返回类型数据
 * 一般的可以直接使用默认的处理即可，例如
 * @see com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler
 * @see com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler
 * @see com.baomidou.mybatisplus.extension.handlers.GsonTypeHandler
 *
 * @link https://github.com/baomidou/mybatis-plus-samples/blob/master/mybatis-plus-sample-typehandler/src/main/java/com/baomidou/mybatisplus/samples/typehandler/WalletListTypeHandler.java
 */
public class BasicInfoDTOTypeHandler extends JacksonTypeHandler {

    public BasicInfoDTOTypeHandler(Class<?> type) {
        super(type);
    }

    @Override
    protected Object parse(String json) {
        try {
            return getObjectMapper().readValue(json, new TypeReference<List<BasicInfoDTO>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}