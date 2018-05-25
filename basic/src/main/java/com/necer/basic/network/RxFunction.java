package com.necer.basic.network;

import io.reactivex.functions.Function;

/**
 * 错误信息统一处理,异常会在RxObserver的onError中catch
 * Created by necer on 2017/6/29.
 */

public class RxFunction<T> implements Function<HttpResult<T>, T> {
    @Override
    public T apply(HttpResult<T> httpResult) throws Exception {
        if (httpResult.isSuccess()) {
            T data = httpResult.data;
            if (data == null) {
                throw new ApiException(httpResult.code, "加载数据失败！");
            } else {
                return httpResult.data;
            }
        } else {
            throw new ApiException(httpResult.code, httpResult.message);
        }
    }

}