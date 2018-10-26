package com.victor.app;

import android.app.Application;

import com.victor.http.module.VolleyRequest;

/**
 * Created by victor on 2017/9/12 0012.
 */

public class HttpApplication extends Application {
    private static HttpApplication instance;
    public HttpApplication() {
        instance = this;
    }

    public static Application getContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化
        VolleyRequest.buildRequestQueue(this);//不是用volley这里可以不初始化volley
    }
}
