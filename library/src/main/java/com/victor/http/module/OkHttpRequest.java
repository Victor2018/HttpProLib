package com.victor.http.module;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.victor.http.data.HttpParm;
import com.victor.http.data.UpLoadParm;
import com.victor.http.interfaces.OkHttpListener;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class OkHttpRequest {
    private String TAG = "OkHttpRequest";
    /**
     * 连接超时
     */
    private static final long CONNECT_TIMEOUT_MILLIS = 10;

    /**
     * 读取超时
     */
    private static final long READ_TIMEOUT_MILLIS = 10;

    /**
     * 写入超时
     */
    private static final long WRITE_TIMEOUT_MILLIS = 10;

    private static OkHttpRequest mOkHttpRequest;
    private int requestMethod = HttpRequest.GET;//请求方式 默认get
    private String mBodyContentType = HttpRequest.mDefaultBodyContentType;
    /**
     * 网络请求客户端
     */
    private static OkHttpClient okHttpClient;

    private Class responseCls;//响应数据

    public static OkHttpRequest getInstance() {
        if (mOkHttpRequest == null) {
            synchronized (VolleyRequest.class) {
                if (mOkHttpRequest == null) {
                    mOkHttpRequest = new OkHttpRequest();
                }
            }
        }
        return mOkHttpRequest;
    }

    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            synchronized (OkHttpClient.class) {
                if (okHttpClient == null) {
                    okHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.SECONDS)
                            .readTimeout(READ_TIMEOUT_MILLIS, TimeUnit.SECONDS)
                            .writeTimeout(WRITE_TIMEOUT_MILLIS, TimeUnit.SECONDS)
                            //.addNetworkInterceptor(new HttpUtils.RetryInterceptor())
                            .build();
                }
            }
        }
        return okHttpClient;
    }

    /**
     * 设置请求方式
     */
    public void setRequestMethod (int method) {
        requestMethod = method;
    }

    /**
     * 设置返回数据模型
     * @param cls
     */
    public void setResponseCls (Class cls) {
        responseCls = cls;
    }

    /**
     * 设置请求数据格式
     * @param bodyContentType
     */
    public void setBodyContentType (String bodyContentType) {
        mBodyContentType = bodyContentType;
    }

    public <T> OkHttpMethod<T> sendGetRequest (String url, HashMap<String,String> headers,String parm, Class<T> clazz, OkHttpListener<T> listener) {
        OkHttpGetRequest<T> request = new OkHttpGetRequest<T>(url,headers,parm,clazz,getOkHttpClient(),listener);
        return request;
    }
    public <T> OkHttpMethod<T> sendPostRequest (String url, HashMap<String,String> headers, String parm, String bodyContentType,
                                                Class<T> clazz, OkHttpListener<T> listener) {

        OkHttpPostRequest<T> request = new OkHttpPostRequest<T>(url,headers,parm,bodyContentType,clazz,getOkHttpClient(),listener);
        return request;
    }

    public <T> OkHttpMethod<T> sendRequest(HttpParm httpParm, OkHttpListener<T> listener) {
        Log.e(TAG,"request url = " + httpParm.url);
        Log.e(TAG,"request parm = " + JSON.toJSONString(httpParm));
        Log.e(TAG,"request requestMethod = " + (httpParm.requestMethod == 0 ? "GET" : "POST"));
        OkHttpMethod<T> request = null;
        if (httpParm.requestMethod == Request.Method.POST) {
            request = sendPostRequest(httpParm.url,httpParm.headers,httpParm.jsonParm,
                    httpParm.bodyContentType,httpParm.responseCls,listener);
        } else if (requestMethod == Request.Method.GET) {
            request = sendGetRequest(httpParm.url,httpParm.headers,httpParm.jsonParm,httpParm.responseCls,listener);
        }
        request.sendRequest();
        return request;
    }

    public <T> OkHttpMethod<T> sendMultipartUploadRequest (String url, UpLoadParm upLoadParm, OkHttpListener<T> listener) {
        Log.e(TAG,"request url = " + url);
        Log.e(TAG,"request parm = " + JSON.toJSONString(upLoadParm));
        OkHttpPostImgRequest<T> request = new OkHttpPostImgRequest<T>(url,responseCls,upLoadParm,
                getOkHttpClient(),listener);
        request.sendRequest();
        return request;
    }
}
