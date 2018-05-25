package com.necer.basic.base;

/**
 * Created by 闫彬彬 on 2017/10/11.
 * QQ:619008099
 */

public interface BaseView {

    void onStartLoading(int whichRequest);

    void onEndLoading(int whichRequest);

    void onLoadingSuccess();

    void onLoadingError(String error);

    String getTag();
}
