package com.victor.http.presenter;

/**
 * Created by victor on 2017/9/26.
 */

public interface HttpPresenter<H,T> {
    void sendRequest(String url, H header, T parm);
}
