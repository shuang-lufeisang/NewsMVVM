package com.duan.android.news.headlinenews;

import com.duan.android.base.model.BaseModel;
import com.duan.android.base.utils.Logger;
import com.duan.android.network.errorhandler.ExceptionHandle;
import com.duan.android.network.observer.BaseObserver;
import com.duan.android.news.api.NewsApi;
import com.duan.android.news.api.bean.NewsChannelsBean;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * <pre>
 * author : Duan
 * time : 2019/10/14
 * desc :   新闻栏目 model
 * version: 2.2.0
 * </pre>
 */
public class ChannelsModel extends BaseModel<ArrayList<ChannelsModel.Channel>> {

    // 给基类 方便其 发序列化
    @Override
    protected Type getTClass() {
        return new TypeToken<ArrayList<Channel>>() {}.getType();
    }

    private static final String PREF_KEY_HOME_CHANNEL = "pref_key_home_channel"; // 有key 才有存缓存
    public static final String PREDEFINED_CHANNELS = "[\n" +
            "    {\n" +
            "        \"channelId\": \"5572a108b3cdc86cf39001cd\",\n" +
            "        \"channelName\": \"国内焦点\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"channelId\": \"5572a108b3cdc86cf39001ce\",\n" +
            "        \"channelName\": \"国际焦点\"\n" +
            "    }\n" +
            "]";

//    public static final String PREDEFINED_CHANNELS =
//            "[\n" +
//            "  {\n" +
//            "      \"channelId\":\"5572a108b3cdc86cf39001cd\",\n" +
//            "      \"channeName\":\"国内焦点\",\n" +
//            "   },\n" +
//            "  {\n" +
//            "      \"channelId\":\"5572a108b3cdc86cf39001ce\",\n" +
//            "      \"channeName\":\"国际焦点\",\n" +
//            "   },\n" +
//            "]";


    @Override
    public void refresh() {

    }

    String TAG = "ChannelsModel";
    @Override
    protected void load() {
        NewsApi.getInstance().getNewsChannels(new BaseObserver<NewsChannelsBean>(this){

            @Override
            public void onNext(NewsChannelsBean newsChannelsBean) {

                Logger.e(TAG, "========= onNext ========");
                ArrayList<Channel> channels = new ArrayList<>();
                for (NewsChannelsBean.ChannelList source : newsChannelsBean.showapiResBody.channelList){
                    Channel channel = new Channel();
                    channel.channelId = source.channelId;
                    channel.channelName = source.name;
                    channels.add(channel);
                }
                loadSuccess(channels);
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                Logger.e(TAG, "========= onError ========");
                e.printStackTrace();
                loadFail(e.message);
            }
        });
    }

    // 需要缓存 继承该方法 返回 key
    @Override
    protected String getCachedPreferenceKey() {
        return PREF_KEY_HOME_CHANNEL;
    }
    // 需要 APK 级别的数据缓存 继承该方法 返回 key
    @Override
    protected String getApkString() {
        return PREDEFINED_CHANNELS;
    }



    public class Channel{
        public String channelId;
        public String channelName;
    }
}
