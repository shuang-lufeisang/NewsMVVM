package com.duan.android.base.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.duan.android.base.loadsir.EmptyCallback;
import com.duan.android.base.loadsir.ErrorCallback;
import com.duan.android.base.loadsir.LoadingCallback;
import com.duan.android.base.viewmodel.BaseViewModel;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import androidx.appcompat.app.AppCompatActivity;

/**
 * <pre>
 * author : Duan
 * time : 2019/10/14
 * desc :   base 层 Activity 基类
 * version: 2.2.0
 * </pre>
 */
public abstract class BaseActivity<V extends ViewDataBinding, VM extends BaseViewModel>
        extends AppCompatActivity implements IBaseView{

    protected VM viewModel;
    protected V viewDataBinding;
    private LoadService mLoadService; // view 加载处理



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
        performDataBinding();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (viewModel != null && viewModel.isUIAttached())
            viewModel.detachUI();
    }

    private void initViewModel() {
        viewModel = getViewModel();
        if(viewModel != null) {
            viewModel.attachUI(this);
        }
    }

    private void performDataBinding(){
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        if (viewModel == null){
            this.viewModel = getViewModel();
        }
        if (getBindingVariable() > 0){
            viewDataBinding.setVariable(getBindingVariable(), viewModel);
        }
        viewDataBinding.executePendingBindings();
    }

    protected abstract void onRetryBtnClick();  // 点击重试

    @LayoutRes
    public abstract int getLayoutId();

    protected abstract VM getViewModel();

    public abstract int getBindingVariable();  // 数据

    public void setLoadSir(View view){
        mLoadService = LoadSir.getDefault().register(view, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                onRetryBtnClick();
            }
        });
    }


    /* implements IBaseView start */
    @Override
    public void showContent() {
        if (mLoadService != null) {
            mLoadService.showSuccess();
        }
    }

    @Override
    public void showLoading() {
        if (mLoadService != null) {
            mLoadService.showCallback(LoadingCallback.class);
        }
    }

    @Override
    public void onRefreshEmpty() {
        if(mLoadService != null) {
            mLoadService.showCallback(EmptyCallback.class);
        }
    }

    @Override
    public void onRefreshFailure(String message) {
        if(mLoadService != null) {
            mLoadService.showCallback(ErrorCallback.class);
        }
    }
    /* implements IBaseView end */

}
