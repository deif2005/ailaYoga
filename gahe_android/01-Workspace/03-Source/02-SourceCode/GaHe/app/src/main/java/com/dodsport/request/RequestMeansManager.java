package com.dodsport.request;

import android.app.Activity;

import com.dodsport.nohttp.HttpListener;
import com.dodsport.nohttp.HttpResponseListener;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;

/**
 * Created by Administrator on 2017/3/8 0008.
 *
 * 网络请求管理类
 */

public class RequestMeansManager {

    public static Object cancelObject = new Object();

    /**
     * 用来标记取消。
     */
    public static Object object = new Object();

    /**
     * 请求队列。
     */
    public static RequestQueue mQueue;


    /**
     * 发起请求。
     *
     * @param what      what.
     * @param request   请求对象。
     * @param callback  回调函数。
     * @param canCancel 是否能被用户取消。
     * @param isLoading 实现显示加载框。
     * @param <T>       想请求到的数据类型。
     */
    public static <T> void request2(Activity actvity, int what, Request<T> request, HttpListener<T> callback,
                             boolean canCancel, boolean isLoading) {
        request.setCancelSign(object);
        mQueue.add(what, request, new HttpResponseListener<>(actvity, request, callback, canCancel, isLoading));
    }



    /**
     * 发送网络请求
     * */
    public static <T> void request(int what, Request<T> request, OnResponseListener<T> listener) {
        // 这里设置一个sign给这个请求。
        request.setCancelSign(cancelObject);

        com.dodsport.request.CallServer.getInstance().add(what, request, listener);
    }

}
