package com.necer.basic.network;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by necrer on 2018/4/26.
 */

public class Requestt {

    /**
     * 返回解析完成的bean
     * @param observable
     * @param <T>
     * @return
     */
    public static <T> Observable<T> request(Observable<HttpResult<T>> observable) {
        return observable.map(new RxFunction<T>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 返回httpresult 没有data的情况
     * @param observable
     * @param <T>
     * @return
     */
    public static <T> Observable<T> requestHttpResult(Observable<T> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 返回json
     * @param observable
     * @return
     */
    public static Observable<String> requestJson(Observable<String> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
