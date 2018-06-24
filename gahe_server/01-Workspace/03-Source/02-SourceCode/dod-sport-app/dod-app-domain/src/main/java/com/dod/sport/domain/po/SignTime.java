package com.dod.sport.domain.po;

import java.io.Serializable;

/**
 * 设置签到/签退时间
 * Created by Administrator on 2017/8/30.
 */
public class SignTime implements Serializable {

    private static final long serialVersionUID = -5832616262600241054L;
    private String id;
    private Integer schedulingType;  //排班类型1:早班;2:晚班
    private String storeId;         // 门店id
    private String firstTime;       // 签到时间
    private String lastTime;        // 签退时间
    private String timeScope;       //打卡时间范围:单位为:分钟,默认为30分钟
    private String creator;         // 创建人
    private String createTime;      // 创建时间

    public Integer getSchedulingType() {
        return schedulingType;
    }

    public void setSchedulingType(Integer schedulingType) {
        this.schedulingType = schedulingType;
    }

    public String getTimeScope() {return timeScope;}

    public void setTimeScope(String timeScope) {this.timeScope = timeScope;}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(String firstTime) {
        this.firstTime = firstTime;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
