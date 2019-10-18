package com.duan.android.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duan.android.base.R;
import com.duan.android.base.loadsir.EmptyCallback;
import com.duan.android.base.loadsir.ErrorCallback;
import com.duan.android.base.loadsir.LoadingCallback;
import com.duan.android.base.utils.ToastUtil;
import com.duan.android.base.viewmodel.BaseViewModel;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

/**
 * <pre>
 * author : Duan
 * time : 2019/10/14
 * desc :
 * todo 懒加载等。。。
 * version: 2.2.0
 * </pre>
 */
public abstract class BaseFragment <V extends ViewDataBinding, VM extends BaseViewModel>
        extends Fragment implements IBasePagingView{

    protected String mFragmentTag = "";

    protected VM viewModel;
    protected V viewDataBinding;

    private LoadService mLoadService; // view 加载处理

    @LayoutRes
    public abstract int getLayoutId();

    protected abstract VM getViewModel();
    public abstract int getBindingVariable();  // 数据

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParameters();
    }

    /***
     *   初始化参数
     */
    protected void initParameters() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return viewDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = getViewModel();
        if (viewModel != null) {
            viewModel.attachUI(this);
        }
        if(getBindingVariable() > 0) {
            viewDataBinding.setVariable(getBindingVariable(), viewModel);
            viewDataBinding.executePendingBindings();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /* implements IBasePagingView(extends IBaseView) start */
    @Override
    public void onRefreshEmpty() {
        if(mLoadService != null){
            mLoadService.showCallback(EmptyCallback.class);
        }
    }

    @Override
    public void onRefreshFailure(String message) {
        if (mLoadService != null) {
            if(!isShowedContent) {
                mLoadService.showCallback(ErrorCallback.class);
            } else {
                ToastUtil.show(getContext(), message);
            }
        }
    }

    @Override
    public void showLoading() {
        if (mLoadService != null) {
            mLoadService.showCallback(LoadingCallback.class);
        }
    }
    private boolean isShowedContent = false;

    @Override
    public void showContent() {
        if (mLoadService != null) {
            isShowedContent = true;
            mLoadService.showSuccess();
        }
    }

    @Override
    public void onLoadMoreFailure(String message) {
        ToastUtil.show(getContext(), message);
    }

    @Override
    public void onLoadMoreEmpty() {
        ToastUtil.show(getContext(), getString(R.string.no_more_data));
    }
    /* implements IBasePagingView(extends IBaseView) start */

    /** 点击重试 */
    protected abstract void onRetryBtnClick();

    /** loadsir */
    public void setLoadSir(View view) {
        // You can change the callback on sub thread directly.
        mLoadService = LoadSir.getDefault().register(view, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                onRetryBtnClick();
            }
        });
    }



    /*  打印生命周期 start               */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(getFragmentTag(), this + ": " + "onActivityCreated");
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(getContext());
        Log.d(getFragmentTag(), this + ": " + "onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (viewModel != null && viewModel.isUIAttached())
            viewModel.detachUI();
        Log.d(getFragmentTag(), this + ": " + "onDetach");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(getFragmentTag(), this + ": " + "onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(getFragmentTag(), this + ": " + "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(getFragmentTag(), this + ": " + "onResume");
    }

    @Override
    public void onDestroy() {
        Log.d(getFragmentTag(), this + ": " + "onDestroy");
        super.onDestroy();
    }
    /*  打印生命周期 end               */


    protected abstract String getFragmentTag();

}
