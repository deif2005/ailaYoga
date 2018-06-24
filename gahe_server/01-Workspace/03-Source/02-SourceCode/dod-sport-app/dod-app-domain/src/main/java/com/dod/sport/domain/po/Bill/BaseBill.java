package com.dod.sport.domain.po.Bill;

import java.io.Serializable;

/**
 * Created by defi on 2017-08-09.
 * 基础单据pojo
 * File->setting->Inspections->Serializationissues
 */
public class BaseBill implements Serializable {
    private static final long serialVersionUID = -201604831622080982L;

    private String id;
    private String billId;             //单据编号
    private String billType;           //单据类型:1请假单，2休假单，3,入职单，4,离职单，5调岗单，6转正单,7报销单
    private String businessId;         //商家id
    private String storeId;            //门店id
    private String depName;            //部门名称
    private String employeeId;         //单据填写人id
    private String description;        //事由
    private String approver;           //审批人id
    private String approveDate;        //审批时间
    private String approveStatus;      //审批状态：1未审批，2已批准，3已拒绝
    private String approveDesc;        //审批意见
    private String createTime;         //创建时间
    private String startTime;          //开始时间
    private String startDay;           //开始时间天
    private String endTime;            //结束时间
    private String endDay;             //结束时间天
    private String duration;           //时长
    private String employeeName;       //员工姓名
    private String vacationType;       //
    private String positionName;       //职位名称
    private String entryDate;          //入职日期
    private String storeName;          //门店名称
    private String phoneNum;           //员工电话
    private String sex;                //员工性别
    private String idCard;             //员工省份证
    private String employeeSerialId;   //员工工号
    private String empRela;            //雇佣状态：1正式员工，2.非正式员工，3解聘员工，4其它
    private String empType;            //聘用关系：1全职，2兼职
    private String isCoach;            //是否是教练:1.否;2.是
    private String jobTitle;           //教练职称;1:初级教练;2:中级教练;3:高级教练;9.无职称;
    private String selfIntroduction;   //员工简介

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getPositionName() {return positionName;}

    public void setPositionName(String positionName) {this.positionName = positionName;}

    public String getVacationType() {return vacationType;}

    public void setVacationType(String vacationType) {this.vacationType = vacationType;}

    public String getEmployeeName() {return employeeName;}

    public void setEmployeeName(String employeeName) {this.employeeName = employeeName;}

    public String getEndDay() {return endDay;}

    public void setEndDay(String endDay) {this.endDay = endDay;}

    public String getStartDay() {return startDay;}

    public void setStartDay(String startDay) {this.startDay = startDay;}

    public String getDuration() {return duration;}

    public void setDuration(String duration) {this.duration = duration;}

    public String getEndTime() {return endTime;}

    public void setEndTime(String endTime) {this.endTime = endTime;}

    public String getStartTime() {return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getBillId() {
        return billId;
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

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
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

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getApproveDesc() {
        return approveDesc;
    }

    public void setApproveDesc(String approveDesc) {
        this.approveDesc = approveDesc;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getEmployeeSerialId() {
        return employeeSerialId;
    }

    public void setEmployeeSerialId(String employeeSerialId) {
        this.employeeSerialId = employeeSerialId;
    }

    public String getEmpRela() {
        return empRela;
    }

    public void setEmpRela(String empRela) {
        this.empRela = empRela;
    }

    public String getEmpType() {
        return empType;
    }

    public void setEmpType(String empType) {
        this.empType = empType;
    }

    public String getIsCoach() {
        return isCoach;
    }

    public void setIsCoach(String isCoach) {
        this.isCoach = isCoach;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getSelfIntroduction() {
        return selfIntroduction;
    }

    public void setSelfIntroduction(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }
}
