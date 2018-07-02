package com.dodsport.model;

import java.io.Serializable;

/**
 * 返回状态的基本类
 */
public class StatusBean implements Serializable {


    private String result;
    private String datas;

    public StatusBean() {
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
