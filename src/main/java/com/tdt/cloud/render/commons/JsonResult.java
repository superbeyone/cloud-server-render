package com.tdt.cloud.render.commons;

/**
 * @program: tool
 * @description:
 * @author: Mr.superbeyone
 * @create: 2018-10-18 16:37
 **/
public class JsonResult<T> {

    private Integer code;

    private String msg;

    private T data;


    public JsonResult() {
    }

    public static JsonResult getInstance() {
        return new JsonResult<>();
    }

    public JsonResult(int code, String message, T data) {
        this.code = code;
        this.msg = message;
        this.data = data;
    }

    public JsonResult success(T data) {
        return new JsonResult(200, "操作成功", data);
    }

   

    public static JsonResult fail(int status, String message) {
        return new JsonResult(status, message, null);
    }
    

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
