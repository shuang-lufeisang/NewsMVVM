package com.duan.android.base.viewmodel;

/**
 * <pre>
 * author : Duan
 * time : 2019/10/14
 * desc :  ViewModel 基类接口
 * version: 1.0.0
 * </pre>
 */
public interface IBaseViewModel<V> {

    void attachUI(V view);

    V getPageView();

    boolean isUIAttached();

    void detachUI();
}
