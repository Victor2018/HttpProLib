package com.victor.http.module;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.victor.http.util.MainHandler;
import com.victor.http.interfaces.OkHttpListener;

import java.io.IOException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public abstract class OkHttpMethod<T> implements Callback{

    protected static final String TAG = "OkHttpMethod";

    protected Class<T> mClass;
    protected String requestUrl;
    protected OkHttpListener<T> mListener;
    protected String mParm;

    /**
     * 网络请求客户端
     */
    protected OkHttpClient okHttpClient;

    /**
     * 请求的地址
     */
    protected String url;

    protected HashMap<String,String> headers;

    /**
     * 建造者
     */
    protected Request.Builder mBuilder;

    public OkHttpMethod(String url , OkHttpClient okHttpClient, HashMap<String,String> headers) {
        this.url = url;
        this.okHttpClient = okHttpClient;
        this.headers = headers;
        addHeaderParm();
    }

    private void addHeaderParm () {
        if (headers == null) return;
        Iterator<Map.Entry<String, String>> iterator = headers.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            getRequestBuilder().addHeader(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Tag标签
     *
     * @param obj
     * @return
     */
    public OkHttpMethod tag(Object obj) {
        getRequestBuilder().tag(obj);
        return this;
    }

    public OkHttpMethod headers(String key, String value) {
        getRequestBuilder().addHeader(key, value);
        return this;
    }

    /**
     * 构造请求Builder
     *
     * @return
     */
    protected Request.Builder getRequestBuilder() {
        if (mBuilder == null) {
            mBuilder = new Request.Builder();
        }
        return mBuilder;
    }

    /**
     * 同步请求方法
     *
     * @return
     */
    public Response execute() {
        try {
            return okHttpClient.newCall(toRequest()).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 异步请求方法
     */
    public void sendRequest() {
        if (mListener != null) {
            okHttpClient.newCall(toRequest()).enqueue(this);
        }
    }

    /**
     * 构造请求
     *
     * @return
     */
    protected abstract Request toRequest();


    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response != null) {
            if (response.code() == 200) {
                //请求成功
                final HttpUrl httpUrl = call.request().url();
                try {
                    final String responseData = response.body().string();
                    //输出调试信息
                    Log.e(TAG,"repsonse url = " + httpUrl);
                    Log.e(TAG,"responseData = " + responseData);
                    onResult(responseData,"");

                } catch (UnsupportedCharsetException e) {
                    e.printStackTrace();
                    onResult(null,e.getMessage());
                }
            } else {
                //Http请求错误
                onResult(null,"response.code() = "+ response.code());
            }
        } else {
            //请求响应为空
            onResult(null,"server no data response");
        }
    }

    @Override
    public void onFailure(Call call, IOException e) {
        Log.e(TAG,"onFailure()......" + e.getMessage());
        onResult(null,e.getMessage());
    }

    private void onResult (final String responseData ,final String msg) {
        MainHandler.runMainThread(new Runnable() {
            @Override
            public void run() {
                if (mListener != null) {
                    T result = parseObject(responseData,mClass);
                    if (mClass.toString().contains("String")) {
                        mListener.onResponse((T) responseData,msg);
                    } else {
                        mListener.onResponse(result, msg);
                    }
                }
            }
        });
    }

    public static <T> T parseObject(String text, Class<T> clazz) {
        try {
            T result = JSON.parseObject(text, clazz);
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
