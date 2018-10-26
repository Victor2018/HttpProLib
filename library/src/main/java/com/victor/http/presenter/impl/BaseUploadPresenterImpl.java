package com.victor.http.presenter.impl;

import com.victor.http.model.HttpModel;
import com.victor.http.model.impl.HttpModelImpl;
import com.victor.http.model.impl.HttpUploadModelImpl;
import com.victor.http.presenter.HttpPresenter;
import com.victor.http.presenter.OnHttpListener;

/**
 * Created by victor on 2017/9/26.
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
