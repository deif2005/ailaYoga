package com.dod.sport.util;

/**
 * Created by defi on 2017/8/10.
 * 用于返回结果集
 */
public class ReturnResult {

    private String msg="sys_ok";
    private String code="0";

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
