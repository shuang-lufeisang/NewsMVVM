package com.duan.android.news.api;

import com.duan.android.news.api.bean.NewsChannelsBean;
import com.duan.android.news.api.bean.NewsListBean;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.QueryMap;

/**
 * <pre>
 * author : Duan
 * time : 2019/10/15
 * desc :
 * version: 1.0.0
 * </pre>
 */
public interface NewsApiInterface {
    @GET("release/news")
    Observable<NewsListBean> getNewsList(
            @Header("Source") String source,
            @Header("Authorization") String authorization,
            @Header("Date") String date,
            @QueryMap Map<String, String> options
            );

    @GET("release/channel")
    Observable<NewsChannelsBean> getNewsChannels(
            @Header("Source") String source,
            @Header("Authorization") String authorization,
            @Header("Date") String date,
            @QueryMap Map<String, String> options
            );
}
