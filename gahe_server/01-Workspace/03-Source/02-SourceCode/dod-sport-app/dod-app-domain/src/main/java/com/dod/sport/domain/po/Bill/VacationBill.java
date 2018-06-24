package com.dod.sport.domain.po.Bill;

import com.dod.sport.domain.po.Bill.BaseBill;

/**
 * Created by defi on 2017-08-09.
 * 请假/休假单pojo
 */
public class VacationBill extends BaseBill {
    String vacationType;          //请假类型
    String startTime;             //开始时间
    String startDay;              //开始时间天
    String endTime;               //结束时间
    String endDay;               //结束时间天
    String duration;              //时长
    String employeeName;         //员工姓名
    String employeeSerialId;     //员工编号


    public String getEmployeeSerialId() {return employeeSerialId;}

    public void setEmployeeSerialId(String employeeSerialId) {this.employeeSerialId = employeeSerialId;}

    public String getEmployeeName() {return employeeName;}

    public void setEmployeeName(String employeeName) {this.employeeName = employeeName;}

    public String getEndDay() {return endDay;}

    public void setEndDay(String endDay) {this.endDay = endDay;}

    public String getStartDay() {return startDay;}

    public void setStartDay(String startDay) {this.startDay = startDay;}

    public String getVacationType() {
        return vacationType;
    }

    public void setVacationType(String vacationType) {
        this.vacationType = vacationType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
