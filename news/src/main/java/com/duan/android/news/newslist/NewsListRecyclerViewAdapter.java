package com.duan.android.news.newslist;

import android.view.ViewGroup;

import com.duan.android.base.customview.BaseCustomViewModel;
import com.duan.android.base.recyclerview.BaseViewHolder;
import com.duan.android.common.picturetitleview.PictureTitleView;
import com.duan.android.common.picturetitleview.PictureTitleViewModel;
import com.duan.android.common.titleview.TitleView;
import com.duan.android.common.titleview.TitleViewModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <pre>
 * author : Duan
 * time : 2019/10/15
 * desc :
 * version: 2.2.0
 * </pre>
 */
public class NewsListRecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private ArrayList<BaseCustomViewModel> mItems;
    private final int VIEW_TYPE_PICTURE_TITLE = 1;  // 有图片的item
    private final int VIEW_TYPE_TITLE = 2;          // 纯文字的item

    NewsListRecyclerViewAdapter() {
    }

    void setData(ArrayList<BaseCustomViewModel> items) {
        mItems = items;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_PICTURE_TITLE) {
            PictureTitleView pictureTitleView = new PictureTitleView(parent.getContext());
            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            pictureTitleView.setLayoutParams(layoutParams);
            return new BaseViewHolder(pictureTitleView);
        } else if(viewType == VIEW_TYPE_TITLE) {
            TitleView titleView = new TitleView(parent.getContext());
            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            titleView.setLayoutParams(layoutParams);
            return new BaseViewHolder(titleView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.bind(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        if(mItems != null && mItems.size() > 0) {
            return mItems.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if(mItems.get(position) instanceof PictureTitleViewModel){
            return VIEW_TYPE_PICTURE_TITLE;
        } else if(mItems.get(position) instanceof TitleViewModel){
            return VIEW_TYPE_TITLE;
        }
        return -1;
    }
}
