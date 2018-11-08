package com.victor.http.data;

import com.victor.http.module.HttpRequest;

import java.util.HashMap;
import java.util.List;

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HttpParm.java
 * Author: Victor
 * Date: 2018/9/6 18:25
 * Description:
 * -----------------------------------------------------------------
 */
public class HttpParm <T> {
    public int requestMethod = Request.GET;
    public int httpFramework = HttpRequest.OKHTTP_FRAMEWORK;
    public String bodyContentType = HttpRequest.mDefaultBodyContentType;
    public Class responseCls;//响应数据
    public String url;
    public HashMap<String,String> headers;
    public String jsonParm;
    public List<String> fileNames;
    public int msg;//jsoup 使用
}
