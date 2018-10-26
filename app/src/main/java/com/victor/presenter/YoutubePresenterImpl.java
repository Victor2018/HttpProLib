package com.victor.presenter;import com.android.volley.Request;import com.victor.http.annotation.HttpParms;import com.victor.http.inject.HttpInject;import com.victor.http.module.HttpRequest;import com.victor.http.module.VolleyRequest;import com.victor.http.presenter.impl.BasePresenterImpl;import com.victor.view.YoutubeView;/** * Created by victor on 2017/2/8. * 获取所有直播节目 Prestener实现 */public class YoutubePresenterImpl<H,T> extends BasePresenterImpl<H,T> {	/*Presenter作为中间层，持有View和Model的引用*/	private YoutubeView youtubeView;	public YoutubePresenterImpl(YoutubeView youtubeView) {		this.youtubeView = youtubeView;	}	@Override	public void onSuccess(T data) {		if (youtubeView == null) return;		if (data == null) {			youtubeView.OnYoutube(null,"no data response");		} else {			youtubeView.OnYoutube(data,"");		}	}	@Override	public void onError(String error) {		if (youtubeView == null) return;		youtubeView.OnYoutube(null,error);	}	@Override	public void detachView() {		youtubeView = null;	}	@HttpParms(method = Request.Method.GET,bodyContentType = HttpRequest.mDefaultBodyContentType,responseCls = String.class,httpFramework = HttpRequest.OKHTTP_FRAMEWORK )	@Override	public void sendRequest(String url, H header, T parm) {		HttpInject.inject(this);		super.sendRequest(url,header,parm);	}}