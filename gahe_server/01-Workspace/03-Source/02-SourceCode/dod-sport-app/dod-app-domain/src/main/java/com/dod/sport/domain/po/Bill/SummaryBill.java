package com.dod.sport.domain.po.Bill;

import com.dod.sport.domain.po.Bill.BaseBill;

/**
 * Created by defi on 2017-08-31.
 * 概要信息pojo
 */
public class SummaryBill extends BaseBill {
    private String employeeName;   //员工姓名
    private String positionName;   //当前岗位
    private String transferPositionName;  //调岗后岗位
    private String entryPositionName;     //入职岗位
    private String account;                //报销金额
    private String transferReason;    //调岗原因
    private String transferDate;      //调岗日期
    private String transferPositionId;    //调岗后职位
    private String transferDepId;    //调岗后部门
    private String transferDepName;  //调岗后部门名称

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

    public String getTransferDepName() {
        return transferDepName;
    }

    public void setTransferDepName(String transferDepName) {
        this.transferDepName = transferDepName;
    }

    public String getAccount() {return account;}

    public void setAccount(String account) {this.account = account;}

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getTransferPositionName() {
        return transferPositionName;
    }

    public void setTransferPositionName(String transferPositionName) {
        this.transferPositionName = transferPositionName;
    }

    public String getEntryPositionName() {
        return entryPositionName;
    }

    public void setEntryPositionName(String entryPositionName) {
        this.entryPositionName = entryPositionName;
    }
}
