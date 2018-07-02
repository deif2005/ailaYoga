package com.dodsport.consumer.model;

import java.io.Serializable;

/**
 * 服务器通用返回数据格式
 * Created by jaycee on 2017/6/23.
 */
public class BaseEntity<E> implements Serializable{

//    @SerializedName("code")
//    private E code;
//    @SerializedName("msg")
//    private String msg;
//    @SerializedName("data")
//    private E data;

    private String result;
    private String datas;

    public BaseEntity() {
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDatas() {
        return datas;
    }

    public void setDatas(String datas) {
        this.datas = datas;
    }

    @Override
    public String toString() {
        return "StatusBean{" +
                "result='" + result + '\'' +
                ", datas='" + datas + '\'' +
                '}';
    }

}
