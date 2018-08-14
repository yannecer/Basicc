package com.necer.basic.network;


import android.app.Activity;
import android.content.Context;
import android.os.ParcelUuid;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.necer.basic.base.BaseView;
import com.necer.basic.base.MyLog;
import com.necer.basic.enentbus.Event401;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by necer on 2017/6/28.
 */

public abstract class RxObserver<T> implements Observer<T> {

    private int mWhichRequest;
    private BaseView mBaseView;
    private Context mContext;
    private boolean isShowDialog = true;

    public RxObserver(BaseView baseView) {
        this(baseView, 0);
    }

    public RxObserver(BaseView baseView, boolean isShowDialog) {
        this(baseView, 0);
        this.isShowDialog = isShowDialog;
    }

    public RxObserver(BaseView baseView, int whichRequest, boolean isShowDialog) {
        this(baseView, whichRequest);
        this.isShowDialog = isShowDialog;
    }

    public RxObserver(BaseView baseView, int whichRequest) {
        this.mBaseView = baseView;
        this.mWhichRequest = whichRequest;

        if (mBaseView instanceof Activity) {
            mContext = (Context) mBaseView;
        } else if (mBaseView instanceof Fragment) {
            mContext = ((Fragment) mBaseView).getContext();
        }


    }


    @Override
    public final void onSubscribe(Disposable d) {
        RxManager.getInstance().add(mBaseView.getTag(), d);
        if (isShowDialog) mBaseView.onStartLoading(mWhichRequest);
    }

    @Override
    public final void onNext(T value) {
        onSuccess(mWhichRequest, value);
    }

    @Override
    public final void onError(Throwable e) {
        if (isShowDialog) mBaseView.onEndLoading(mWhichRequest);

        String errorMessage = getErrorMessage(e);
        mBaseView.onLoadingError(errorMessage);
        onError(mWhichRequest, e, errorMessage);

    }

    @Override
    public final void onComplete() {
        mBaseView.onLoadingSuccess();
        if (isShowDialog) mBaseView.onEndLoading(mWhichRequest);
    }

    public abstract void onSuccess(int whichRequest, T t);

    public void onError(int whichRequest, Throwable e, String errorMessage) {
        if (mContext != null) {
            Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    private String getErrorMessage(Throwable e) {

        String error = "";
        if (e instanceof ApiException) {
            error = ((ApiException) e).getMessage();
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            switch (httpException.code()) {
                case 401:
                    //用户权限不够，重新登录
                    EventBus.getDefault().post(new Event401());
                    error = "请重新登录";
                    break;
                default:
                    error = "错误代码：" + httpException.code();
                    break;
            }
        } else if (e instanceof JsonParseException || e instanceof JSONException) {
            error = "解析错误！";
        } else if (e instanceof ConnectException) {
            error = "连接失败！";
        } else if (e instanceof SocketTimeoutException) {
            error = "连接超时!";
        } else {
            error = e.toString();
        }

        return error;
    }
}
