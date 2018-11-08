package com.victor.http.inject;

import com.victor.http.annotation.HttpParms;
import com.victor.http.module.HttpRequest;
import com.victor.http.module.OkHttpRequest;
import com.victor.http.module.VolleyRequest;

import java.lang.reflect.Method;

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HttpInject.java
 * Author: Victor
 * Date: 2018/9/6 18:25
 * Description: http 注解解析
 * -----------------------------------------------------------------
 */

public class HttpInject {
    private static String TAG = "HttpInject";
    /**
     * 解析注解 http request method
     */
    public static void inject(Object obj) {
        Class cls = obj.getClass();
        Method[] methods = cls.getDeclaredMethods();

        for (Method method : methods) {
            try {
                HttpParms httpParms = method.getAnnotation(HttpParms.class);
                if (httpParms != null) {
                    if (VolleyRequest.getInstance() != null) {
                        VolleyRequest.getInstance().setRequestMethod(httpParms.method());
                        VolleyRequest.getInstance().setBodyContentType(httpParms.bodyContentType());
                        VolleyRequest.getInstance().setResponseCls(httpParms.responseCls());
                    }

                    OkHttpRequest.getInstance().setRequestMethod(httpParms.method());
                    OkHttpRequest.getInstance().setBodyContentType(httpParms.bodyContentType());
                    OkHttpRequest.getInstance().setResponseCls(httpParms.responseCls());

                    HttpRequest.getInstance().setRequestMethod(httpParms.method());
                    HttpRequest.getInstance().setBodyContentType(httpParms.bodyContentType());
                    HttpRequest.getInstance().setResponseCls(httpParms.responseCls());
                    HttpRequest.getInstance().setHttpFramework(httpParms.httpFramework());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
