package com.duan.android.base.model;

import java.lang.ref.WeakReference;

/**
 * <pre>
 * author : Duan
 * time : 2019/10/14
 * desc :   分页数据
 * version: 2.2.0
 * </pre>
 */
public abstract class BasePagingModel<T> extends SuperBaseModel<T> {

    protected boolean isRefresh = true;  // 是否是刷新
    protected  int pagerNumber = 0;      // 第几页

    public interface IModeListener<T> extends IBaseModelListener{
        // 一个 ViewModel 可能接受多份数据 - 多个网络请求
        void onLoadFinish(BasePagingModel model, T data, boolean isEmpty, boolean isFirstPage, boolean hasNextPager);
        // 加载失败：第一页需要 错误提示，否则提示加载失败（加载更多）
        void onLoadFail(BasePagingModel model, String prompt, boolean isFirstPage);
    }

    @Override
    protected void notifyCachedData(T data) {
        loadSuccess(data, false, true, true);
    }

    /**
     * 发消息给UI线程
     * @param data
     * @param isEmpty     model的整个数据是否为空
     * @param isFirstPage 是否是第一页
     * @param hasNextPage 是否还有下一页
     */
    protected void loadSuccess(T data, final boolean isEmpty, final boolean isFirstPage,
                               final boolean hasNextPage){
        synchronized (this){
            mUiHandler.postDelayed(() -> {
                for (WeakReference<IBaseModelListener> weakListener : mWeakListenerArrayList){
                    if (weakListener.get() instanceof IModeListener){
                        IModeListener listener = (IModeListener) weakListener.get();
                        if (listener != null){
                            listener.onLoadFinish(BasePagingModel.this, data, isEmpty, isFirstPage, hasNextPage);
                        }
                    }
                }
                if (getCachedPreferenceKey() != null && isFirstPage){ // 分页数据 只缓存第0页
                    saveDataToPreference(data);
                }
            },0);
        }

    }

    protected void loadFail(final String prompt, final boolean isFirstPage){
        synchronized (this){
            mUiHandler.postDelayed(() -> {
                for (WeakReference<IBaseModelListener> weakListener : mWeakListenerArrayList){
                    if (weakListener.get() instanceof IModeListener){
                        IModeListener listener = (IModeListener) weakListener.get();
                        if (listener != null){
                            listener.onLoadFail(BasePagingModel.this, prompt, isFirstPage);
                        }
                    }
                }
            }, 0);
        }
    }
}
