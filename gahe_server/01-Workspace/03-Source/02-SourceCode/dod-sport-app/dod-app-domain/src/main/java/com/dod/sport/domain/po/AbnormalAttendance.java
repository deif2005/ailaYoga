package com.dod.sport.domain.po;

/**

 * \* Date: 2017/10/26
 * \* Description:考勤异常记录
 * \
 */
public class AbnormalAttendance extends  AttendanceRecord {

    private String[] earlyTime;                //早退时间
    private String[] lateTime;                 //迟到时间
    private String[] notSign;                 //未签到时间

    public String[] getEarlyTime() {
        return earlyTime;
    }

    public void setEarlyTime(String[] earlyTime) {
        this.earlyTime = earlyTime;
    }

    public String[] getLateTime() {
        return lateTime;
    }

    public void setLateTime(String[] lateTime) {
        this.lateTime = lateTime;
    }

    public String[] getNotSign() {
        return notSign;
    }

    public void setNotSign(String[] notSign) {
        this.notSign = notSign;
    }
}