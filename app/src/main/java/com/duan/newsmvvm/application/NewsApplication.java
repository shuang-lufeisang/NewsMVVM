package com.duan.newsmvvm.application;

import com.duan.android.base.BaseApplication;
import com.duan.newsmvvm.BuildConfig;// 必须是 Application 的变量

/**
 * <pre>
 * author : Duan
 * time : 2019/10/12
 * desc :
 * version: 2.2.0
 * </pre>
 */
public class NewsApplication extends BaseApplication{

    @Override
    public void onCreate() {
        super.onCreate();
        setDebug(BuildConfig.DEBUG);// 必须是 Application 的变量（每一个Module 都有一个debug的开关）
    }

}
