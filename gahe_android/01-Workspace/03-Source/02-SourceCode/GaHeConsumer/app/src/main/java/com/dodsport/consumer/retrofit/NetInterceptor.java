package com.dodsport.consumer.retrofit;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 界面描述：
 * <p>
 *
 * @author tianyang
 * @date 2017/9/27
 */

public class NetInterceptor implements Interceptor {
    private RequestHandler handler;

    public NetInterceptor(RequestHandler handler) {
        this.handler = handler;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        if (handler != null) {
            request = handler.onBeforeRequest(request, chain);
        }
        Response response = chain.proceed(request);
        if (handler != null) {
            try {
                Log.i("*****", "网络请求返回--->"+response.toString()+"");
            } catch (Exception e) {
                Log.i("*****", "网络请求返回捕获异常--->"+e.toString()+"");
                e.printStackTrace();
            }
            Response tmp = handler.onAfterRequest(response, chain);
            if (tmp != null) {
                return tmp;
            }
        }
        return response;
    }
}
