package com.necer.basic.network;


import com.necer.basic.base.MyLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLDecoder;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by necer on 2017/11/30.
 */

public class ParameterInterceptor implements Interceptor {


    private boolean isJson;//post 参数是否为json

    public ParameterInterceptor(boolean isJson) {
        this.isJson = isJson;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String method = request.method();

        if (isJson && "POST".equals(method) ) {
            RequestBody requestBody = request.body();
            if (requestBody instanceof FormBody) {
                JSONObject result = new JSONObject();
                for (int i = 0; i < ((FormBody) requestBody).size(); i++) {
                    String decode = URLDecoder.decode(((FormBody) requestBody).encodedValue(i), "UTF-8");
                    try {
                        result.put(((FormBody) requestBody).encodedName(i), decode);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                MyLog.d("请求参数：：：" + result.toString());
                RequestBody body = RequestBody.create(MediaType.parse("application/json"), result.toString());
                request = request.newBuilder().post(body).build();
            }
        }
        return chain.proceed(request);
    }
}
