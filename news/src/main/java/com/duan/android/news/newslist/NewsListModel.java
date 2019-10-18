package com.duan.android.news.newslist;

import com.duan.android.base.customview.BaseCustomView;
import com.duan.android.base.customview.BaseCustomViewModel;
import com.duan.android.base.model.BasePagingModel;
import com.duan.android.common.picturetitleview.PictureTitleView;
import com.duan.android.common.picturetitleview.PictureTitleViewModel;
import com.duan.android.common.titleview.TitleViewModel;
import com.duan.android.network.errorhandler.ExceptionHandle;
import com.duan.android.network.observer.BaseObserver;
import com.duan.android.news.api.NewsApi;
import com.duan.android.news.api.bean.NewsListBean;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * <pre>
 * author : Duan
 * time : 2019/10/14
 * desc :   新闻列表 model
 * version: 2.2.0
 * </pre>
 */
public class NewsListModel<T> extends BasePagingModel<T> {



    private static final String PREF_KEY_NEWS_CHANNEL = "pref_key_news_"; // 有key 才有存缓存
    private String mChannelId = "";
    private String mChannelName = "";

    @Override
    protected String getCachedPreferenceKey() {
        return PREF_KEY_NEWS_CHANNEL + mChannelId;
    }

    // 给基类 方便其 发序列化
    @Override
    protected Type getTClass() {
        return new TypeToken<ArrayList<PictureTitleViewModel>>(){}.getType();
    }

    public NewsListModel(String channelId, String channelName){
        mChannelId = channelId;
        mChannelName = channelName;
    }

    @Override
    public void refresh() {
        isRefresh = true;
        load();
    }

    // 加载更多（不是刷新）
    public void loadNextPage(){
        isRefresh = false;
        load();
    }

    @Override
    protected void load() {

        NewsApi.getInstance().getNewsList(new BaseObserver<NewsListBean>(this) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                e.printStackTrace();
                loadFail(e.message, isRefresh);
            }

            @Override
            public void onNext(NewsListBean newsListBean) {

                pagerNumber = isRefresh ? 2 : pagerNumber + 1;
                ArrayList<BaseCustomViewModel> baseCustomViewModels = new ArrayList<>();
                for (NewsListBean.Contentlist source: newsListBean.showapiResBody.pagebean.contentlist){
                    if (source.imageurls != null && source.imageurls.size()>1){
                        PictureTitleViewModel viewModel = new PictureTitleViewModel();
                        viewModel.avatarUrl = source.imageurls.get(0).url;
                        viewModel.link = source.link;
                        viewModel.title = source.title;
                        baseCustomViewModels.add(viewModel);
                    }else {
                        TitleViewModel viewModel = new TitleViewModel();
                        viewModel.link = source.link;
                        viewModel.title = source.title;
                        baseCustomViewModels.add(viewModel);
                    }
                }
                loadSuccess((T) baseCustomViewModels,
                        baseCustomViewModels.size() == 0,
                        isRefresh,
                        !(baseCustomViewModels.size() == 0));
            }
        }, mChannelId, mChannelName, String.valueOf(isRefresh ? 1 : pagerNumber));
    }
}
