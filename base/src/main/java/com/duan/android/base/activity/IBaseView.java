package com.duan.android.base.activity;

/**
 * <pre>
 * author : Duan
 * time : 2019/10/14
 * desc :  IBaseView
 *
 * version: 1.0.0
 * </pre>
 */
public interface IBaseView {
    void showContent();

    void showLoading();

    void onRefreshEmpty();

    void onRefreshFailure(String message);
}
