package io.ark.framework.result;

import io.ark.framework.exception.IErrorCode;

/**
 * @Description: 状态码字段载体，供枚举组合使用，消除重复样板代码
 *
 * @Author: Noah Zhou
 */
public record CodeSupport(int code, String messageKey) implements IErrorCode {

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessageKey() {
        return messageKey;
    }
}
