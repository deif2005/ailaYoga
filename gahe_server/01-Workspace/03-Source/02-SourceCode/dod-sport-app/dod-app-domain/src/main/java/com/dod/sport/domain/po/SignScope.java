package com.dod.sport.domain.po;

import java.io.Serializable;

/**
 * 打卡范围设置
 * Created by Administrator on 2017/9/2.
 */
public class SignScope implements Serializable {
    private static final long serialVersionUID = -1757971157363173839L;
    private String id;
    private String storeId;          //门店id
    private String  radius;              //打卡半径范围 ,单位为米
    private String vacationDays;     //可休假天数
    private String reissueTimes;    //可补卡次数
    private String signAdd;         //签到地址
    private String lng;             //经度
    private String lat;             //纬度
    private String creator;          //创建人
    private String createTime;       //创建时间

    public String getSignAdd() {
        return signAdd;
    }

    public void setSignAdd(String signAdd) {
        this.signAdd = signAdd;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getVacationDays() {
        return vacationDays;
    }

    public void setVacationDays(String vacationDays) {
        this.vacationDays = vacationDays;
    }

    public String getReissueTimes() {
        return reissueTimes;
    }

    public void setReissueTimes(String reissueTimes) {
        this.reissueTimes = reissueTimes;
    }

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

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
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
