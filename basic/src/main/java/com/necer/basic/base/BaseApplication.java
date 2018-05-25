package com.necer.basic.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.necer.basic.utils.Toast;

public class BaseApplication extends MultiDexApplication {


    public static Application application;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        Toast.init(this);
    }
}
