/*
 * 版权：Copyright (c) 2007-2016 长沙埃欧塔网络科技有限公司.
 * 描述：
 * 作者： 杨光亮
 * 联系方式：13142068880
 * 修改单号：
 * 修改内容：新增
 */

package com.dodsport.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private final static String TAG = JsonUtils.class.getSimpleName();

    private JsonUtils() {
        throw new AssertionError("Can not be instantiated class");
    }

    public static <T> JsonElement toJsonTree(List<T> target) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<T>>() {
        }.getType();
        return gson.toJsonTree(target, listType);
    }

    public static <T> T fromJson(JsonElement json, Class<T> mT) {
        Gson gson = new Gson();
        return gson.fromJson(json, mT);
    }

    public static <T> T fromJsonObjectToObject(JSONObject jsonObject, Class<T> mT) {
        Gson gson = new Gson();
        return gson.fromJson(jsonObject.toString(), mT);
    }

    public static JSONObject fromObjectToJsonObject(Object obj) {
        Gson gson = new Gson();
        JsonObject jsonObject = (JsonObject) gson.toJsonTree(obj);
        JSONObject object = null;
        try {
            object = new JSONObject(jsonObject.toString());
        } catch (JSONException e) {
            Log.e(TAG, "JSON String is invalid");
        }
        return object;
    }

    public static String ObjectToJsonString(Object mT) {
        Gson gson = new Gson();
        return gson.toJson(mT);
    }

    public static <T> T fromStringToJson(String jsonString, Class<T> mT) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, mT);
    }

    public static <T> List<T> fromJSONArrayToList(JSONArray array, Class<T> mT) {
        Gson gson = new Gson();
        List<T> list = new ArrayList<>();
        try {
            int length = array.length();
            for (int i = 0; i < length; i++){
                String child = array.getJSONObject(i).toString();
                list.add(gson.fromJson(child, mT));
            }
        } catch (JSONException e) {
            Log.e(TAG, "JSON String is invalid");
        }
        return list;
    }

    public static <T> List<T> fromStringToList(String jsonString, Class<T> mT) {
        Gson gson = new Gson();
        List<T> list = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(jsonString);
            int length = array.length();
            for (int i = 0; i < length; i++){
                String child = array.getJSONObject(i).toString();
                list.add(gson.fromJson(child, mT));
            }
        } catch (JSONException e) {
            Log.e(TAG, "JSON String is invalid");
        }
        return list;
    }

    public static JsonObject toJson(Object obj) {
        Gson gson = new Gson();
        return (JsonObject) gson.toJsonTree(obj);
    }

    public static <T> List<T> fromJsonArrayToList(JSONArray response, Class<T> mT) throws JSONException {
        List<T> list = new ArrayList<T>();
        int length = response.length();
        for (int i = 0; i < length; i++) {
            JSONObject object = (JSONObject) response.get(i);
            list.add(fromJsonObjectToObject(object, mT));

        }
        return list;
    }


}
