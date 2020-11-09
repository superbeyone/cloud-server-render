package com.tdt.cloud.render.exception;

/**
 * @author Mr.superbeyone
 * @project online-data-manager
 * @className CommonException
 * @description 自定义统一异常处理类
 * @date 2019-03-20 13:53
 **/

public class CommonException extends RuntimeException {
    private static final long serialVersionUID = -3032821876599556414L;
    private Integer code;

    private String msg;

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

    public CommonException(Integer code, String msg) {
        this.msg = msg;
        this.code = code;
    }
}
