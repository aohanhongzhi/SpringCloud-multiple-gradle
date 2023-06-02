package hxy.dream.common.reids;

import hxy.dream.BaseTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * @author eric
 * @description
 * @date 2023/6/2
 */
public class RedisTemplateTest extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(RedisTemplateTest.class);


    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void testRedisTemplate() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String key = "key";
        valueOperations.set(key, "value");

        Object o = valueOperations.get(key);

        log.info("结果：{}", o);


    }

}
