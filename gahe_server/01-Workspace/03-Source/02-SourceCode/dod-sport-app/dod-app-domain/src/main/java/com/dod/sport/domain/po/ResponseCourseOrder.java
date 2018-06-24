package com.dod.sport.domain.po;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/24.
 */
public class ResponseCourseOrder implements Serializable {
    private static final long serialVersionUID = 1820782165542944256L;
    private String orderTimes;     //已预约次数
    private String signTimes;      //签到次数
    private String breakOrderTimes ; //爽月次数
    private String orderTotal;      //预约总数
    private List<BackCourseOrderInfo> backCourseOrderInfos;

    public List<BackCourseOrderInfo> getBackCourseOrderInfos() {
        return backCourseOrderInfos;
    }

    public void setBackCourseOrderInfos(List<BackCourseOrderInfo> backCourseOrderInfos) {
        this.backCourseOrderInfos = backCourseOrderInfos;
    }

    public String getOrderTimes() {
        return orderTimes;
    }

    public void setOrderTimes(String orderTimes) {
        this.orderTimes = orderTimes;
    }

    public String getSignTimes() {
        return signTimes;
    }

    public void setSignTimes(String signTimes) {
        this.signTimes = signTimes;
    }

    public String getBreakOrderTimes() {
        return breakOrderTimes;
    }

    public void setBreakOrderTimes(String breakOrderTimes) {
        this.breakOrderTimes = breakOrderTimes;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }
}
