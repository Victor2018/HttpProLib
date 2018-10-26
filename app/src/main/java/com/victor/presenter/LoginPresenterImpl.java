package com.victor.presenter;import com.android.volley.Request;import com.victor.http.annotation.HttpParms;import com.victor.http.inject.HttpInject;import com.victor.http.module.HttpRequest;import com.victor.http.module.VolleyRequest;import com.victor.http.presenter.impl.BasePresenterImpl;import com.victor.data.LoginReq;import com.victor.view.LoginView;/** * Created by victor on 2017/2/8. * 登陆 Prestener实现 */public class LoginPresenterImpl<H,T> extends BasePresenterImpl<H,T> {	/*Presenter作为中间层，持有View和Model的引用*/	private LoginView loginView;	public LoginPresenterImpl(LoginView loginView) {		this.loginView = loginView;	}	@Override	public void onSuccess(T data) {		if (loginView == null) return;		if (data == null) {			loginView.OnLoginInfo(null,"no data response");		} else {			loginView.OnLoginInfo(data,"");		}	}	@Override	public void onError(String error) {		if (loginView == null) return;		loginView.OnLoginInfo(null,error);	}	@Override	public void detachView() {		loginView = null;	}	@HttpParms(method = Request.Method.POST,bodyContentType = HttpRequest.mDefaultBodyContentType,			responseCls = LoginReq.class,httpFramework = HttpRequest.OKHTTP_FRAMEWORK)	@Override	public void sendRequest(String url,H header,T parm) {		HttpInject.inject(this);		super.sendRequest(url,header,parm);	}}