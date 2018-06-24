package com.dod.sport.domain.common;

import java.io.Serializable;

/**
 * ResponseCommon
 * 响应公共类
 * @author yuhao
 * @date 2016/9/20 19:33
 */
public class ResponseCommon implements Serializable{

    private static final long serialVersionUID = 4076174482500448600L;

    private String code;

    private String msg;

    public ResponseCommon(){}

    public ResponseCommon(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

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
}
