package io.ark.engine.data.core.enums;

/**
 * @author Noah Zhou 业务枚举规范接口 所有业务枚举实现此接口，统一序列化/反序列化行为
 * @description:
 */
public interface IBaseEnum<T> {
  T getValue();

  String getLabel();
}
