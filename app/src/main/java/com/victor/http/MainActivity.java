package com.victor.http;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.victor.data.PhoneCodeParm;
import com.victor.data.PhoneCodeReq;
import com.victor.data.PhoneCodeRequest;
import com.victor.data.UploadReq;
import com.victor.http.annotation.BindView;
import com.victor.http.annotation.OnClick;
import com.victor.http.data.FormImage;
import com.victor.http.data.UpLoadParm;
import com.victor.http.inject.ViewInject;
import com.victor.http.presenter.HttpPresenter;
import com.victor.data.LoginReq;
import com.victor.data.Weather;
import com.victor.presenter.LoginPresenterImpl;
import com.victor.presenter.HeaderPresenterImpl;
import com.victor.presenter.PhoneCodePresenterImpl;
import com.victor.presenter.UploadPresenterImpl;
import com.victor.presenter.WeatherPresenterImpl;
import com.victor.presenter.YoutubePresenterImpl;
import com.victor.view.LoginView;
import com.victor.view.HeaderView;
import com.victor.view.PhoneCodeView;
import com.victor.view.UploadView;
import com.victor.view.WeatherView;
import com.victor.view.YoutubeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: MainActivity.java
 * Author: Victor
 * Date: 2018/9/6 18:25
 * Description:
 * -----------------------------------------------------------------
 */

