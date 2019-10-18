package com.duan.android.base.fragment;

import com.duan.android.base.activity.IBaseView;

/**
 * <pre>
 * author : Duan
 * time : 2019/10/14
 * desc :   有分页的view
 *          1.加载分页失败
 *          2.加载分页为空（没有数据了）
 * version: 1.0.0
 * </pre>
 */
public interface IBasePagingView extends IBaseView {

    void onLoadMoreFailure(String message);

    void onLoadMoreEmpty();
}
