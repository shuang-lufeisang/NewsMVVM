package com.duan.android.network;

import android.util.Log;

import com.duan.android.base.utils.Logger;
import com.duan.android.network.errorhandler.AppDataErrorHandler;
import com.duan.android.network.errorhandler.HttpErrorHandler;
import com.duan.android.network.interceptor.RequestInterceptor;
import com.duan.android.network.interceptor.ResponseInterceptor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <pre>
 * author : Duan
 * time : 2019/10/14
 * desc : 一个Base初始化， 两个拦截器，两个错误处理
 *        网络安全等问题另有课程
 * version: 1.0.0
 * </pre>
 */
public abstract class ApiBase {
    protected Retrofit retrofit;
    protected static INetworkRequestInfo networkRequestInfo;
    private static ErrorTransformer sErrorTransformer = new ErrorTransformer();
    private static RequestInterceptor sHttpsRequestInterceptor;
    private static ResponseInterceptor sHttpsResponseInterceptor;

    protected ApiBase(String baseUrl) {
        retrofit = new Retrofit
                .Builder()
                .client(getOkHttpClient())
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    // 在 NewsApplication 初始化网络
    public static void setNetworkRequestInfo(INetworkRequestInfo requestInfo) {
        networkRequestInfo = requestInfo;
        sHttpsRequestInterceptor = new RequestInterceptor(requestInfo);
        sHttpsResponseInterceptor = new ResponseInterceptor();
    }

    public OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS);

        /*可以统一添加网络参数到请求头*/
        okHttpClientBuilder.addInterceptor(sHttpsRequestInterceptor);

        /*网络请求返回的时候的数据处理*/
        okHttpClientBuilder.addInterceptor(sHttpsResponseInterceptor);


        setLoggingLevel(okHttpClientBuilder);

        OkHttpClient httpClient = okHttpClientBuilder.build();
        httpClient.dispatcher().setMaxRequestsPerHost(20);
        return httpClient;
    }

    // 设置日志拦截器
    private void setLoggingLevel(OkHttpClient.Builder builder) {
        HttpLoggingInterceptor logging = getHttpLoggingInterceptor();
        //BODY打印信息,NONE不打印信息
        logging.setLevel(networkRequestInfo.isDebug() ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        builder.addInterceptor(logging);
    }

    /**
     * 封装线程管理和订阅的过程
     */
    protected void ApiSubscribe(Observable observable, Observer observer) {
        observable.subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .unsubscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(sErrorTransformer)
                .subscribe(observer);
    }

    /**
     * 处理错误的变换
     * 网络请求的错误处理，其中网络错误分为两类：
     * 1、http请求相关的错误，例如：404，403，socket timeout等等；
     * 2、http请求正常，但是返回的应用数据里提示发生了异常，表明服务器已经接收到了来自客户端的请求，但是由于
     * 某些原因，服务器没有正常处理完请求，可能是缺少参数，或者其他原因；
     */
    private static class ErrorTransformer<T> implements ObservableTransformer {

        @Override
        public ObservableSource apply(Observable upstream) {
            //onErrorResumeNext当发生错误的时候，由另外一个Observable来代替当前的Observable并继续发射数据
            return (Observable<T>) upstream
                    .map(new AppDataErrorHandler())/*返回的数据统一错误处理*/
                    .onErrorResumeNext(new HttpErrorHandler<T>());/*Http 错误处理**/
        }
    }


    /////////////////////// 拦截器
    public static HttpLoggingInterceptor getHttpLoggingInterceptor(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Logger.i("RetrofitLog","retrofitBack = "+ message);
                    }
                }
        );

        Logger.i("ApiBase getHttpLoggingInterceptor", ""+ networkRequestInfo.isDebug());
        loggingInterceptor.setLevel(networkRequestInfo.isDebug() ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        return loggingInterceptor;

    }
}
