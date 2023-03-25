package hxy.dream.common.configuration;

/**
 * @author eric
 * @program eric-dream
 * @description
 * @date 2020/6/25
 */

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import hxy.dream.common.serializer.BaseEnumDeserializer;
import hxy.dream.common.serializer.BaseEnumSerializer;
import hxy.dream.common.serializer.BaseLongSerializer;
import hxy.dream.common.serializer.DateJsonDeserializer;
import hxy.dream.common.serializer.DateJsonSerializer;
import hxy.dream.common.serializer.LocalDateTimeJsonSerializer;
import hxy.dream.common.serializer.SimpleDeserializersWrapper;
import hxy.dream.common.serializer.StringTrimDeserializer;
import hxy.dream.entity.enums.BaseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

@Slf4j
@Configuration
public class BeanConfig {


    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        SimpleModule simpleModule = new SimpleModule();

        SimpleDeserializersWrapper deserializers = new SimpleDeserializersWrapper();
        deserializers.addDeserializer(BaseEnum.class, new BaseEnumDeserializer());
        deserializers.addDeserializer(Date.class, new DateJsonDeserializer());
        simpleModule.setDeserializers(deserializers);

        simpleModule.addSerializer(BaseEnum.class, new BaseEnumSerializer());
        simpleModule.addSerializer(Date.class, new DateJsonSerializer());
        simpleModule.addSerializer(LocalDateTime.class, new LocalDateTimeJsonSerializer());

        // 超过浏览器处理精度的Long类型转成String给前端
        simpleModule.addSerializer(Long.class, new BaseLongSerializer());
        simpleModule.addSerializer(Long.TYPE, new BaseLongSerializer());

        simpleModule.addDeserializer(String.class, new StringTrimDeserializer(String.class));

        builder.timeZone(TimeZone.getDefault());
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.registerModule(simpleModule);

        // 配置忽略未知属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return objectMapper;
    }

    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        mybatisplus与pagehelper的功能冲突了,所以后面会带上两个limit
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        paginationInnerInterceptor.setMaxLimit(100L);

//        防止全表更新与删除
        BlockAttackInnerInterceptor blockAttackInnerInterceptor = new BlockAttackInnerInterceptor();

        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        interceptor.addInnerInterceptor(blockAttackInnerInterceptor);
        return interceptor;
    }

    /**
     *  解决两个斜杠问题。后期搞个方案，直接从一开始就直接去掉多余的斜杠，保证后期处理都是正常的。
     * //此种方式没起作用
     * StrictHttpFirewall firewall = new StrictHttpFirewall();
     * firewall.setAllowSemicolon(true);
     * return firewall;
     */
//    @Bean
//    public HttpFirewall allowUrlSemicolonHttpFirewall() {
//
//        //起作用
//        return new DefaultHttpFirewall();
//    }
}
