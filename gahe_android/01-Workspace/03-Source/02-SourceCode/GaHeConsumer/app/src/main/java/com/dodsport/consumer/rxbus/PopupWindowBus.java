package com.dodsport.consumer.rxbus;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/2.
 */

public class PopupWindowBus implements Serializable {

    private int mPosition;
    private String msg;
    private String Type;
    private String url;

    public PopupWindowBus() {
    }

    public PopupWindowBus(int position, String msg, String type,String url) {
        mPosition = position;
        this.msg = msg;
        this.Type = type;
        this.url = url;
    }

    public int getmPosition() {
        return mPosition;
    }

    public void setmPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getPosition() {
        return mPosition;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "PopupWindowBus{" +
                "mPosition=" + mPosition +
                ", msg='" + msg + '\'' +
                ", Type='" + Type + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
