package com.duan.android.news.api.bean;

import com.duan.android.network.beans.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * <pre>
 * author : Duan
 * time : 2019/10/14
 * desc :
 * version: 1.2.0
 * </pre>
 */
public class NewsListBean  extends BaseResponse {
    public class Contentlist {
        @SerializedName("allList")
        @Expose
        public List<String> allList = null;
        @SerializedName("pubDate")
        @Expose
        public String pubDate;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("channelName")
        @Expose
        public String channelName;
        @SerializedName("imageurls")
        @Expose
        public List<ImageUrl> imageurls = null;
        @SerializedName("desc")
        @Expose
        public String desc;
        @SerializedName("source")
        @Expose
        public String source;
        @SerializedName("channelId")
        @Expose
        public String channelId;
        @SerializedName("nid")
        @Expose
        public String nid;
        @SerializedName("link")
        @Expose
        public String link;
    }

    @SerializedName("showapi_res_body")
    @Expose
    public ShowapiResBody showapiResBody;

    public class Pagebean {

        @SerializedName("allPages")
        @Expose
        public Integer allPages;
        @SerializedName("contentlist")
        @Expose
        public List<Contentlist> contentlist = null;
        @SerializedName("currentPage")
        @Expose
        public Integer currentPage;
        @SerializedName("allNum")
        @Expose
        public Integer allNum;
        @SerializedName("maxResult")
        @Expose
        public Integer maxResult;
    }

    public class ImageUrl {

        @SerializedName("height")
        @Expose
        public String height;
        @SerializedName("width")
        @Expose
        public String width;

        @SerializedName("url")
        @Expose
        public String url;
    }

    public class ShowapiResBody {

        @SerializedName("ret_code")
        @Expose
        public Integer retCode;
        @SerializedName("pagebean")
        @Expose
        public Pagebean pagebean;
    }
}
