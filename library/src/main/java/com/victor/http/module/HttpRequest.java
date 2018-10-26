package com.victor.http.module;

import android.util.Log;

import com.android.volley.Response;
import com.victor.http.data.HttpParm;
import com.victor.http.data.UpLoadParm;
import com.victor.http.interfaces.OkHttpListener;

import java.util.HashMap;

public class HttpRequest {
    private String TAG = "HttpRequest";
    public static final int OKHTTP_FRAMEWORK = 0;
    public static final int VOLLEY_FRAMEWORK = 1;
    private int httpFramework = OKHTTP_FRAMEWORK;

    public static final int GET               = 0;
    public static final int POST              = 1;
    private int requestMethod = GET;

    public final static String mDefaultBodyContentType = "application/x-www-form-urlencoded; charset=";
    public final static String mJsonBodyContentType = "application/json; charset=";
    private String mBodyContentType = mDefaultBodyContentType;
    public final static String userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36";

    public final static String character = "utf-8";

    public Class responseCls;//响应数据

    private static HttpRequest mHttpRequest;

    public static HttpRequest getInstance () {
        if (mHttpRequest == null) {
            synchronized (HttpRequest.class) {
                if (mHttpRequest == null) {
                    mHttpRequest = new HttpRequest();
                }
            }
        }
        return mHttpRequest;
    }

    public void setHttpFramework(int httpFramework) {
        this.httpFramework = httpFramework;
    }
    /**
     * 设置请求方式
     */
    public void setRequestMethod (int method) {
        requestMethod = method;
    }

    /**
     * 设置请求数据格式
     * @param bodyContentType
     */
    public void setBodyContentType (String bodyContentType) {
        mBodyContentType = bodyContentType;
    }

    /**
     * 设置返回数据模型
     * @param cls
     */
    public void setResponseCls (Class cls) {
        responseCls = cls;
    }

    public <T> void sendRequest (String url, HashMap<String,String> headers,
                                 String parm,OkHttpListener<T> okHttpListener, Response.Listener<T> listener,
                             Response.ErrorListener errorListener) {
        Log.e(TAG,"sendRequest()......");
        HttpParm httpParams = new HttpParm();
        httpParams.requestMethod = requestMethod;
        httpParams.responseCls = responseCls;
        httpParams.bodyContentType = mBodyContentType;
        httpParams.url = url;
        httpParams.headers = headers;
        httpParams.jsonParm = parm;
        switch (httpFramework) {
            case VOLLEY_FRAMEWORK:
                Log.e(TAG,"sendRequest()......VOLLEY_FRAMEWORK");
                VolleyRequest.getInstance().sendRequest(httpParams,listener,errorListener);
                break;
            case OKHTTP_FRAMEWORK:
                Log.e(TAG,"sendRequest()......OKHTTP_FRAMEWORK");
                OkHttpRequest.getInstance().sendRequest(httpParams,okHttpListener);
                break;
        }
    }

    public <T> void sendMultipartUploadRequest (String url, UpLoadParm parm,OkHttpListener<T> okHttpListener, Response.Listener<T> listener,
                                                Response.ErrorListener errorListener) {
        switch (httpFramework) {
            case VOLLEY_FRAMEWORK:
                Log.e(TAG, "sendMultipartUploadRequest()......VOLLEY_FRAMEWORK");
                VolleyRequest.getInstance().sendMultipartUploadRequest(url, parm, listener, errorListener);
                break;
            case OKHTTP_FRAMEWORK:
                Log.e(TAG, "sendMultipartUploadRequest()......OKHTTP_FRAMEWORK");
                OkHttpRequest.getInstance().sendMultipartUploadRequest(url,parm, okHttpListener);
                break;
        }
    }
}
