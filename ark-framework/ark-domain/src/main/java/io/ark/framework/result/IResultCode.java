package io.ark.framework.result;

/**
 * @Description: 响应码顶层接口，成功码和错误码均实现此接口
 *
 * <p>设计约定：
 *
 * <ul>
 *   <li>{@code code} 建议按模块划分段位，如 user 模块用 1000~1999，auth 模块用 2000~2999
 *   <li>{@code messageKey} 对应各模块 i18n 资源文件中的 key，由 engine-web 层解析为实际文案
 * </ul>
 *
 * @Author: Noah Zhou
 */
public interface IResultCode {

  /** 状态码，全局唯一 */
  int getCode();

  /** i18n 消息 key，对应 messages*.properties 中的键 */
  String getMessageKey();
}
