package com.dod.sport.domain.dto.socket;

import java.io.Serializable;

/**
 * SocketRequest
 * socket通信请求消息类
 * @author yuhao
 * @date 2016/9/19 19:46
 */
public class SocketResponse implements Serializable {
    private static final long serialVersionUID = 822675877349842266L;

    //请求id
    private String cmdId;

    //请求标识
    private String flag;

    //请求操作
    private String command;

    //响应结果,success/failed
    private String result;

    //请求数据
    private String data;

    public String getCmdId() {
        return cmdId;
    }

    public void setCmdId(String cmdId) {
        this.cmdId = cmdId;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
