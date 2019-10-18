package com.duan.android.common.titleview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.duan.android.base.customview.BaseCustomView;
import com.duan.android.common.R;
import com.duan.android.common.databinding.TitleViewBinding;

import androidx.annotation.Nullable;

/**
 * <pre>
 * author : Duan
 * time : 2019/10/15
 * desc :
 * version: 2.2.0
 * </pre>
 */
public class TitleView extends BaseCustomView<TitleViewBinding, TitleViewModel> {
    public TitleView(Context context) {
        super(context);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.title_view;
    }

    @Override
    protected void setDataToView(TitleViewModel data) {

        getDataBinding().setViewModel(data);
    }

    @Override
    protected void onRootClick(View view) {

        // TODO: 2019/10/15 点击事件
    }
}
