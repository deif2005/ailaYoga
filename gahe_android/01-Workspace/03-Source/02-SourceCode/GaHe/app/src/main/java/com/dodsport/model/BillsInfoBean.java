package com.dodsport.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/29.
 *
 * 单据详情 审批状态详情
 */

public class BillsInfoBean implements Serializable {

    /**
     * approveDesc :
     * approveStatus : 2
     * approver :
     * billId : QJB170830000066
     * billType : 1
     * businessId : 07b45f9b-d2b8-407a-bac6-5267a24b94c2
     * createTime : 2017-08-30 10:48:18.0
     * depName : 市场部
     * description : 擦擦擦啊
     * duration : 49.0
     * employeeId : bd3224ac-66b8-4549-8362-65231a82208a
     * employeeName : 刘高春
     * employeeSerialId : 00003
     * endDay :
     * endTime : 2017-10-20 00:00:00.0
     * id : e69ab4ac-df69-48be-930f-be04533cc82b
     * startDay :
     * startTime : 2017-09-01 00:00:00.0
     * storeId : cec42010-8328-11e7-bb48-e0071bf36af3
     * vacationType : 7
     */

    private String approveDesc;
    private String approveStatus;
    private String approver;
    private String billId;
    private String billType;
    private String businessId;
    private String createTime;
    private String depName;
    private String description;
    private String duration;
    private String employeeId;
    private String employeeName;
    private String employeeSerialId;
    private String endDay;
    private String endTime;
    private String id;
    private String startDay;
    private String startTime;
    private String storeId;
    private String vacationType;

    public String getApproveDesc() {
        return approveDesc;
    }

    public void setApproveDesc(String approveDesc) {
        this.approveDesc = approveDesc;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeSerialId() {
        return employeeSerialId;
    }

    public void setEmployeeSerialId(String employeeSerialId) {
        this.employeeSerialId = employeeSerialId;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getVacationType() {
        return vacationType;
    }

    public void setVacationType(String vacationType) {
        this.vacationType = vacationType;
    }

    public BillsInfoBean() {
    }

    @Override
    public String toString() {
        return "BillsInfoBean{" +
                "approveDesc='" + approveDesc + '\'' +
                ", approveStatus='" + approveStatus + '\'' +
                ", approver='" + approver + '\'' +
                ", billId='" + billId + '\'' +
                ", billType='" + billType + '\'' +
                ", businessId='" + businessId + '\'' +
                ", createTime='" + createTime + '\'' +
                ", depName='" + depName + '\'' +
                ", description='" + description + '\'' +
                ", duration='" + duration + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", employeeSerialId='" + employeeSerialId + '\'' +
                ", endDay='" + endDay + '\'' +
                ", endTime='" + endTime + '\'' +
                ", id='" + id + '\'' +
                ", startDay='" + startDay + '\'' +
                ", startTime='" + startTime + '\'' +
                ", storeId='" + storeId + '\'' +
                ", vacationType='" + vacationType + '\'' +
                '}';
    }
}
