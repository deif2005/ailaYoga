package com.dodsport.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/29.
 *
 * 请假单、休假单历史记录
 */

public class BIllDetailBean implements Serializable {


    private List<BillsBean> bills;

    public List<BillsBean> getBills() {
        return bills;
    }

    public void setBills(List<BillsBean> bills) {
        this.bills = bills;
    }

    public BIllDetailBean() {
    }

    @Override
    public String toString() {
        return "BIllDetailBean{" +
                "bills=" + bills +
                '}';
    }

    public static class BillsBean implements Serializable {


        /**
         * account : 0.0
         * approveDate :
         * approveDesc :
         * approveStatus : 1
         * approver :
         * billId : QJB170823000025
         * billType : 1
         * businessId :
         * createTime : 2017-08-23 14:31:11.0
         * depName :
         * description :
         * duration : 3.2
         * employeeId : bd3224ac-66b8-4549-8362-65231a82208a
         * employeeName : 刘高春
         * endDay :
         * endTime : 2017-08-19 08:00:00.0
         * entryPositionName : 销售顾问
         * id : 01ac7060-1443-42a7-b851-43ca02130efe
         * positionName : 中级教练
         * startDay :
         * startTime : 2017-08-17 08:00:00.0
         * storeId :
         * transferPositionName :
         * vacationType : 1
         */

        private String account;
        private String approveDate;
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
        private String endDay;
        private String endTime;
        private String entryPositionName;
        private String id;
        private String positionName;
        private String startDay;
        private String startTime;
        private String storeId;
        private String transferPositionName;
        private String vacationType;
        private String entryDate;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getApproveDate() {
            return approveDate;
        }

        public void setApproveDate(String approveDate) {
            this.approveDate = approveDate;
        }

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

        public String getEntryPositionName() {
            return entryPositionName;
        }

        public void setEntryPositionName(String entryPositionName) {
            this.entryPositionName = entryPositionName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPositionName() {
            return positionName;
        }

        public void setPositionName(String positionName) {
            this.positionName = positionName;
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

        public String getTransferPositionName() {
            return transferPositionName;
        }

        public void setTransferPositionName(String transferPositionName) {
            this.transferPositionName = transferPositionName;
        }

        public String getVacationType() {
            return vacationType;
        }

        public void setVacationType(String vacationType) {
            this.vacationType = vacationType;
        }

        public String getEntryDate() {
            return entryDate;
        }

        public void setEntryDate(String entryDate) {
            this.entryDate = entryDate;
        }

        public BillsBean() {
        }

        @Override
        public String toString() {
            return "BillsBean{" +
                    "account='" + account + '\'' +
                    ", approveDate='" + approveDate + '\'' +
                    ", approveDesc='" + approveDesc + '\'' +
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
                    ", endDay='" + endDay + '\'' +
                    ", endTime='" + endTime + '\'' +
                    ", entryPositionName='" + entryPositionName + '\'' +
                    ", id='" + id + '\'' +
                    ", positionName='" + positionName + '\'' +
                    ", startDay='" + startDay + '\'' +
                    ", startTime='" + startTime + '\'' +
                    ", storeId='" + storeId + '\'' +
                    ", transferPositionName='" + transferPositionName + '\'' +
                    ", vacationType='" + vacationType + '\'' +
                    ", entryDate='" + entryDate + '\'' +
                    '}';
        }
    }
}
