package com.victor.http.module;

import com.victor.http.presenter.OnHttpListener;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class OkHttpPostRequest<T> extends OkHttpMethod<T> {

    /**
     * 指定Content-Type类型
     */
    public final static MediaType MEDIA_TYPE_JSON = MediaType.parse(HttpRequest.mJsonBodyContentType + HttpRequest.character);

    /**
     * 请求格式
     */
    private MediaType mMediaType = MEDIA_TYPE_JSON;

    /**
     * 请求体
     */
    private RequestBody mRequestBody;

    public OkHttpPostRequest(String url, OkHttpClient okHttpClient, HashMap<String,String> headers) {
        super(url,okHttpClient,headers);
    }

    public OkHttpPostRequest(String url, HashMap<String,String> headers, String parm, String bodyContentType, Class<T> clazz, OkHttpClient okHttpClient, OnHttpListener<T> listener) {
        this(url,okHttpClient,headers);
        mParm = parm;
        mMediaType = MediaType.parse(bodyContentType + HttpRequest.character);
        mClass = clazz;
        requestUrl = url;
        mListener = listener;
    }

    @Override
    protected Request toRequest() {
        mRequestBody = getRequestBody(mMediaType, mParm);
        return getRequestBuilder().url(url).post(mRequestBody).build();
    }

    /**
     * 构造请求体
     *
     * @param type
     * @param content
     * @return
     */
    private RequestBody getRequestBody(MediaType type, String content) {
        if (mRequestBody == null) {
            mRequestBody = RequestBody.create(type, content);
        }
        return mRequestBody;
    }
}
