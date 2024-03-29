package hxy.dream.app.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import hxy.dream.BaseTest;
import hxy.dream.entity.enums.GenderEnum;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;

/**
 * @author eric
 * @program eric-dream
 * @description
 * @date 2020/6/25
 */
public class EnumTest extends BaseTest {
    @Resource
    private ObjectMapper objectMapper;

    @Test
    public void enumTest() {
        try {
            String s = objectMapper.writeValueAsString(GenderEnum.BOY);
            // 输出字符串 {"code":1,"description":"男"}
            System.out.println(s);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void enumJsonTest(){
        try {
            String s = objectMapper.writeValueAsString(GenderEnum.BOY);
            GenderEnum genderEnum = objectMapper.readValue(s, GenderEnum.class);
            System.out.println(genderEnum);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void enumJsonTest2(){

            GenderEnum byCode = GenderEnum.getEnumByCode(100);
            System.out.println(byCode);

    }



}
