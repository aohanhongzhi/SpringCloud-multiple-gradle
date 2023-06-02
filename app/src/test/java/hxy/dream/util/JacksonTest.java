package hxy.dream.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import hxy.dream.common.serializer.SqlDateJsonSerializer;
import hxy.dream.entity.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;

/**
 * @author eric
 * @description
 * @date 2023/5/26
 */
public class JacksonTest {

    private static final Logger log = LoggerFactory.getLogger(JacksonTest.class);

    @Test
    public void testDateSerialization() throws Exception {

        Date date = new Date(122, 5, 23);

        String string = date.toString();
        log.info("{}", string);


        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Date.class, new SqlDateJsonSerializer());
        objectMapper.registerModule(simpleModule);
        String s = objectMapper.writeValueAsString(date);
        log.info("{}", s);

    }

    @Test
    public void testSqlDate() throws Exception {

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setName("test");

        // 看Date的序列化
        userDTO.setRegisterDate(new Date(110, 1, 1));

        // 自定义序列化器
        ObjectMapper objectMapper = new ObjectMapper();
        if (false) {
            // 这里相当于全局配置
            SimpleModule simpleModule = new SimpleModule();
            simpleModule.addSerializer(Date.class, new SqlDateJsonSerializer());
            objectMapper.registerModule(simpleModule);
        }

        // 修改配置即可。从com.fasterxml.jackson.databind.ser.std.SqlDateSerializer源代码看出来的
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);

        String s = objectMapper.writeValueAsString(userDTO);
        log.info("{}", s);

    }


}
