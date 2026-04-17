package io.ark.framework.result;

/**
 * @Description: 成功码 @Author: Noah Zhou
 */
public enum SuccessCode implements IResultCode {
  SUCCESS(0, "common.success");

  private final CodeSupport support;

  SuccessCode(int code, String messageKey) {
    this.support = new CodeSupport(code, messageKey);
  }

  @Override
  public int getCode() {
    return support.getCode();
  }

  @Override
  public String getMessageKey() {
    return support.getMessageKey();
  }
}
