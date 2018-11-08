package com.victor.presenter;import com.android.volley.Request;import com.victor.data.LoginReq;import com.victor.data.UploadReq;import com.victor.http.annotation.HttpParms;import com.victor.http.inject.HttpInject;import com.victor.http.module.HttpRequest;import com.victor.http.module.VolleyRequest;import com.victor.http.presenter.impl.BasePresenterImpl;import com.victor.http.presenter.impl.BaseUploadPresenterImpl;import com.victor.view.LoginView;import com.victor.view.UploadView;/* * ----------------------------------------------------------------- * Copyright (C) 2018-2028, by Victor, All rights reserved. * ----------------------------------------------------------------- * File: UploadPresenterImpl.java * Author: Victor * Date: 2018/9/6 18:25 * Description: * ----------------------------------------------------------------- */public class UploadPresenterImpl<H,T> extends BaseUploadPresenterImpl<H,T> {	/*Presenter作为中间层，持有View和Model的引用*/	private UploadView uploadView;	public UploadPresenterImpl(UploadView uploadView) {		this.uploadView = uploadView;	}	@Override	public void onSuccess(T data) {		if (uploadView == null) return;		if (data == null) {			uploadView.OnUpload(null,"no data response");		} else {			uploadView.OnUpload(data,"");		}	}	@Override	public void onError(String error) {		if (uploadView == null) return;		uploadView.OnUpload(null,error);	}	@Override	public void detachView() {		uploadView = null;	}	@HttpParms(method = Request.Method.POST,bodyContentType = HttpRequest.mDefaultBodyContentType,			responseCls = UploadReq.class,httpFramework = HttpRequest.OKHTTP_FRAMEWORK)	@Override	public void sendRequest(String url,H header,T parm) {		HttpInject.inject(this);		super.sendRequest(url,header,parm);	}}