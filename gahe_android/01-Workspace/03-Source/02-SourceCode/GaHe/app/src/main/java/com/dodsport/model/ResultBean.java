package com.dodsport.model;

/**
 * Created by Administrator on 2017/8/16.
 *
 * 请求
 */

public class ResultBean {

    /**
     * code : 0
     * msg : sys_ok
     */

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBean() {
    }

    @Override
    public String toString() {
        return "ResultBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
