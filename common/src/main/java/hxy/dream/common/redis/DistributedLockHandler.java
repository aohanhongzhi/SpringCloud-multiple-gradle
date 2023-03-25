package hxy.dream.common.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * 不是很严谨的分布式锁
 */
@Component
public class DistributedLockHandler {

    private static final Logger log = LoggerFactory.getLogger(DistributedLockHandler.class);


    public final static long LOCK_EXPIRE = 30 * 1000L;
    public final static long LOCK_TRY_INTERVAL = 30L;
    public final static long LOCK_TRY_TIMEOUT = 20 * 1000L;

    @Autowired
    private RedisTemplate redisTemplate;

    public boolean getLock(String key, String value) {
        try {
            if (StringUtils.hasLength(key) && StringUtils.hasLength(value)) {

                long startTime = System.currentTimeMillis();
                do {
                    if (!redisTemplate.hasKey(key)) {
                        redisTemplate.opsForValue().set(key, value, LOCK_EXPIRE, TimeUnit.MILLISECONDS);
                        return true;
                    }
                    if (System.currentTimeMillis() - startTime > LOCK_TRY_TIMEOUT) {
                        // 获取锁超时，直接放弃
                        return false;
                    }
                    Thread.sleep(LOCK_TRY_INTERVAL);
                } while (redisTemplate.hasKey(key));
            }
        } catch (InterruptedException e) {
            log.error("{}", e.getMessage(), e);
            return false;
        }
        return false;
    }

    public void releaseLock(String key) {
        if (StringUtils.hasLength(key)) {
            redisTemplate.delete(key);
        }
    }

}
