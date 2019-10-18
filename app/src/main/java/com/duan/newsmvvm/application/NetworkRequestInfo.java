package com.duan.newsmvvm.application;

import com.duan.android.network.INetworkRequestInfo;
import com.duan.newsmvvm.BuildConfig;

import java.util.HashMap;

/**
 * <pre>
 * author : Duan
 * time : 2019/10/14
 * desc :
 * version: 1.0.0
 * </pre>
 */
public class NetworkRequestInfo implements INetworkRequestInfo {

    HashMap<String, String> headerMap = new HashMap<>();

    public NetworkRequestInfo(){
        headerMap.put("os", "android");
        headerMap.put("versionName", BuildConfig.VERSION_NAME);
        headerMap.put("versionCode", String.valueOf(BuildConfig.VERSION_CODE));
        headerMap.put("applicationId", BuildConfig.APPLICATION_ID);
    }

    @Override
    public HashMap<String, String> getRequestHeaderMap() {
        return headerMap;
    }

    @Override
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }
}
