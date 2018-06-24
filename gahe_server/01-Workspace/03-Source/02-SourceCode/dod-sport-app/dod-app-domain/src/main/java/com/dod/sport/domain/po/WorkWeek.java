package com.dod.sport.domain.po;

import java.io.Serializable;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Administrator
 * \* Date: 2017/9/5
 * \* Time: 14:43
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class WorkWeek implements Serializable {

    private String id;
    private String storeId;      //门店id
    private String weekNum;  //'星期:1.七;2:一;3:二;4:三:5:四;6:五;7:六;' ,
    private String signtimeId; // '签到规则id' ,
    private String weekType; //'1:上班;2:休息' ,
    private String create_time; // '创建时间' ,

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(String weekNum) {
        this.weekNum = weekNum;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getSigntimeId() {
        return signtimeId;
    }

    public void setSigntimeId(String signtimeId) {
        this.signtimeId = signtimeId;
    }

    public String getWeekType() {
        return weekType;
    }

    public void setWeekType(String weekType) {
        this.weekType = weekType;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}