package hxy.dream.common.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import hxy.dream.common.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

/**
 * redis 配置类
 */
@Slf4j
public class RedisConfigCacheManager extends RedisCacheManager {


    public RedisConfigCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }


    private static final CacheKeyPrefix DEFAULT_CACHE_KEY_PREFIX = cacheName -> cacheName + ":";

    @Override
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {

        ObjectMapper objectMapper = SpringUtils.getBean(ObjectMapper.class);

        RedisSerializationContext.SerializationPair<Object> DEFAULT_PAIR = RedisSerializationContext.SerializationPair
                .fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));

        if (name != null && name.length() > 0) {
            final int lastIndexOf = name.lastIndexOf('#');
            if (lastIndexOf > -1) {
                final String ttl = name.substring(lastIndexOf + 1);
                if (isNumeric(ttl)) {
                    final Duration duration = Duration.ofSeconds(Long.parseLong(ttl));
                    cacheConfig = cacheConfig.entryTtl(duration);
                    //修改缓存key和value值的序列化方式
                    cacheConfig = cacheConfig.computePrefixWith(DEFAULT_CACHE_KEY_PREFIX)
                            .serializeValuesWith(DEFAULT_PAIR);
                    final String cacheName = name.substring(0, lastIndexOf);
                    return super.createRedisCache(cacheName, cacheConfig);
                } else {
                    log.error("过期时间配置错误！！！#号后面不是数字 => {}", ttl);
                }
            }
        }
        //修改缓存key和value值的序列化方式
        cacheConfig = cacheConfig.computePrefixWith(DEFAULT_CACHE_KEY_PREFIX)
                .serializeValuesWith(DEFAULT_PAIR);
        return super.createRedisCache(name, cacheConfig);
    }

    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}