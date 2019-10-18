package com.duan.android.base.model;

import java.lang.ref.WeakReference;

/**
 * <pre>
 * author : Duan
 * time : 2019/10/14
 * desc :   不分页数据
 * version: 2.2.0
 * </pre>
 */
public abstract class BaseModel<T> extends SuperBaseModel<T>{

    public interface IModeListener<T> extends IBaseModelListener{
        // 一个 ViewModel 可能接受多份数据 - 多个网络请求
        void onLoadFinish(BaseModel model, T data);
        // 加载失败
        void onLoadFail(BaseModel model, String prompt);
    }

    @Override
    protected void notifyCachedData(T data) {
        loadSuccess(data);
    }

    /**
     * 发消息给UI线程
     *  加载网络数据失败
     *  通知所有的注册者们 加载结果
     * @param data
     */
    protected void loadSuccess(T data){
        synchronized (this){
            mUiHandler.postDelayed(() -> {
                for (WeakReference<IBaseModelListener> weakListener : mWeakListenerArrayList){
                    if (weakListener.get() instanceof IModeListener){
                        IModeListener listener = (IModeListener) weakListener.get();
                        if (listener != null){
                            listener.onLoadFinish(BaseModel.this, data);
                        }
                    }
                }
                /** 如果我们需要缓存数据，加载成功，缓存到preference */
                if (getCachedPreferenceKey() != null){
                    saveDataToPreference(data);
                }
            },0);
        }

    }

    /**
     * 加载网络数据失败
     * 通知所有的注册者们 加载结果
     */
    protected void loadFail(final String prompt){
        synchronized (this){
            mUiHandler.postDelayed(() -> {
                for (WeakReference<IBaseModelListener> weakListener : mWeakListenerArrayList){
                    if (weakListener.get() instanceof IModeListener){
                        IModeListener listener = (IModeListener) weakListener.get();
                        if (listener != null){
                            listener.onLoadFail(BaseModel.this, prompt);
                        }
                    }
                }
            }, 0);
        }
    }
}
