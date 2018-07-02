package com.dodsport.consumer.net.api;

import com.dodsport.consumer.model.PagingReq;
import com.dodsport.consumer.request.UrlInterfaceManager;
import com.dodsport.consumer.retrofit.NetMgr;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *
 * @author Administrator
 * @date 2017/10/31
 */

public abstract class RequestApi<T> {

    /**用于分页请求**/
    protected PagingReq pagingReq = new PagingReq();


    public T ApiClient() {//BuildConfig.BaseUrl
        return NetMgr.getInstance().getRetrofit(UrlInterfaceManager.URL).create(getType());
    }

    /**指定观察者与被观察者线程**/
    protected <T> Observable.Transformer<T, T> normalSchedulers() {
        return source -> source.onTerminateDetach().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    protected <T> Observable.Transformer<ResponseBody, T> norTransformer(final Class<T> clazz) {
        return new Observable.Transformer<ResponseBody, T>() {
            @Override
            public Observable<T> call(Observable<ResponseBody> apiResultObservable) {
                return apiResultObservable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                        .mainThread()).map(new ApiFunc<T>(clazz)).onErrorResumeNext(new ApiErrFunc<T>());
            }
        };
    }

    private Class<T> getType() {
        Class<T> entityClass = null;
        Type t = getClass().getGenericSuperclass();
        Type[] p = ((ParameterizedType) t).getActualTypeArguments();
        entityClass = (Class<T>) p[0];
        return entityClass;
    }

}
