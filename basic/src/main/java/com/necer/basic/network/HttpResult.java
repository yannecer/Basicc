package com.necer.basic.network;

/**
 * Created by necer on 2018/3/22.
 */

public class HttpResult<T> {

    public String code;
    public String message;
    public Object version;
    public T data;

    public boolean isSuccess() {
        return "00".equals(code);
    }
}
