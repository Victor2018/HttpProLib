package com.victor.http.presenter;


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: OnHttpListener.java
 * Author: Victor
 * Date: 2018/9/6 18:25
 * Description:
 * -----------------------------------------------------------------
 */

public interface OnHttpListener<T> {
    void onSuccess(T data);
    void onError(String error);
}
