package com.dodsport.consumer.net.api;

import com.dodsport.consumer.exception.ApiException;
import com.dodsport.consumer.model.ApiResult;

import rx.functions.Func1;

/**
 * @Description: ApiResult<T>转T
 * @author: jeasinlee
 * @date: 2016-12-30 17:55
 */
public class ApiDataFunc<T> implements Func1<ApiResult<T>, T> {
    public ApiDataFunc() {
    }

    @Override
    public T call(ApiResult<T> response) {
        if (ApiException.isSuccess(response)) {
            return response.getData();
        } else {
            return (T) new ApiException(new Throwable(response.getMsg()), response.getCode());
        }
    }
}
