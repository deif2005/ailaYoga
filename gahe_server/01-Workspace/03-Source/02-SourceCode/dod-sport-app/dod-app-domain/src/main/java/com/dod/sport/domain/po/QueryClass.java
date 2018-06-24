package com.dod.sport.domain.po;

import java.util.List;

/**
 * 请求数据类
 * Created by Administrator on 2017/10/18.
 */
public class QueryClass {
    private String employeeId;
    private String memberId;
    private String courseId;
    private String cardrelationId;
    private String remark;
    private List<PrivateOrderDateTime> privateOrderDateTimes;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCardrelationId() {
        return cardrelationId;
    }

    public void setCardrelationId(String cardrelationId) {
        this.cardrelationId = cardrelationId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<PrivateOrderDateTime> getPrivateOrderDateTimes() {
        return privateOrderDateTimes;
    }

    public void setPrivateOrderDateTimes(List<PrivateOrderDateTime> privateOrderDateTimes) {
        this.privateOrderDateTimes = privateOrderDateTimes;
    }
}
