package com.dodsport.model;

import java.io.Serializable;

/**
 * 返回状态的基本类
 */

public class StatusBeans implements Serializable{

    private boolean State;
    private String Msg;
    private String data;
    private String IsReg;  //是否是第一次登录：   登陆过1    第一次登录0
    private String StatusCode;
/// StatusCode=0 进入直播间成功
/// StatusCode=1 您在本房间黑名单当中，不能进入
/// StatusCode=2 本房间需要VIP才能够进入。
/// StatusCode=3 您需要购买本视频才能观看。

    public StatusBeans() {

    }

    public String getIsReg() {
        return IsReg;
    }

    public void setIsReg(String isReg) {
        IsReg = isReg;
    }

    public boolean isState() {
        return State;
    }

    public void setState(boolean state) {
        State = state;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    public String getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(String statusCode) {
        StatusCode = statusCode;
    }

    @Override
    public String toString() {
        return "StatusBean{" +
                "State=" + State +
                ", Msg='" + Msg + '\'' +
                ", data='" + data + '\'' +
                ", IsReg='" + IsReg + '\'' +
                ", StatusCode='" + StatusCode + '\'' +
                '}';
    }
}
