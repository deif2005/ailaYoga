package com.dodsport.consumer.net.api;


import com.dodsport.consumer.exception.ApiException;

import rx.Observable;
import rx.functions.Func1;

/**
 * @Description: Throwable转Observable<T>
 * @author: jeasinlee
 * @date: 2017-01-03 16:00
 */
public class ApiErrFunc<T> implements Func1<Throwable, Observable<T>> {
    @Override
    public Observable<T> call(Throwable throwable) {
        return Observable.error(ApiException.handleException(throwable));
    }
}
