package io.ark.engine.core.domain;



/**
 * @Description: 前端统一返回
 * @Author: Noah Zhou
 */
//@Schema(name = "统一返回结果", description = "返回前端的响应结果", example = "{\"code\":200,\"msg\":\"success\",\"data\":\"\"}")
public class Result<T> {

    private int code;
    private String msg;
    private T data;

    public Result(int code, String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public static <D> Result<D> success(D data) {
        return new Result<>(200, "success", data);
    }
}
