package com.duan.newsmvvm.application;

import com.billy.cc.core.component.CC;
import com.duan.android.base.BaseApplication;
import com.duan.android.base.loadsir.CustomCallback;
import com.duan.android.base.loadsir.EmptyCallback;
import com.duan.android.base.loadsir.ErrorCallback;
import com.duan.android.base.loadsir.LoadingCallback;
import com.duan.android.base.loadsir.TimeoutCallback;
import com.duan.android.base.preference.PreferencesUtil;
import com.duan.android.base.utils.Logger;
import com.duan.android.network.ApiBase;
import com.duan.newsmvvm.BuildConfig;// 必须是 Application 的变量
import com.kingja.loadsir.core.LoadSir;

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
        Logger.init(this,BuildConfig.DEBUG);
        setDebug(BuildConfig.DEBUG);// 必须是 Application 的变量（每一个Module 都有一个debug的开关）

        PreferencesUtil.init(this);
        ApiBase.setNetworkRequestInfo(new NetworkRequestInfo()); // 初始化网络层

        // 在 NewsApplication中初始化 lottie - 以资源文件方式导进来的动画库
        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())//添加各种状态页
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .addCallback(new CustomCallback())
                .setDefaultCallback(LoadingCallback.class)//设置默认状态页
                .commit();

        // CC 接入
        CC.enableDebug(BuildConfig.DEBUG);
        CC.enableVerboseLog(BuildConfig.DEBUG);
        CC.enableRemoteCC(BuildConfig.DEBUG);

    }

}
