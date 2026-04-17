package io.ark.engine.cache.core;

import java.time.Duration;
import java.util.Optional;

/**
 * @author Noah Zhou
 * @description: 缓存操作抽象接口。
 *     <p>业务层统一依赖此接口，不直接使用 RedisTemplate / Caffeine 等具体实现， 保证缓存方案可替换。
 *     <p>Key 约定：使用冒号分隔的层级结构，如 {@code session:user:123}， 便于 Redis 的 key 扫描和管理工具展示。
 */
public interface CacheTemplate {

  /** 写入缓存，永不过期 */
  void set(String key, String value);

  /** 写入缓存，指定过期时间 */
  void set(String key, String value, Duration ttl);

  /** 读取缓存，不存在返回 empty */
  Optional<String> get(String key);

  /** 删除缓存 */
  void delete(String key);

  /** 判断 key 是否存在 */
  boolean exists(String key);

  /** 计数器递增，返回递增后的值 */
  long increment(String key);

  /** 设置过期时间 */
  void expire(String key, Duration ttl);
}