public class MainActivity extends AppCompatActivity implements WeatherView<Weather>,LoginView<LoginReq>,
        PhoneCodeView<PhoneCodeReq>,UploadView<UploadReq>,HeaderView<LoginReq>,YoutubeView<String> {
    private String TAG = "MainActivity";

    @BindView(R.id.tv_result)
    private TextView mTvResult;

    private HttpPresenter weatherPresenter;
    private HttpPresenter loginPresenter;
    private HttpPresenter phonePresenter;
    private HttpPresenter uploadFilePresenter;
    private HttpPresenter headerPresenter;
    private HttpPresenter youtubePresenter;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        initData();
    }

    private void initialize () {
        ViewInject.injectView(this);
    }

    private void initData () {
        weatherPresenter = new WeatherPresenterImpl(this); //传入WeatherView
        loginPresenter = new LoginPresenterImpl(this);
        phonePresenter = new PhoneCodePresenterImpl(this);
        uploadFilePresenter = new UploadPresenterImpl(this);
        headerPresenter = new HeaderPresenterImpl(this);
        youtubePresenter = new YoutubePresenterImpl(this);
        loadingDialog = new ProgressDialog(this);
        loadingDialog.setTitle("加载中...");
    }

    private void sendHeaderRequest() {
        try {
            JSONObject data = new JSONObject();
            data.put("mac","F4911E03F20C");
            data.put("menuId",88);

            HashMap<String,String> header = new HashMap<>();
            header.put("X-token","");
            header.put("X-version","2.0.3");
            header.put("X-deviceType","phone");
            header.put("X-mac","525400123456");
            header.put("X-areaCode","");
            String url = "http://poc.sdmc.tv/v1/menu/recommend";

            headerPresenter.sendRequest(url,header,data);
        } catch (com.alibaba.fastjson.JSONException e) {
            e.printStackTrace();
        }

    }

    private void sendYoutubeRequest () {
        String url = String.format("http://www.youtube.com/get_video_info?video_id=%s&asv=3&el=detailpage&hl=en_US", "u-W9oEZoOTY");
        youtubePresenter.sendRequest(url,null,null);
    }

    @OnClick (R.id.btn_go)
    public void onGoBtnClick () {
        loadingDialog.show();
        String parms = String.format("username=%s&password=%s&client_id=%s&client_secret=%s&grant_type=%s",
                "815449243@qq.com","423099","c9aa97bb","fe52528fb1828907756f393e3906d1f1fe3acd80224ff6cdb0834e4ea204f036","password");
//        loginPresenter.sendRequest("https://www.diycode.cc/oauth/token", null,parms);
//        uploadImg();
//        uploadImg2();

//        sendYoutubeRequest();
        sendPhoneCode();
//        sendHeaderRequest();
//        weatherPresenter.sendRequest("http://www.weather.com.cn/data/sk/101280601.html",null,mEtCityNo.getText().toString().trim());
    }

    @Override
    public void OnWeatherInfo(Weather data, String msg) {
        if (isFinishing()) return;
        loadingDialog.dismiss();
        if (data == null) {
            Toast.makeText(getApplicationContext(), "error = " + msg, Toast.LENGTH_SHORT).show();
            return;
        }
        mTvResult.setText(JSON.toJSONString(data));
    }

    @Override
    public void OnLoginInfo(LoginReq data, String msg) {
        if (isFinishing()) return;
        loadingDialog.dismiss();
        if (data == null) {
            Toast.makeText(getApplicationContext(), "error = " + msg, Toast.LENGTH_SHORT).show();
            return;
        }
        mTvResult.setText(JSON.toJSONString(data));
        Toast.makeText(getApplicationContext(), "login success access_token = " + data.access_token, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnPhoneCode(PhoneCodeReq data, String msg) {
        if (isFinishing()) return;
        loadingDialog.dismiss();
        if (data == null) {
            Toast.makeText(getApplicationContext(), "error = " + msg, Toast.LENGTH_SHORT).show();
            return;
        }
        Log.e(TAG,"OnPhoneCode-data.desc = " + data.desc);
        Log.e(TAG,"OnPhoneCode-data.data.first = " + data.data.first);
        Log.e(TAG,"OnPhoneCode-data.errno = " + data.errno);
        mTvResult.setText(JSON.toJSONString(data));
    }

    private void sendPhoneCode () {
        PhoneCodeRequest request = new PhoneCodeRequest();
        request.app = "com.fungo.hotchat";
        request.channel = "360";
        request.model = "Lenovo A7600-m";
        request.nettype = 0;
        request.term = 0;
        request.ts = System.currentTimeMillis();
        request.udid = "aad36635a567008985006ea9741b7823";
        request.uid = 0;
        request.version = 10;
        request.version_name = "1.5.0";

        PhoneCodeParm data = new PhoneCodeParm();
        data.phone = "18813938924";
        data.type = 0;

        request.data = data;

        phonePresenter.sendRequest("http://api.zg.xiaoyoureliao.net/cgi-bin/get_phone_code", null,JSON.toJSONString(request));
    }

    private void uploadImg () {
        HashMap<String,String> headers = new HashMap<>();
        headers.put("token","a78d6ab6b54d3961a911c3fcf495633c");
        headers.put("tokentime","20180615141801");
        headers.put("tokentype","update");
        headers.put("customerid","5");
        headers.put("imgtype","2");
        headers.put("imgname","logo-5.png");
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img_upload);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        List<FormImage> imgs = new ArrayList<>();
        FormImage img = new FormImage(bitmap,"/sdcard/test1.png","image/png","file");
        FormImage img2 = new FormImage(bitmap2,"/sdcard/test1.png","image/png","file");
        imgs.add(img);
        imgs.add(img2);

        UpLoadParm parm = new UpLoadParm();
        parm.headers = headers;
        parm.imgs = imgs;
        uploadFilePresenter.sendRequest("http://120.77.218.62:39003/api/tblcontactsdiy_rest/allof/", null,parm);
    }
    private void uploadImg2 () {
        HashMap<String,String> headers = new HashMap<>();
        headers.put("token","JxRaZezavm3HXM3d9pWnYiqqQC1SJbsU");
        headers.put("tokentime","123456789");
        headers.put("tokentype","insert");
        headers.put("imgname","logo-5.png");
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img_upload);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        List<FormImage> imgs = new ArrayList<>();
        FormImage img = new FormImage(bitmap,"","image/png","file");
        FormImage img2 = new FormImage(bitmap2,"","image/png","file");
        imgs.add(img);
        imgs.add(img2);

        UpLoadParm parm = new UpLoadParm();
        parm.headers = getHttpHeaderParm();
        parm.imgs = imgs;
        uploadFilePresenter.sendRequest("http://cn.ott.demo.long.tv/v1/auth/avatar/upload", null,parm);
    }

    public static HashMap<String,String> getHttpHeaderParm () {

        HashMap<String,String> header = new HashMap<>();
        header.put("X-token","8ed18faa2e56dca409dc0a39b747ed0f");
        header.put("X-version", "1.0.0");
        header.put("X-deviceType","phone");
        header.put("X-mac", "f4911e03f20c");
        header.put("X-areaCode","");//
        return header;
    }

    @Override
    public void OnUpload(UploadReq data, String msg) {
        if (isFinishing()) return;
        loadingDialog.dismiss();
        if (data == null) {
            Toast.makeText(getApplicationContext(), "error = " + msg, Toast.LENGTH_SHORT).show();
            return;
        }
        mTvResult.setText(JSON.toJSONString(data));
        Toast.makeText(getApplicationContext(), "upload success", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void OnHeader(LoginReq data, String msg) {
        if (isFinishing()) return;
        loadingDialog.dismiss();
        mTvResult.setText(JSON.toJSONString(data));
    }

    @Override
    public void OnYoutube(String data, String msg) {
        if (isFinishing()) return;
        loadingDialog.dismiss();
        Log.e(TAG,"OnYoutube-data = " + data);
        mTvResult.setText(JSON.toJSONString(data));
    }
}
