package io.ark.engine.cache.redis;

import io.ark.engine.cache.core.DistributedLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * @author Noah Zhou
 * @description: 基于 Redis SET NX 的分布式锁实现。
 *
 * <p>释放锁通过 Lua 脚本保证原子性，防止以下竞态：
 * 线程A锁过期 → 线程B加锁 → 线程A误删线程B的锁。
 */
@Slf4j
@RequiredArgsConstructor
public class RedisDistributedLock implements DistributedLock {

    private static final String UNLOCK_SCRIPT =
            "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                    "return redis.call('del', KEYS[1]) else return 0 end";

    private final StringRedisTemplate redisTemplate;

    @Override
    public boolean tryLock(String lockKey, String lockValue, Duration ttl) {
        Boolean result = redisTemplate.opsForValue()
                .setIfAbsent(lockKey, lockValue, ttl);
        return Boolean.TRUE.equals(result);
    }

    @Override
    public void unlock(String lockKey, String lockValue) {
        redisTemplate.execute(
                new org.springframework.data.redis.core.script
                        .DefaultRedisScript<>(UNLOCK_SCRIPT, Long.class),
                java.util.List.of(lockKey),
                lockValue
        );
    }

    @Override
    public <T> T executeWithLock(String lockKey, Duration ttl, Supplier<T> supplier) {
        String lockValue = UUID.randomUUID().toString();
        if (!tryLock(lockKey, lockValue, ttl)) {
            throw new IllegalStateException("Failed to acquire lock: " + lockKey);
        }
        try {
            return supplier.get();
        } finally {
            unlock(lockKey, lockValue);
        }
    }
}
