package com.necer.basic.network;

/**
 * 自定义异常
 */
public class ApiException extends Exception {
    private String code;//错误码
    private String message;//错误信息

    public ApiException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }
}
