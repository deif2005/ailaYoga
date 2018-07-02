package com.dodsport.consumer.cases;


import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.ArrayMap;

import com.dodsport.consumer.cases.base.UseCase;
import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * 界面描述：
 * <p>
 *
 * @author tianyang
 * @date 2017/9/27
 */

public class GetCitiesCase extends UseCase<GetCitiesCase.Api> {
    interface Api {
        @POST("/api/common/v1/getToken/")
        Observable<String> getCitiesCase();

        /**
         * 获取Token
         * id	string		是	用户ID
         * password	String		是	用户密码
         * */
        @POST("/api/common/v1/getToken/")
        @FormUrlEncoded
        Observable<JsonObject> getToken(
                @Field("id") String id,
                @Field("password") String password

        );

        /**普通的post请求*/
        @POST()
        @FormUrlEncoded
        Observable<JsonObject> post(@Url() String url, @FieldMap Map<String, String> maps);


        //<T> Call<TokenBean> token(String id, String password, Observable.Transformer<T, T> ttTransformer);
    }

//    public Observable<String> getCities() {
//        return ApiClient().getCitiesCase()
//                .compose(this.<String>normalSchedulers());
//    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Observable<JsonObject> getToken(final Class clazz){
        Map<String, String> map = new ArrayMap<>();
        String url = "/api/common/v1/getToken/";
        map.put("id","dodsport");
        map.put("password","dodsport");
        return ApiClient().post(url,map)
                .compose(this.<JsonObject>norTransformer(clazz));
    }


}
