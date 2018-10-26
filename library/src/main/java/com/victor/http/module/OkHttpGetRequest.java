package com.victor.http.module;

import com.victor.http.interfaces.OkHttpListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkHttpGetRequest<T> extends OkHttpMethod<T> {


    public OkHttpGetRequest(String url , OkHttpClient okHttpClient,HashMap<String,String> headers) {
        super(url, okHttpClient,headers);
    }

    public OkHttpGetRequest(String url,HashMap<String,String> headers, String parm, Class<T> clazz, OkHttpClient okHttpClient, OkHttpListener<T> listener) {
        this(url + parm,okHttpClient,headers);
        mParm = parm;
        mClass = clazz;
        requestUrl = url;
        mListener = listener;
    }

    @Override
    protected Request toRequest() {
        return getRequestBuilder().url(requestUrl + mParm).get().build();
    }

    /**
     * 构造请求体
     *
     * @param url
     * @return
     */
    private String getRequestBody(String url, HashMap<String,Object> parms) {
        if (parms == null)
            return url;
        Iterator<Map.Entry<String, Object>> iterator = parms.entrySet().iterator();
        StringBuilder builder = new StringBuilder(url);
        builder.append("?");
        boolean hasNext = iterator.hasNext();
        while (hasNext) {
            Map.Entry<String, Object> entry = iterator.next();
            builder.append(entry.getKey()).append("=").append(entry.getValue());
            if (hasNext = iterator.hasNext()) {
                //这里是=赋值 不是==判断
                builder.append("&");
            }
        }
        return builder.toString();
    }

}
