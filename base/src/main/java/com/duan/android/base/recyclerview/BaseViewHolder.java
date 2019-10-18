package com.duan.android.base.recyclerview;

import android.view.View;

import com.duan.android.base.customview.BaseCustomViewModel;
import com.duan.android.base.customview.ICustomView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <pre>
 * author : Duan
 * time : 2019/10/15
 * desc :   BaseViewHolder 统一绑定 BaseCustomViewModel
 * version: 2.2.0
 * </pre>
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    ICustomView view;

    public BaseViewHolder(@NonNull ICustomView itemView) {
        super((View)itemView);
        this.view = itemView;
    }

    public void bind(@NonNull BaseCustomViewModel item){
        view.setData(item);
    }
}
