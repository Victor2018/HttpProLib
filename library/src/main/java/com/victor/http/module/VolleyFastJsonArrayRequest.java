package com.victor.http.module;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @param <T>
 * Created by victor on 2017/2/8.
 */
public class VolleyFastJsonArrayRequest<T> extends Request<T> {
    private String TAG = "VolleyFastJsonArrayRequest";
    private final Listener<List<T>> mListener;
    private Class<T> mClass;
    private String url;

    public VolleyFastJsonArrayRequest(int method, String url, Class<T> clazz, Listener<List<T>> listener,
                                      ErrorListener errorListener) {
        super(method, url, errorListener);
        this.url = url;
        mClass = clazz;
        mListener = listener;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            Log.e(TAG,"HttpHeaderParser.parseCharset(response.headers) = " + HttpHeaderParser.parseCharset(response.headers));
            String responseData = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Log.e(TAG,"url = " + url);
            Log.e(TAG,"responseData = " + responseData);
            if (mClass.toString().contains("String")) {
                return (Response<T>) Response.success(responseData,
                        HttpHeaderParser.parseCacheHeaders(response));
            }
            return (Response<T>) Response.success(JSON.parseArray(responseData, mClass),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse((List<T>) response);
    }


}