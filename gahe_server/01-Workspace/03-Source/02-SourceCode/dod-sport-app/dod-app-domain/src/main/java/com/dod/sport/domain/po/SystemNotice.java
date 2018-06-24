package com.dod.sport.domain.po;

import java.io.Serializable;
import java.security.PrivateKey;

/**
 * Created by defi on 2017-08-28.
 * 系统消息pojo
 */
public class SystemNotice implements Serializable {

    private static final long serialVersionUID = -9214015740304189107L;
    private String id;
    private String newsSerialId;       //消息编号
    private String type;         //消息类型：1系统消息，2,联盟消息，3内部消息
    private String title;        //标题
    private String alert;        //对应ios和android设备的alert信息
    private String content;      //对应系统通知内容，主要针对ios设
    private String sendDatetime; //发送时间
    private String remark;       //备注'
    private String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNewsSerialId() {
        return newsSerialId;
    }

    public void setNewsSerialId(String newsSerialId) {
        this.newsSerialId = newsSerialId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendDatetime() {
        return sendDatetime;
    }

    public void setSendDatetime(String sendDatetime) {
        this.sendDatetime = sendDatetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}