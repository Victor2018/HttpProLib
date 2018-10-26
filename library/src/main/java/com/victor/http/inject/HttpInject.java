package com.victor.http.inject;

import android.util.Log;

import com.victor.http.annotation.HttpParms;
import com.victor.http.module.HttpRequest;
import com.victor.http.module.OkHttpRequest;
import com.victor.http.module.VolleyRequest;

import java.lang.reflect.Method;

/**
 * http 注解解析
 * Created by victor on 2017/9/13 0013.
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
