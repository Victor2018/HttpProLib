package com.victor.http.model;

import com.victor.http.presenter.OnHttpListener;

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HttpModel.java
 * Author: Victor
 * Date: 2018/9/6 18:25
 * Description:
 * -----------------------------------------------------------------
 */

public interface HttpModel <H,T> {
    void sendReuqest(String url, H header, T parm, OnHttpListener<T> listener);
}
