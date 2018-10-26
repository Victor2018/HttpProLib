package com.victor.http.interfaces;

import okhttp3.Callback;

public interface OkHttpListener <T> {
    void onResponse(T response, String msg);
}
