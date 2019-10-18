package com.duan.android.base.model;

import java.io.Serializable;

/**
 * <pre>
 * author : Duan
 * time : 2019/10/14
 * desc :  结构化的数据，比如交易数据 要写到数据库里去
 * version: 2.2.0
 * </pre>
 */
public class BaseCachedData<T> implements Serializable {
    public long updateTimeInMills; // 更新时间，读缓存时 根据时间判断是否重新加载网络请求
    public T data;
}
