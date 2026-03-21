package io.ark.engine.cache.autoconfigure;

import io.ark.engine.cache.core.CacheTemplate;
import io.ark.engine.cache.core.DistributedLock;
import io.ark.engine.cache.redis.RedisCacheTemplate;
import io.ark.engine.cache.redis.RedisDistributedLock;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author Noah Zhou
 * @description:
 * <p>{@code @ConditionalOnClass} 保证只有 classpath 中存在
 * {@code StringRedisTemplate} 时才激活 Redis 实现，
 * 后续新增 Caffeine 实现时可用同样方式条件装配。
 *
 * <p>{@code @ConditionalOnMissingBean} 保证业务模块可以自定义实现覆盖默认 Bean。
 */
@AutoConfiguration
@ConditionalOnClass(StringRedisTemplate.class)
public class CacheAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(CacheTemplate.class)
    public CacheTemplate cacheTemplate(StringRedisTemplate redisTemplate) {
        return new RedisCacheTemplate(redisTemplate);
    }

    @Bean
    @ConditionalOnMissingBean(DistributedLock.class)
    public DistributedLock distributedLock(StringRedisTemplate redisTemplate) {
        return new RedisDistributedLock(redisTemplate);
    }
}
