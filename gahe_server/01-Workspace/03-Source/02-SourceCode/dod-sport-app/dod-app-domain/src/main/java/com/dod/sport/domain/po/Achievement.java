package com.dod.sport.domain.po;

import java.io.Serializable;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Administrator
 * \* Date: 2017/9/19
 * \* Description: 考勤
 * \
 */
public class Achievement implements Serializable {

    private  String id;
    private  String storeId;    //门店Id
    private  String achievement;
    private  String commission;

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

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }
}