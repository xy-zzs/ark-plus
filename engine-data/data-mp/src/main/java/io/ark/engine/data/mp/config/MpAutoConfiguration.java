package io.ark.engine.data.mp.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @description: MyBatis-Plus 自动配置
 *     <p>注册顺序（MP 官方规范）：
 *     <ol>
 *       <li>分页插件 — 必须第一个，否则 COUNT SQL 可能异常
 *       <li>乐观锁插件 — 更新时自动处理 version 字段
 *       <li>防全表更新/删除插件 — 拦截无 WHERE 条件的危险操作
 *     </ol>
 *     <p>{@code @ConditionalOnMissingBean} 保证业务方可以自定义覆盖此配置
 * @author Noah Zhou
 */
@AutoConfiguration
public class MpAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(MybatisPlusInterceptor.class)
  public MybatisPlusInterceptor mybatisPlusInterceptor() {
    MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

    // 1. 分页插件（DbType 明确指定，避免 MP 自动探测失败）
    interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));

    // 2. 乐观锁插件（配合 @Version 注解使用）
    interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());

    // 3. 防全表更新/删除（开发期保护，生产按需保留）
    interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());

    return interceptor;
  }
}
