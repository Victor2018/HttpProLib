package com.victor.http.model;

import com.victor.http.presenter.OnHttpListener;

/**
 * Created by victor on 2017/9/26.
 */

public interface HttpModel <H,T> {
    void sendReuqest(String url, H header, T parm, OnHttpListener<T> listener);
}
