package com.victor.http.inject;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.victor.http.annotation.BindView;
import com.victor.http.annotation.OnClick;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ViewInject.java
 * Author: Victor
 * Date: 2018/9/6 18:25
 * Description:
 * -----------------------------------------------------------------
 */

public class ViewInject {
    private static String TAG = "ViewInject";

    /**
     * 解析注解view id
     */
    public static void injectView(Activity activity) {
        Class<?> clazz = activity.getClass();
        Field[] fields = clazz.getDeclaredFields();//获得声明的成员变量
        for (Field field : fields) {
            //判断是否有注解
            try {
                if (field.getAnnotations() != null) {
                    if (field.isAnnotationPresent(BindView.class)) {//如果属于这个注解
                        //为这个控件设置属性
                        field.setAccessible(true);//允许修改反射属性
                        BindView inject = field.getAnnotation(BindView.class);
                        field.set(activity, activity.findViewById(inject.value()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "not found view id!");
            }
        }
        injectEvent(activity);
    }

    private static void injectEvent(final Activity activity) {
        Class cls = activity.getClass();
        Method[] methods = cls.getDeclaredMethods();
        for (final Method m : methods) {
            OnClick click = m.getAnnotation(OnClick.class);//通过反射api获取方法上面的注解
            if (click != null) {
                if (click.value() == -1) return;
                View view = activity.findViewById(click.value());//通过注解的值获取View控件
                if (view == null) return;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            m.invoke(activity);//通过反射来调用被注解修饰的方法
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

}
