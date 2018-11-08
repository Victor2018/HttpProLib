package com.victor.http.presenter.impl;

import com.victor.http.model.HttpModel;
import com.victor.http.model.impl.HttpModelImpl;
import com.victor.http.model.impl.HttpUploadModelImpl;
import com.victor.http.presenter.HttpPresenter;
import com.victor.http.presenter.OnHttpListener;

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: BaseUploadPresenterImpl.java
 * Author: Victor
 * Date: 2018/9/6 18:25
 * Description:
 * -----------------------------------------------------------------
 */

public abstract class BaseUploadPresenterImpl<H,T> implements HttpPresenter<H,T>,OnHttpListener<T> {
    private HttpModel httpModelImpl;
    public abstract void detachView();

    public BaseUploadPresenterImpl() {
        httpModelImpl = new HttpUploadModelImpl();
    }

    @Override
    public void sendRequest(String url, H header,T parm) {
        httpModelImpl.sendReuqest(url, header,parm,this);
    }
}
