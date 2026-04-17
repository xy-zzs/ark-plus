package io.ark.engine.data.core.context;

import lombok.NoArgsConstructor;

/**
 * @author Noah Zhou
 * @description:
 */
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class TenantContext {

  private static final ThreadLocal<Long> TENANT_ID = new InheritableThreadLocal<>();

  public static void set(Long tenantId) {
    TENANT_ID.set(tenantId);
  }

  public static Long get() {
    return TENANT_ID.get();
  }

  public static void clear() {
    TENANT_ID.remove();
  }
}
