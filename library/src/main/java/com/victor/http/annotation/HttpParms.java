package com.victor.http.annotation;

import com.victor.http.module.HttpRequest;
import com.victor.http.module.VolleyRequest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2017/9/13 0013.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpParms {
    int method() default -1;
    String url() default "";
    String bodyContentType() default HttpRequest.mDefaultBodyContentType;
    Class responseCls();
    int httpFramework() default HttpRequest.OKHTTP_FRAMEWORK;
}
