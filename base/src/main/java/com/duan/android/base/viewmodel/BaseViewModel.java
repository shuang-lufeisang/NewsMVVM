package com.duan.android.base.viewmodel;

import com.duan.android.base.model.SuperBaseModel;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import androidx.lifecycle.ViewModel;

/**
 * <pre>
 * author : Duan
 * time : 2019/10/14
 * desc :   ViewModel 基类，只有 attachUI 和 detachUI 的处理
 *          ViewModel 传入 View 和 Model
 * version: 2.2.0
 * </pre>
 */
public class BaseViewModel<V, M extends SuperBaseModel> extends ViewModel implements IBaseViewModel<V> {

    private Reference<V> mUIRef;
    protected M model;

    @Override
    public void attachUI(V view) {
        mUIRef = new WeakReference<>(view);
    }

    @Override
    public V getPageView() {
        if (mUIRef == null){
            return null;
        }
        return mUIRef.get();
    }

    @Override
    public boolean isUIAttached() {
        return mUIRef != null && mUIRef.get() != null;
    }

    @Override
    public void detachUI() {
        if (mUIRef != null){
            mUIRef.clear();
            mUIRef = null;
        }
        if (model != null){
            model.cancel();
        }
    }
}
