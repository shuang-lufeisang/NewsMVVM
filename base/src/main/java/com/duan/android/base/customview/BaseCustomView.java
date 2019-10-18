package com.duan.android.base.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * <pre>
 * author : Duan
 * time : 2019/10/15
 * desc :   自定义 View 基类
 * version: 2.2.0
 * </pre>
 */
public abstract class BaseCustomView<D extends ViewDataBinding, VM extends BaseCustomViewModel> extends LinearLayout
        implements ICustomView<VM>, View.OnClickListener{

    private D dataBinding;
    private VM viewModel;
    private ICustomViewActionListener mListener;

    public BaseCustomView(Context context) {
        super(context);
        init();
    }

    public BaseCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BaseCustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public View getRootView(){
        return dataBinding.getRoot();
    }

    protected D getDataBinding(){
        return dataBinding;
    }

    protected VM getViewModel(){
        return viewModel;
    }


//    protected abstract int setViewLayoutId();
    @LayoutRes
    public abstract int getLayoutId();

    protected abstract void setDataToView(VM data);

    protected abstract void onRootClick(View view);

    public void init(){
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (getLayoutId() != 0) {
            dataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), this, false);
            dataBinding.getRoot().setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.onAction(ICustomViewActionListener.ACTION_ROOT_VIEW_CLICKED, view, viewModel);
                    }
                    onRootClick(view);
                }
            });
            this.addView(dataBinding.getRoot());
        }
    }

    @Override
    public void setData(VM data) {
        viewModel = data;
        setDataToView(viewModel);
        if (dataBinding != null){
            dataBinding.executePendingBindings();
        }
        onDataUpdated();
    }

    protected void onDataUpdated(){

    }

    @Override
    public void setStyle(int resId) {
    }

    @Override
    public void setActionListener(ICustomViewActionListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {
    }

}
