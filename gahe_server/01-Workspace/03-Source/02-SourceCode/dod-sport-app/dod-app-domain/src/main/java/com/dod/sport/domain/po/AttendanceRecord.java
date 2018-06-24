package com.dod.sport.domain.po;

import java.io.Serializable;
import java.util.List;

/**
 * \* Date: 2017/10/25
 * \* Description:考勤记录
 * \
 */
public class AttendanceRecord implements Serializable {

    private String depName;                //部门名称
    private String positionName;                //职位名称
    private String id;                  //员工关系id
    private String employeeSerialId;    //员工编号
    private String empId;               //员工id
    private String depId;               //部门id
    private String storeId;             //门店id
    private String employeeName;                //员工名称
    private String lasttimes;                   //迟到次数
    private String leavetimes;                  //早退次数
    private String failurePunch;                //未打卡次数
    private String empHead;                     //员工头像
    private String signType;                          //签到类型

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getEmpHead() {
        return empHead;
    }

    public void setEmpHead(String empHead) {
        this.empHead = empHead;
    }

    public String getLasttimes() {
        return lasttimes;
    }

    public void setLasttimes(String lasttimes) {
        this.lasttimes = lasttimes;
    }

    public String getLeavetimes() {
        return leavetimes;
    }

    public void setLeavetimes(String leavetimes) {
        this.leavetimes = leavetimes;
    }

    public String getFailurePunch() {
        return failurePunch;
    }

    public void setFailurePunch(String failurePunch) {
        this.failurePunch = failurePunch;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeSerialId() {
        return employeeSerialId;
    }

    public void setEmployeeSerialId(String employeeSerialId) {
        this.employeeSerialId = employeeSerialId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}