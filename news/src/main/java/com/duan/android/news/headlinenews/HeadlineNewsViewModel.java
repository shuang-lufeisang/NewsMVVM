package com.duan.android.news.headlinenews;

import com.duan.android.base.activity.IBaseView;
import com.duan.android.base.model.BaseModel;
import com.duan.android.base.utils.Logger;
import com.duan.android.base.viewmodel.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * author : Duan
 * time : 2019/10/14
 * desc :
 * version: 2.2.0
 * </pre>
 */
public class HeadlineNewsViewModel extends BaseViewModel<HeadlineNewsViewModel.IMainView, ChannelsModel>
        implements BaseModel.IModeListener<ArrayList<ChannelsModel.Channel>> {

    String TAG = "HeadlineNewsViewModel";
    public ArrayList<ChannelsModel.Channel> channels = new ArrayList<>();

    public HeadlineNewsViewModel(){
        model = new ChannelsModel();
        model.register(this);
    }

    // 获取数据
    public void refresh(){
        model.getCachedDataAndLoad();
    }

    @Override
    public void onLoadFinish(BaseModel model, ArrayList<ChannelsModel.Channel> data) {
        if (model instanceof ChannelsModel){
            if (getPageView() != null && data instanceof List){
                channels.clear();
                channels.addAll(data);
                getPageView().onChannelsLoaded(channels);
            }
        }
    }

    @Override
    public void onLoadFail(BaseModel model, String prompt) {

        Logger.e(TAG, prompt);
    }

    public interface IMainView extends IBaseView{
        void onChannelsLoaded(ArrayList<ChannelsModel.Channel> channels);
    }
}
