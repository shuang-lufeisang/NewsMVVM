package com.duan.android.news.newslist;

import com.duan.android.base.customview.BaseCustomViewModel;
import com.duan.android.base.fragment.IBasePagingView;
import com.duan.android.base.model.BasePagingModel;
import com.duan.android.base.viewmodel.BaseViewModel;

import java.util.ArrayList;


/**
 * <pre>
 * author : Duan
 * time : 2019/10/14
 * desc :
 * version: 2.2.0
 * </pre>
 */
public class NewsListViewModel extends BaseViewModel<NewsListViewModel.INewsView, NewsListModel>
        implements BasePagingModel.IModeListener<ArrayList<BaseCustomViewModel>> {

    private ArrayList<BaseCustomViewModel> mNewsList = new ArrayList<>();
    public NewsListViewModel(String classId, String lboClassId){
        model = new NewsListModel(classId, lboClassId);
        model.register(this);
        model.getCachedDataAndLoad();
    }
    @Override
    public void onLoadFinish(BasePagingModel model, ArrayList<BaseCustomViewModel> data, boolean isEmpty, boolean isFirstPage, boolean hasNextPager) {
        if (getPageView() != null){
            if (model instanceof NewsListModel){
                if (isFirstPage){
                    mNewsList.clear();
                }
                if (isEmpty){
                    if (isFirstPage){
                        getPageView().onRefreshEmpty();
                    }else {
                        getPageView().onLoadMoreEmpty();
                    }
                }else {
                    mNewsList.addAll(data);
                    getPageView().onNewsLoaded(mNewsList);
                }
            }
        }
    }

    @Override
    public void onLoadFail(BasePagingModel model, String prompt, boolean isFirstPage) {
        if (getPageView() != null) {
            if (isFirstPage) {
                getPageView().onRefreshFailure(prompt);
            } else {
                getPageView().onLoadMoreFailure(prompt);
            }
        }
    }

    /** 刷新 */
    public void tryToRefresh() {
        model.refresh();
    }

    /** 加载更多 */
    public void tryToLoadNextPage() {
        model.loadNextPage();
    }

    public interface INewsView extends IBasePagingView{
        void onNewsLoaded(ArrayList<BaseCustomViewModel> channels);
    }
}
