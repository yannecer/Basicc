package com.necer.basic.base;

/**
 * Created by 闫彬彬 on 2017/10/11.
 * QQ:619008099
 */

public class BaseModel<T> {

    public T mView;
    public void setVM(T v) {
        this.mView = v;
    }
}
