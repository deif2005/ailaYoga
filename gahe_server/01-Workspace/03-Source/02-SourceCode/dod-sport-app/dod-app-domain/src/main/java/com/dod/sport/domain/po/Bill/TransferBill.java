package com.dod.sport.domain.po.Bill;

import com.dod.sport.domain.po.Bill.BaseBill;

/**
 * Created by defi on 2017-08-16.
 * 调岗单pojo
 */
public class TransferBill extends BaseBill {
    private String depId;             //所在部门
    private String empId;             //员工编号
    private String idCard;            //证件号
    private String positionName;      //职位名称
    private String entryDate;         //入职日期
    private String workDuration;      //工龄
    private String transferReason;    //调岗原因
    private String transferDate;      //调岗日期
    private String transferPositionId;    //调岗后职位
    private String transferDepId;    //调岗后部门
    private String transferDepName;  //调岗后部门名称
    private String transferPositionName;  //调岗后职位名称

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getWorkDuration() {
        return workDuration;
    }

    public void setWorkDuration(String workDuration) {
        this.workDuration = workDuration;
    }

    public String getTransferReason() {
        return transferReason;
    }

    public void setTransferReason(String transferReason) {
        this.transferReason = transferReason;
    }

    public String getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }

    public String getTransferPositionId() {
        return transferPositionId;
    }

    public void setTransferPositionId(String transferPositionId) {
        this.transferPositionId = transferPositionId;
    }

    public String getTransferDepId() {
        return transferDepId;
    }

    public void setTransferDepId(String transferDepId) {
        this.transferDepId = transferDepId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getTransferDepName() {
        return transferDepName;
    }

    public void setTransferDepName(String transferDepName) {
        this.transferDepName = transferDepName;
    }

    public String getTransferPositionName() {
        return transferPositionName;
    }

    public void setTransferPositionName(String transferPositionName) {
        this.transferPositionName = transferPositionName;
    }
}
