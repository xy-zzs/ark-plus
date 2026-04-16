package io.ark.engine.web.stater.config;

import io.ark.engine.web.core.handler.GlobalExceptionHandler;
import io.ark.engine.web.core.i18n.ArkLocaleResolver;
import io.ark.engine.web.core.i18n.MessageSourceHolder;
import io.ark.engine.web.core.i18n.MessageSourceResolver;
import io.ark.framework.i18n.MessageResolver;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;

import java.nio.charset.StandardCharsets;

/**
 * @Description:
 * @Author: Noah Zhou
 */
@AutoConfiguration
@Import(GlobalExceptionHandler.class)
public class WebAutoConfiguration {

    /**
     * 聚合所有模块 i18n 资源。
     * 各模块 *-spring-boot-starter 按约定在 resources/i18n/{module}/ 下放置文件即可。
     * 新增模块时在此追加对应 basename。
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setBasenames(
                "classpath:i18n/messages",
                "classpath:i18n/auth/messages",
                "classpath:i18n/user/messages"
        );
        source.setDefaultEncoding(StandardCharsets.UTF_8.name());
        // key 不存在时返回 key 本身，避免 NoSuchMessageException
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }
    /**
     * Bean 注入方式，供 Service 等可注入场景使用
     */
    @Bean
    public MessageResolver messageResolver(MessageSource messageSource) {
        MessageSourceResolver resolver = new MessageSourceResolver(messageSource);
        // 同时初始化静态持有器，供枚举/工具类使用
        MessageSourceHolder.setMessageSource(messageSource);
        return resolver;
    }

    /**
     * Locale 解析策略：优先 lang 参数，其次 Accept-Language Header
     * Spring MVC 会用此 Bean 在请求进入时写入 LocaleContextHolder，
     * 保证 MessageSourceHolder 和 MessageSourceResolver 都能拿到正确 Locale
     */
    @Bean
    @ConditionalOnMissingBean
    public LocaleResolver localeResolver() {
        return new ArkLocaleResolver();
    }
    //------
    /**
     * MessageSource：扫描所有模块的 i18n/messages 文件
     * 启动模块放 messages.properties，各业务模块也可放自己的消息文件，
     * 通过 addBasenames 叠加，互不覆盖
     */
    /*@Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.addBasenames("i18n/messages");   // 对应 resources/i18n/messages*.properties
        source.setDefaultEncoding("UTF-8");
        source.setUseCodeAsDefaultMessage(true); // key 找不到时返回 key 本身，不抛异常
        // 注入静态持有器，供非 Bean 场景使用
        MessageSourceHolder.setMessageSource(source);
        return source;
    }*/

    /**
     * LocaleResolver：从 Accept-Language 请求头解析 locale
     * 前端请求时携带 Accept-Language: en 即切换为英文
     * 默认 locale 为中文
     */
    /*@Bean
    @ConditionalOnMissingBean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        resolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return resolver;
    }*/
}
