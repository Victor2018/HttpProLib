package com.victor.http.model.impl;

import android.text.TextUtils;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.victor.http.data.UpLoadParm;
import com.victor.http.model.HttpModel;
import com.victor.http.module.HttpRequest;
import com.victor.http.presenter.OnHttpListener;

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HttpUploadModelImpl.java
 * Author: Victor
 * Date: 2018/9/6 18:25
 * Description:
 * -----------------------------------------------------------------
 */

public class HttpUploadModelImpl<H,T> implements HttpModel<H,T> {
    private String TAG = "HttpModelImpl";

    @Override
    public void sendReuqest(String url, H header,T parm, final OnHttpListener<T> listener) {
        HttpRequest.getInstance().sendMultipartUploadRequest(url, (UpLoadParm) (parm), listener, new Response.Listener<T>() {
            @Override
            public void onResponse(T response) {
                if (response != null) {
                    listener.onSuccess(response);
                } else {
                    listener.onError("no data response!");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String msg = error.getMessage();
                if (TextUtils.isEmpty(msg)) {
                    msg = "no data response!";
                }
                listener.onError(msg);
            }
        });
    }
}
