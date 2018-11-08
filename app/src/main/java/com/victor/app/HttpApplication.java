package com.victor.app;

import android.app.Application;

import com.victor.http.module.VolleyRequest;

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HttpApplication.java
 * Author: Victor
 * Date: 2018/9/6 18:25
 * Description:
 * -----------------------------------------------------------------
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
