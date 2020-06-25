package hxy.dream.app.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hxy.dream.BaseTest;
import hxy.dream.entity.enums.GenderEnum;
import org.junit.Test;

import javax.annotation.Resource;

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
            // 输出字符串 MALE
            System.out.println(s);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
