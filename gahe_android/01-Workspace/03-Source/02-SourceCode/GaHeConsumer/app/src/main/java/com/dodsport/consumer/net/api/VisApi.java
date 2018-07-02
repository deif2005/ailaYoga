package com.dodsport.consumer.net.api;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.ArrayMap;

import com.dodsport.consumer.activity.BaseActivity;
import com.dodsport.consumer.request.UrlInterfaceManager;
import com.google.gson.JsonObject;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 *
 * @author Administrator
 * @date 2017/10/31
 *
 * 网络请求API
 */


@TargetApi(Build.VERSION_CODES.KITKAT)
public class VisApi extends  RequestApi<ApiService> {

    private String token = BaseActivity.token;

    /**
     * 请求Token
     * */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Observable<JsonObject> getToken(){
        Map<String, String> map = new ArrayMap<>();
        String url = "/api/common/v1/getToken/";
        map.put("id","dodsport");
        map.put("password","dodsport");
        return ApiClient().posts(url,map)
                .compose(this.<JsonObject>normalSchedulers());
    }


    /**
     * 登录
     * */
    public Observable<ResponseBody> login(Map<String, String> map){
        String url = UrlInterfaceManager.LOGIN;
        map.put("token",token);
        return ApiClient().post(url,map)
                .compose(this.<ResponseBody>normalSchedulers());
    }

    /**
     * 获取验证码
     * */
    public Observable<ResponseBody> getCode(Map<String,String> map){
        String url = UrlInterfaceManager.CODE;
        map.put("token",token);
        return ApiClient().post(url,map)
                .compose(this.<ResponseBody>normalSchedulers());
    }

    /**
     * 校对验证码
     * */
    public Observable<ResponseBody> proofreadCode(Map<String,String> map){

        String url = UrlInterfaceManager.PROOFREADCODE;
        map.put("token",token);
        return ApiClient().post(url,map)
                .compose(this.<ResponseBody>normalSchedulers());
    }


  /*  public Observable<ResponseBody> getPost(String type,String url,Map<String,String>map){
        post(url,map)
                .subscribe(new BaseSubscriber<ResponseBody>() {
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onNext(ResponseBody body) {
                        mResponseBody.setType(type);
                        mResponseBody.setResponseBody(body);
                        RxBus.getInstance().post(mResponseBody);
                    }
                });
        return null;
    }*/
    /**
     * 普通的Post 请求返回JsonObject
     * */
    public Observable<JsonObject> post(String url, Map<String,String> map){
        map.put("token",token);
        return ApiClient().posts(url,map)
                .compose(this.<JsonObject>normalSchedulers());
    }

    /**
     * Post请求 返回ResponseBody
     * */
    public Observable<ResponseBody> posts(String url, Map<String,String> map){
        map.put("token",token);
        return ApiClient().post(url,map)
                .compose(this.<ResponseBody>normalSchedulers());
    }


    /**
     * 带图片的Post请求
     * */
    public Observable<JsonObject>postJson(String url,  RequestBody map){

        return ApiClient().postJsons(url,map)
                .compose(this.<JsonObject>normalSchedulers());
    }
    //@POST()
    //Observable<ResponseBody> postJson(@Url() String url, @Body RequestBody jsonBody);
}
