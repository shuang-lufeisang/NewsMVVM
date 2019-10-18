package com.duan.android.news.headlinenews;

import android.os.Parcelable;

import com.duan.android.news.newslist.NewsListFragment;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * <pre>
 * author : Duan
 * time : 2019/10/15
 * desc :
 * version: 2.2.0
 * </pre>
 */
public class HeadlineNewsFragmentAdapter extends FragmentStatePagerAdapter {
    private ArrayList<ChannelsModel.Channel> mChannels;

    public HeadlineNewsFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setChannels(ArrayList<ChannelsModel.Channel> channels){
        this.mChannels = channels;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return NewsListFragment.newInstance(mChannels.get(position).channelId, mChannels.get(position).channelName);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        //return super.getItemPosition(object);
        return FragmentPagerAdapter.POSITION_NONE;
    }

    @Override
    public int getCount() {
        if(mChannels != null && mChannels.size() > 0) {
            return mChannels.size();
        }
        return 0;
    }

    @Override
    public void restoreState(Parcelable parcelable, ClassLoader classLoader){
    }

}
