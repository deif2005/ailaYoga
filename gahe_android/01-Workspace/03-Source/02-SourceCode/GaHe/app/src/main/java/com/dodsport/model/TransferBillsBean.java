package com.dodsport.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/11.
 */

public class TransferBillsBean implements Serializable {


    /**
     * datas : {"transferBill":{"approveDate":"","approveDesc":"","approveStatus":"1","approver":"","billId":"DGB170911000015","billType":"","businessId":"f28bcf52-09f9-4fdf-8c86-c2dcf915b41c","createTime":"","depId":"","depName":"万店瑜伽部门1","description":"","duration":"","empId":"","employeeId":"bdc13c70-de9b-47e0-b59d-ea90e8926ee9","employeeName":"张高春","endDay":"","endTime":"","entryDate":"2017-09-03 15:03:53.0","id":"15ab635a-a589-4e8f-ac0f-d4a8f5999cf4","idCard":"362422199108114033","positionName":"万店瑜伽高级教练","startDay":"","startTime":"","storeId":"ee810841-977e-432d-9a8e-ee660563b302","transferDate":"2017-09-10 00:00:00.0","transferDepId":"","transferDepName":"万店瑜伽部门3","transferPositionId":"","transferPositionName":"万店瑜伽老板","transferReason":"","vacationType":"","workDuration":"0.0"}}
     * result : {"code":"0","msg":"sys_ok"}
     */

    private DatasBean datas;
    private ResultBean result;

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public TransferBillsBean() {
    }

    public static class DatasBean implements Serializable {
        /**
         * transferBill : {"approveDate":"","approveDesc":"","approveStatus":"1","approver":"","billId":"DGB170911000015","billType":"","businessId":"f28bcf52-09f9-4fdf-8c86-c2dcf915b41c","createTime":"","depId":"","depName":"万店瑜伽部门1","description":"","duration":"","empId":"","employeeId":"bdc13c70-de9b-47e0-b59d-ea90e8926ee9","employeeName":"张高春","endDay":"","endTime":"","entryDate":"2017-09-03 15:03:53.0","id":"15ab635a-a589-4e8f-ac0f-d4a8f5999cf4","idCard":"362422199108114033","positionName":"万店瑜伽高级教练","startDay":"","startTime":"","storeId":"ee810841-977e-432d-9a8e-ee660563b302","transferDate":"2017-09-10 00:00:00.0","transferDepId":"","transferDepName":"万店瑜伽部门3","transferPositionId":"","transferPositionName":"万店瑜伽老板","transferReason":"","vacationType":"","workDuration":"0.0"}
         */

        private TransferBills transferBill;

        public TransferBills getTransferBill() {
            return transferBill;
        }

        public void setTransferBill(TransferBills transferBill) {
            this.transferBill = transferBill;
        }

        public DatasBean() {
        }

        @Override
        public String toString() {
            return "DatasBean{" +
                    "transferBill=" + transferBill +
                    '}';
        }

        public static class TransferBills implements Serializable {
            /**
             * approveDate :
             * approveDesc :
             * approveStatus : 1
             * approver :
             * billId : DGB170911000015
             * billType :
             * businessId : f28bcf52-09f9-4fdf-8c86-c2dcf915b41c
             * createTime :
             * depId :
             * depName : 万店瑜伽部门1
             * description :
             * duration :
             * empId :
             * employeeId : bdc13c70-de9b-47e0-b59d-ea90e8926ee9
             * employeeName : 张高春
             * endDay :
             * endTime :
             * entryDate : 2017-09-03 15:03:53.0
             * id : 15ab635a-a589-4e8f-ac0f-d4a8f5999cf4
             * idCard : 362422199108114033
             * positionName : 万店瑜伽高级教练
             * startDay :
             * startTime :
             * storeId : ee810841-977e-432d-9a8e-ee660563b302
             * transferDate : 2017-09-10 00:00:00.0
             * transferDepId :
             * transferDepName : 万店瑜伽部门3
             * transferPositionId :
             * transferPositionName : 万店瑜伽老板
             * transferReason :
             * vacationType :
             * workDuration : 0.0
             */

