package com.duan.android.common;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import androidx.databinding.BindingAdapter;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * <pre>
 * author : Duan
 * time : 2019/10/16
 * desc :   dataBinding 中的公共方法
 * version: 1.0.0
 * </pre>
 */
public class CommonBindingAdapters {

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView view, String url){
        if(!TextUtils.isEmpty(url)) {
            Glide.with(view.getContext())
                    .load(url)
                    .transition(withCrossFade())
                    .into(view);
        }
    }

    @BindingAdapter("android:visibility")
    public static void setVisibility(View view, Boolean value){
        view.setVisibility(value ? View.VISIBLE : View.GONE);
    }
}
