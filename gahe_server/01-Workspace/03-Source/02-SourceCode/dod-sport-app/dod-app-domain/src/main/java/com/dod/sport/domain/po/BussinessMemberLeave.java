package com.dod.sport.domain.po;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Administrator
 * \* Date: 2017/11/7
 * \* Time: 11:41
 * \* To change this template use File | Settings | File Templates.
 * \* Description:商家会员请假基础表
 * \
 */
public class BussinessMemberLeave {

    private String id;
    private String bussinessId;           //商家ID
    private String appointmentDay;      //提前预约课程天数（最长6天）
    private String giveCourse;          //开课前停止预约分钟
    private String leaveFee;            //  '会员请假 费用 （元/月）' ,
    private String duration;             //'请假时长（最大十二个月）'
    private String leaveNumber;          //最多请假次数 (0表示不限次数)

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBussinessId() {
        return bussinessId;
    }

    public void setBussinessId(String bussinessId) {
        this.bussinessId = bussinessId;
    }

    public String getAppointmentDay() {
        return appointmentDay;
    }

    public void setAppointmentDay(String appointmentDay) {
        this.appointmentDay = appointmentDay;
    }

    public String getGiveCourse() {
        return giveCourse;
    }

    public void setGiveCourse(String giveCourse) {
        this.giveCourse = giveCourse;
    }

    public String getLeaveFee() {
        return leaveFee;
    }

    public void setLeaveFee(String leaveFee) {
        this.leaveFee = leaveFee;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLeaveNumber() {
        return leaveNumber;
    }

    public void setLeaveNumber(String leaveNumber) {
        this.leaveNumber = leaveNumber;
    }
}