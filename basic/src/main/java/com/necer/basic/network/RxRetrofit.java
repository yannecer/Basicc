package com.necer.basic.network;



import com.necer.basic.base.MyLog;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by necer on 2017/6/29.
 */

public class RxRetrofit {

    private static final int READ_TIME_OUT = 30;
    private static final int WRITE_TIME_OUT = 30;
    private static final int CONNECT_TIME_OUT = 30;
    private static Retrofit retrofit;

    public static Retrofit getRetrofit(String baseUrl, final Map<String,String > headers) {
        //开启Log
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                MyLog.d("HTTP::" + message);
            }
        });
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //增加头部信息
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                /*Request build = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("token", token)
                        .addHeader("systemIdentify", "lzjr_lz_lzhc_master")
                        .addHeader("timestamp", System.currentTimeMillis() + "")
                        .build();*/

                Request.Builder builder = chain.request().newBuilder();
                if (headers != null) {
                    for (String key : headers.keySet()) {
                        String value = headers.get(key);
                        builder.addHeader(key, value);
                    }
                }

                Request build = builder.build();
                return chain.proceed(build);
            }
        };
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(logInterceptor)
                .addInterceptor(headerInterceptor)
                .addInterceptor(new ParameterInterceptor(true))
                .build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();

        return retrofit;
    }


}
