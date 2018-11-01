package com.victor.http.module;

import android.app.WallpaperManager;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.victor.http.data.HttpParm;
import com.victor.http.presenter.OnHttpListener;
import com.victor.http.util.HttpUtil;
import com.victor.http.util.MainHandler;

import java.util.HashMap;

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by longtv, All rights reserved.
 * -----------------------------------------------------------------
 * File: JsoupRequest.java
 * Author: Victor
 * Date: 2018/10/26 17:16
 * Description:
 * -----------------------------------------------------------------
 */
public class JsoupRequest {
    public String TAG = "JsoupRequest";
    public static final int JSOUP_REQUEST = 0x1001;
    private Handler mRequestHandler;
    private HandlerThread mRequestHandlerThread;
    private static JsoupRequest mJsoupRequest;
    private OnHttpListener mOnHttpListener;

    private JsoupRequest () {
        startRequestTask ();
    }
    public static JsoupRequest getInstance () {
        if (mJsoupRequest == null) {
            synchronized (VolleyRequest.class) {
                if (mJsoupRequest == null) {
                    mJsoupRequest = new JsoupRequest();
                }
            }
        }
        return mJsoupRequest;
    }

    private void startRequestTask (){
        mRequestHandlerThread = new HandlerThread("HttpRequestTask");
        mRequestHandlerThread.start();
        mRequestHandler = new Handler(mRequestHandlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case JSOUP_REQUEST:
                        String des = "success";
                        HashMap<Integer,Object> jsoupMap = (HashMap<Integer, Object>) msg.obj;
                        HttpParm httpParm = (HttpParm) jsoupMap.get(JSOUP_REQUEST);
                        if (httpParm == null) {
                            des = "httpParm == null";
                            onResponse(null,des);
                            return;
                        }
                        String result = HttpUtil.jsoup(httpParm.url);
                        if (TextUtils.isEmpty(result)) {
                            des = "result == null";
                            onResponse(null,des);
                            return;
                        }
                        onResponse(result,des);
                        break;
                }
            }
        };
    }

    public void sendRequestWithParms (int Msg,Object requestData) {
        HashMap<Integer, Object> requestMap = new HashMap<Integer, Object>();
        requestMap.put(Msg, requestData);
        Message msg = mRequestHandler.obtainMessage(Msg,requestMap);
        mRequestHandler.sendMessage(msg);
    }

    public void sendRequest (HttpParm httpParams, OnHttpListener listener) {
        mOnHttpListener = listener;
        HashMap<Integer, Object> requestMap = new HashMap<Integer, Object>();
        requestMap.put(JSOUP_REQUEST, httpParams);
        Message msg = mRequestHandler.obtainMessage(JSOUP_REQUEST,requestMap);
        mRequestHandler.sendMessage(msg);
    }

    private void onResponse (final String result,final String msg) {
        MainHandler.runMainThread(new Runnable() {
            @Override
            public void run() {
                if (mOnHttpListener != null) {
                    if (!TextUtils.isEmpty(result)) {
                        mOnHttpListener.onSuccess(result);
                    } else {
                        mOnHttpListener.onError(msg);
                    }
                }
            }
        });
    }

    public void onDestroy (){
        if (mRequestHandlerThread != null) {
            mRequestHandlerThread.quit();
            mRequestHandlerThread = null;
        }
    }
}
