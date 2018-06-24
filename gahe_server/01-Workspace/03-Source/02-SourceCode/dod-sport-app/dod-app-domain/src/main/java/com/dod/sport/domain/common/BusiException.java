package com.dod.sport.domain.common;

/**
 * 业务异常，为检查时异常，必须捕获
 * ==包装成本异常需要log记录原始msg==
 * Created by qyang on 14-6-17.
 */
public class BusiException extends RuntimeException {


    /** 异常码 例如： 0010001 业务异常，业务模块01的0001错误（0业务异常、1系统异常）*/
    private String rtnCode;
    /** 对用户友好的错误信息 */
    private String msg;
    /** 错误堆栈信息，便于排查问题 */
    private String developMsg;
    /** 表示这个错误相关的web页面，可以帮助开发人员获取更多的错误的信息 */
    private String uri;

    public BusiException(String rtnCode){
        this.rtnCode = rtnCode;
    }

    public BusiException(String rtnCode, String message) {
        this.rtnCode = rtnCode;
        this.msg = message;
    }

    public BusiException(String rtnCode, String message, String developMsg) {
        this(rtnCode, message);
        this.developMsg = developMsg;
    }

    public BusiException(String rtnCode, String message, Throwable cause) {
        this(rtnCode, message);
        this.developMsg = cause.getMessage();
    }

    public BusiException(String rtnCode, String message, String developMsg, String uri) {
        this(rtnCode, message, developMsg);
        this.uri = uri;
    }



    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDevelopMsg() {
        return developMsg;
    }

    public void setDevelopMsg(String developMsg) {
        this.developMsg = developMsg;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
    public String getRtnCode() {
        return rtnCode;
    }

    public void setRtnCode(String rtnCode) {
        this.rtnCode = rtnCode;
    }
    @Override
    public String toString() {
        return "BusiException{" +
                "rtnCode='" + rtnCode + '\'' +
                ", msg='" + msg + '\'' +
                ", developMsg='" + developMsg + '\'' +
                ", uri='" + uri + '\'' +
                '}';
    }
}
