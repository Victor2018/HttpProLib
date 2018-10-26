package com.victor.http.model.impl;

import android.text.TextUtils;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.victor.http.model.HttpModel;
import com.victor.http.module.HttpRequest;
import com.victor.http.interfaces.OkHttpListener;
import com.victor.http.presenter.OnHttpListener;

import java.util.HashMap;

/**
 * Created by victor on 2017/9/26.
 */

public class HttpModelImpl<H,T> implements HttpModel<H,T> {
    private String TAG = "HttpModelImpl";

    @Override
    public void sendReuqest(String url,H header,T parm, final OnHttpListener<T> listener) {
           /* OkHttpRequest.getInstance().sendRequest(url, (HashMap<String, String>) header, parm == null ? "" : parm.toString(), new OkHttpListener<T>() {

                @Override
                public void onResponse(T response,String msg) {
                    if (response != null) {
                        listener.onSuccess(response);
                    } else {
                        listener.onError(msg);
                    }
                }
            });*/
        /*VolleyRequest.getInstance().sendRequest(url, (HashMap<String, String>) header,parm == null ? "": parm.toString(), new Response.Listener<T>() {
            @Override
            public void onResponse(T info) {
                if (info != null) {
                    listener.onSuccess(info);
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
        });*/

        HttpRequest.getInstance().sendRequest(url, (HashMap<String, String>) header, parm == null ? "" : parm.toString(), new OkHttpListener<T>() {

            @Override
            public void onResponse(T response, String msg) {
                if (response != null) {
                    listener.onSuccess(response);
                } else {
                    listener.onError(msg);
                }
            }
        }, new Response.Listener<T>() {
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
