package com.duan.android.base;

import android.app.Application;

/**
 * <pre>
 * author : Duan
 * time : 2019/10/12
 * desc :
 * version: 2.2.0
 * </pre>
 */
public class BaseApplication extends Application{

    // OOM won't happen.(跟整个application 关联，在任何地方引用都安全)
    public static Application sApplication;
    public static boolean sDebug; // Debug时打印日志，否则关掉。

    // 必须是 Application 的变量（每一个Module 都有一个debug的开关）
    public void setDebug(boolean isDebug){
        sDebug = isDebug;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }
}
