package io.ark.engine.cache.core;

import java.time.Duration;
import java.util.function.Supplier;

/**
 * @author Noah Zhou
 * @description: 分布式锁抽象接口。
 *
 * <p>基于 Redis SET NX 实现，用于防止并发场景下的重复操作，
 * 如注册时的用户名唯一性保护、限流计数等。
 */
public interface DistributedLock {

    /**
     * 尝试加锁，成功返回 true
     *
     * @param lockKey   锁的 key
     * @param lockValue 锁的持有者标识（防止误释放他人的锁）
     * @param ttl       锁的自动过期时间
     */
    boolean tryLock(String lockKey, String lockValue, Duration ttl);

    /**
     * 释放锁（仅释放自己持有的锁）
     *
     * @param lockKey   锁的 key
     * @param lockValue 加锁时传入的持有者标识
     */
    void unlock(String lockKey, String lockValue);

    /**
     * 加锁执行，自动释放（推荐使用此方法）
     *
     * @param lockKey  锁的 key
     * @param ttl      锁的自动过期时间
     * @param supplier 加锁成功后执行的逻辑
     * @return 执行结果
     * @throws IllegalStateException 获取锁失败时抛出
     */
    <T> T executeWithLock(String lockKey, Duration ttl, Supplier<T> supplier);
}
