package com.dod.sport.domain.po;

/**
 * Created by Administrator on 2017/11/2.
 */
public class ResponsePrivateDateTime {
    private String classTime;  //上课时间段
    private String orderType; //1:可预约;2:不可预约

    public String getClassTime() {
        return classTime;
    }

    public void setClassTime(String classTime) {
        this.classTime = classTime;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
