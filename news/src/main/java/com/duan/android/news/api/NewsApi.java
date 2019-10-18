package com.duan.android.news.api;


import com.duan.android.network.ApiBase;
import com.duan.android.network.utils.TecentUtil;
import com.duan.android.news.api.bean.NewsChannelsBean;
import com.duan.android.news.api.bean.NewsListBean;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;


/**
 * <pre>
 * author : Duan
 * time : 2019/10/15
 * desc :
 * version: 1.0.0
 * </pre>
 */
public final class NewsApi extends ApiBase {

    private static volatile NewsApi instance = null;
    private NewsApiInterface newsApiInterface;
    public static final String PAGE = "page";

    private NewsApi() {
        super("https://service-o5ikp40z-1255468759.ap-shanghai.apigateway.myqcloud.com/");
        newsApiInterface = retrofit.create(NewsApiInterface.class);
    }

    public static NewsApi getInstance(){
        if (instance == null){
            synchronized (NewsApi.class){
                if (instance == null){
                    instance = new NewsApi();
                }
            }
        }
        return instance;
    }

    /**
     * 获取新闻栏目
     *
     * @param observer 由调用者传过来的观察者对象
     */
    public void getNewsChannels(Observer<NewsChannelsBean> observer){
        Map<String, String> requestMap = new HashMap<>();
        String timeStr = TecentUtil.getTimeStr();
        Observable observable = newsApiInterface.getNewsChannels(
                "source",
                TecentUtil.getAuthorization(timeStr),
                timeStr,
                requestMap);
        ApiSubscribe(observable, observer);
    }


    /**
     * 获取新闻列表
     *
     * @param observer     由调用者传过来的观察者对象
     * @param channelId    栏目ID
     * @param channelName  栏目名称
     * @param page         获取页号
     */
    public void getNewsList(Observer<NewsListBean> observer, String channelId, String channelName, String page){
        Map<String, String> requestMap = new HashMap<>();
        String timeStr = TecentUtil.getTimeStr();
        requestMap.put("channelId", channelId);
        requestMap.put("channelName", channelName);
        requestMap.put(PAGE, page);
        ApiSubscribe(newsApiInterface.getNewsList(
                "source",
                TecentUtil.getAuthorization(timeStr),
                timeStr,
                requestMap), observer);
    }

}
