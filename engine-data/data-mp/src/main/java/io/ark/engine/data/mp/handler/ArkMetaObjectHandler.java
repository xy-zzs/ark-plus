package io.ark.engine.data.mp.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @description: MP 字段自动填充处理器
 * <p>INSERT：填充 createdAt / updatedAt / createdBy / updatedBy
 * <p>UPDATE：仅填充 updatedAt / updatedBy，不覆盖创建信息
 *
 * <p>依赖 Spring Security 上下文获取当前用户 ID。
 * 若上下文为空（定时任务、初始化脚本等场景），createdBy/updatedBy 填 0L。
 * @author Noah Zhou
 */
@Slf4j
@Component
public class ArkMetaObjectHandler implements MetaObjectHandler {
    private static final Long SYSTEM_USER_ID = 0L;

    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        Long currentUserId = resolveCurrentUserId();

        strictInsertFill(metaObject, "createdAt", LocalDateTime.class, now);
        strictInsertFill(metaObject, "updatedAt", LocalDateTime.class, now);
        strictInsertFill(metaObject, "createdBy", Long.class, currentUserId);
        strictInsertFill(metaObject, "updatedBy", Long.class, currentUserId);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        strictUpdateFill(metaObject, "updatedAt", LocalDateTime.class, LocalDateTime.now());
        strictUpdateFill(metaObject, "updatedBy", Long.class, resolveCurrentUserId());
    }

    /**
     * 从 Spring Security 上下文解析当前用户 ID
     *
     * <p>约定：SecurityUser（在 security-core 里定义）实现 UserDetails，
     * 其 principal 可转为 Long userId。
     * 此处用 Object 接收，避免 data-mp 直接依赖 security-core。
     */
    private Long resolveCurrentUserId() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return SYSTEM_USER_ID;
            }
            Object principal = authentication.getPrincipal();
            // principal 由 security-core 的 UserDetails 实现提供
            // 若 principal 是数字字符串（如 userId），直接解析
            if (principal instanceof Long userId) {
                return userId;
            }
            // 兜底：无法识别时用系统ID，记录 debug 日志便于排查
            log.debug("MetaObjectHandler: unrecognized principal type [{}], fallback to SYSTEM_USER_ID",
                    principal.getClass().getSimpleName());
            return SYSTEM_USER_ID;
        } catch (Exception e) {
            log.warn("MetaObjectHandler: failed to resolve current user, fallback to SYSTEM_USER_ID", e);
            return SYSTEM_USER_ID;
        }
    }
}
