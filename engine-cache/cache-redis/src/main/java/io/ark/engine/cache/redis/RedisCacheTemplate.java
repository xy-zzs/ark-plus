package io.ark.engine.cache.redis;

import io.ark.engine.cache.core.CacheTemplate;
import java.time.Duration;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Noah Zhou
 * @description: 基于 Spring Data Redis 的 CacheTemplate 实现。
 *     <p>使用 {@link StringRedisTemplate} 操作字符串类型， 复杂对象由调用方自行序列化为 JSON 字符串后存入。
 */
@Component
@RequiredArgsConstructor
public class RedisCacheTemplate implements CacheTemplate {

  private final StringRedisTemplate redisTemplate;

  @Override
  public void set(String key, String value) {
    redisTemplate.opsForValue().set(key, value);
  }

  @Override
  public void set(String key, String value, Duration ttl) {
    redisTemplate.opsForValue().set(key, value, ttl);
  }

  @Override
  public Optional<String> get(String key) {
    return Optional.ofNullable(redisTemplate.opsForValue().get(key));
  }

  @Override
  public void delete(String key) {
    redisTemplate.delete(key);
  }

  @Override
  public boolean exists(String key) {
    return Boolean.TRUE.equals(redisTemplate.hasKey(key));
  }

  @Override
  public long increment(String key) {
    Long value = redisTemplate.opsForValue().increment(key);
    return value != null ? value : 0L;
  }

  @Override
  public void expire(String key, Duration ttl) {
    redisTemplate.expire(key, ttl);
  }
}
