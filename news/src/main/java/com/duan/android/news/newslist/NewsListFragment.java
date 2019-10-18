package com.duan.android.news.newslist;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.duan.android.base.customview.BaseCustomViewModel;
import com.duan.android.base.fragment.BaseFragment;
import com.duan.android.news.R;
import com.duan.android.news.databinding.NewsFragmentBinding;
import com.scwang.smartrefresh.header.WaterDropHeader;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * <pre>
 * author : Duan
 * time : 2019/10/14
 * desc :
 * version: 2.2.0
 * </pre>
 */
public class NewsListFragment extends BaseFragment<NewsFragmentBinding, NewsListViewModel> implements NewsListViewModel.INewsView{

    private NewsListRecyclerViewAdapter mAdapter;
    private String mChannelId = "";
    private String mChannelName = "";

    protected final static String BUNDLE_KEY_PARAM_CHANNEL_ID = "bundle_key_param_channel_id";
    protected final static String BUNDLE_KEY_PARAM_CHANNEL_NAME = "bundle_key_param_channel_name";

    public static NewsListFragment newInstance(String channelId, String channelName) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY_PARAM_CHANNEL_ID, channelId);
        bundle.putString(BUNDLE_KEY_PARAM_CHANNEL_NAME, channelName);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    protected void initParameters() {
        if (getArguments() != null) {
            mChannelId = getArguments().getString(BUNDLE_KEY_PARAM_CHANNEL_ID);
            mChannelName = getArguments().getString(BUNDLE_KEY_PARAM_CHANNEL_NAME);
            mFragmentTag = mChannelName;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.news_fragment;
    }

    @Override
    protected NewsListViewModel getViewModel() {
        Log.e(this.getClass().getSimpleName(), this + ": createViewModel.");
        return new NewsListViewModel(mChannelId, mChannelName);
    }
    @Override
    public int getBindingVariable() {
        return 0;
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentTag = "NewsListFragment";
        viewDataBinding.recyclerView.setHasFixedSize(true);
        viewDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new NewsListRecyclerViewAdapter();
        viewDataBinding.recyclerView.setAdapter(mAdapter);
        viewDataBinding.refreshLayout.setRefreshHeader(new WaterDropHeader(getContext()));
        viewDataBinding.refreshLayout.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale));
        viewDataBinding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            viewModel.tryToRefresh();
        });
        viewDataBinding.refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            viewModel.tryToLoadNextPage();
        });
        setLoadSir(viewDataBinding.refreshLayout);
        showLoading();
    }


    /** NewsViewModel 中onLoadFinish 填充数据后调用 */
    @Override
    public void onNewsLoaded(ArrayList<BaseCustomViewModel> newsItems) {
        if (newsItems != null && newsItems.size() > 0) {
            viewDataBinding.refreshLayout.finishLoadMore();
            viewDataBinding.refreshLayout.finishRefresh();
            showContent();
            mAdapter.setData(newsItems);
        } else {
            onRefreshEmpty();
        }
    }


    /** 点击重试按钮 */
    @Override
    protected void onRetryBtnClick() {
        viewModel.tryToRefresh();
    }

    @Override
    protected String getFragmentTag() {
        return this.getClass().getSimpleName();
    }




}