            private String approveDate;
            private String approveDesc;
            private String approveStatus;
            private String approver;
            private String billId;
            private String billType;
            private String businessId;
            private String createTime;
            private String depId;
            private String depName;
            private String description;
            private String duration;
            private String empId;
            private String employeeId;
            private String employeeName;
            private String endDay;
            private String endTime;
            private String entryDate;
            private String id;
            private String idCard;
            private String positionName;
            private String startDay;
            private String startTime;
            private String storeId;
            private String transferDate;
            private String transferDepId;
            private String transferDepName;
            private String transferPositionId;
            private String transferPositionName;
            private String transferReason;
            private String vacationType;
            private String workDuration;
            private String storeName;

            public TransferBills(){

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

            public String getDepId() {
                return depId;
            }

            public void setDepId(String depId) {
                this.depId = depId;
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

            public String getEmpId() {
                return empId;
            }

            public void setEmpId(String empId) {
                this.empId = empId;
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

            public String getEntryDate() {
                return entryDate;
            }

            public void setEntryDate(String entryDate) {
                this.entryDate = entryDate;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getIdCard() {
                return idCard;
            }

            public void setIdCard(String idCard) {
                this.idCard = idCard;
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

            public String getTransferDate() {
                return transferDate;
            }

            public void setTransferDate(String transferDate) {
                this.transferDate = transferDate;
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

            public String getTransferPositionId() {
                return transferPositionId;
            }

            public void setTransferPositionId(String transferPositionId) {
                this.transferPositionId = transferPositionId;
            }

            public String getTransferPositionName() {
                return transferPositionName;
            }

            public void setTransferPositionName(String transferPositionName) {
                this.transferPositionName = transferPositionName;
            }

            public String getTransferReason() {
                return transferReason;
            }

            public void setTransferReason(String transferReason) {
                this.transferReason = transferReason;
            }

            public String getVacationType() {
                return vacationType;
            }

            public void setVacationType(String vacationType) {
                this.vacationType = vacationType;
            }

            public String getWorkDuration() {
                return workDuration;
            }

            public void setWorkDuration(String workDuration) {
                this.workDuration = workDuration;
            }

            public String getStoreName() {
                return storeName;
            }

            public void setStoreName(String storeName) {
                this.storeName = storeName;
            }

            @Override
            public String toString() {
                return "TransferBills{" +
                        "approveDate='" + approveDate + '\'' +
                        ", approveDesc='" + approveDesc + '\'' +
                        ", approveStatus='" + approveStatus + '\'' +
                        ", approver='" + approver + '\'' +
                        ", billId='" + billId + '\'' +
                        ", billType='" + billType + '\'' +
                        ", businessId='" + businessId + '\'' +
                        ", createTime='" + createTime + '\'' +
                        ", depId='" + depId + '\'' +
                        ", depName='" + depName + '\'' +
                        ", description='" + description + '\'' +
                        ", duration='" + duration + '\'' +
                        ", empId='" + empId + '\'' +
                        ", employeeId='" + employeeId + '\'' +
                        ", employeeName='" + employeeName + '\'' +
                        ", endDay='" + endDay + '\'' +
                        ", endTime='" + endTime + '\'' +
                        ", entryDate='" + entryDate + '\'' +
                        ", id='" + id + '\'' +
                        ", idCard='" + idCard + '\'' +
                        ", positionName='" + positionName + '\'' +
                        ", startDay='" + startDay + '\'' +
                        ", startTime='" + startTime + '\'' +
                        ", storeId='" + storeId + '\'' +
                        ", transferDate='" + transferDate + '\'' +
                        ", transferDepId='" + transferDepId + '\'' +
                        ", transferDepName='" + transferDepName + '\'' +
                        ", transferPositionId='" + transferPositionId + '\'' +
                        ", transferPositionName='" + transferPositionName + '\'' +
                        ", transferReason='" + transferReason + '\'' +
                        ", vacationType='" + vacationType + '\'' +
                        ", workDuration='" + workDuration + '\'' +
                        ", storeName='" + storeName + '\'' +
                        '}';
            }
        }



    }

    public static class ResultBean implements Serializable {
        /**
         * code : 0
         * msg : sys_ok
         */

        private String code;
        private String msg;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public ResultBean() {
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "code='" + code + '\'' +
                    ", msg='" + msg + '\'' +
                    '}';
        }
    }
}
