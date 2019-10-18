package com.duan.android.news.headlinenews;

import android.os.Bundle;
import android.view.View;

import com.duan.android.base.fragment.BaseFragment;
import com.duan.android.news.R;
import com.duan.android.news.databinding.FragmentHeadlineNewsBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import androidx.annotation.NonNull;

/**
 * <pre>
 * author : Duan
 * time : 2019/10/14
 * desc :   头条新闻
 * version: 2.2.0
 * </pre>
 */
public class HeadlineNewsFragment extends BaseFragment<FragmentHeadlineNewsBinding, HeadlineNewsViewModel> implements HeadlineNewsViewModel.IMainView {

    HeadlineNewsFragmentAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_headline_news;
    }

    @Override
    protected HeadlineNewsViewModel getViewModel() {
        return new HeadlineNewsViewModel();
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // TODO: 2019/10/18 数据加载
        viewModel.refresh();
        viewDataBinding.tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        initChannels();
    }

    public void initChannels(){
        mAdapter = new HeadlineNewsFragmentAdapter(getChildFragmentManager());
        viewDataBinding.viewpager.setAdapter(mAdapter);
        viewDataBinding.viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(viewDataBinding.tablayout));
        viewDataBinding.viewpager.setOffscreenPageLimit(1);
        //绑定tab点击事件
        viewDataBinding.tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewDataBinding.viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    // 顶部分类栏 不需要点击重试
    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    protected String getFragmentTag() {
        return this.getClass().getSimpleName();
    }


    @Override
    public void onChannelsLoaded(ArrayList<ChannelsModel.Channel> channels) {
        mAdapter.setChannels(channels);
        viewDataBinding.tablayout.removeAllTabs();
        for (ChannelsModel.Channel channel : channels){
            viewDataBinding.tablayout.addTab(viewDataBinding.tablayout.newTab().setText(channel.channelName));
        }
        viewDataBinding.tablayout.scrollTo(0,0);
    }
}
