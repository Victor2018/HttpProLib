package com.victor.presenter;import com.android.volley.Request;import com.victor.data.LoginReq;import com.victor.http.annotation.HttpParms;import com.victor.http.inject.HttpInject;import com.victor.http.module.HttpRequest;import com.victor.http.module.VolleyRequest;import com.victor.http.presenter.impl.BasePresenterImpl;import com.victor.view.HeaderView;/** * Created by victor on 2017/2/8. * 登陆 Prestener实现 */public class HeaderPresenterImpl<H,T> extends BasePresenterImpl<H,T> {	/*Presenter作为中间层，持有View和Model的引用*/	private HeaderView headerView;	public HeaderPresenterImpl(HeaderView headerView) {		this.headerView = headerView;	}	@Override	public void onSuccess(T data) {		if (headerView == null) return;		if (data == null) {			headerView.OnHeader(null,"no data response");		} else {			headerView.OnHeader(data,"");		}	}	@Override	public void onError(String error) {		if (headerView == null) return;		headerView.OnHeader(null,error);	}	@Override	public void detachView() {		headerView = null;	}	@HttpParms(method = Request.Method.POST,bodyContentType = HttpRequest.mJsonBodyContentType,			responseCls = LoginReq.class,httpFramework = HttpRequest.OKHTTP_FRAMEWORK)	@Override	public void sendRequest(String url,H header,T parm) {		HttpInject.inject(this);		super.sendRequest(url,header,parm);	}}