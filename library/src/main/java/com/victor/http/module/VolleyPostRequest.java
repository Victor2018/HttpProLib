package com.victor.http.module;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: VolleyPostRequest.java
 * Author: Victor
 * Date: 2018/9/6 18:25
 * Description:
 * -----------------------------------------------------------------
 */
public class VolleyPostRequest<T> extends JsonRequest<T> {
    private String TAG = "VolleyPostRequest";
    private Class<T> mClass;
    private String requestUrl;
    private HashMap<String,String> mHeaders;
    private String mBodyContentType = "application/x-www-form-urlencoded; charset=";

    public VolleyPostRequest(int method, String url, String requestBody, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
//        setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public VolleyPostRequest(String url, HashMap<String,String> headers, String requestBody, String bodyContentType, Class<T> clazz, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        this(Method.POST,url,requestBody,listener,errorListener);
        mClass = clazz;
        requestUrl = url;
        mHeaders = headers;
        mBodyContentType = bodyContentType;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            Log.e(TAG,"HttpHeaderParser.parseCharset(response.headers) = " + HttpHeaderParser.parseCharset(response.headers));
//            String responseData = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            String responseData = new String(response.data, "utf-8");
            Log.e(TAG,"repsonse url = " + requestUrl);
            Log.e(TAG,"responseData = " + responseData);
            if (mClass.toString().contains("String")) {
                return (Response<T>) Response.success(responseData,
                        HttpHeaderParser.parseCacheHeaders(response));
            }
            return (Response<T>) Response.success(parseObject(responseData,mClass),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Log.e(TAG,"mHeaders = " + mHeaders);
        if (mHeaders != null) {
            mHeaders.put("User-agent", HttpRequest.userAgent);
        }
        return mHeaders == null ? super.getHeaders() : mHeaders;
    }

    @Override
    public String getBodyContentType() {
//        return "application/x-www-form-urlencoded; charset=" + getParamsEncoding();
        Log.e(TAG,"mBodyContentType = " + mBodyContentType);
        return mBodyContentType + getParamsEncoding();
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
