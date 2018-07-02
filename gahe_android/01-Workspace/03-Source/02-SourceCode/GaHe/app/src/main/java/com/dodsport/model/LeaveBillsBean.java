package com.dodsport.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/13.
 *
 * 离职单 实体类
 */

public class LeaveBillsBean implements Serializable{


    /**
     * datas : {"leaveBill":{"approveDate":"","approveDesc":"","approveStatus":"1","approver":"2dd87844-6595-4685-af35-6cc916042cae","billId":"LZB170913000039","billType":"","businessId":"f28bcf52-09f9-4fdf-8c86-c2dcf915b41c","createTime":"","depName":"","description":"","duration":"","empId":"00025","employeeId":"2dd87844-6595-4685-af35-6cc916042cae","employeeName":"","endDay":"","endTime":"2017-09-13 16:24:45.0","entryDate":"","handleItem":"阿狸了了","handler":"土豪","handlerName":"","id":"9c2c06bd-22b6-4298-9217-9e917f88a289","leaveReason":"阿狸","positionName":"万店瑜伽高级教练","postionName":"","startDay":"","startTime":"2017-09-13 00:00:00.0","storeId":"ee810841-977e-432d-9a8e-ee660563b302","storeName":"","vacationType":""}}
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

    public LeaveBillsBean() {
    }

    public static class DatasBean implements Serializable{
        /**
         * leaveBill : {"approveDate":"","approveDesc":"","approveStatus":"1","approver":"2dd87844-6595-4685-af35-6cc916042cae","billId":"LZB170913000039","billType":"","businessId":"f28bcf52-09f9-4fdf-8c86-c2dcf915b41c","createTime":"","depName":"","description":"","duration":"","empId":"00025","employeeId":"2dd87844-6595-4685-af35-6cc916042cae","employeeName":"","endDay":"","endTime":"2017-09-13 16:24:45.0","entryDate":"","handleItem":"阿狸了了","handler":"土豪","handlerName":"","id":"9c2c06bd-22b6-4298-9217-9e917f88a289","leaveReason":"阿狸","positionName":"万店瑜伽高级教练","postionName":"","startDay":"","startTime":"2017-09-13 00:00:00.0","storeId":"ee810841-977e-432d-9a8e-ee660563b302","storeName":"","vacationType":""}
         */

        private LeaveBillBean leaveBill;

        public LeaveBillBean getLeaveBill() {
            return leaveBill;
        }

        public void setLeaveBill(LeaveBillBean leaveBill) {
            this.leaveBill = leaveBill;
        }

        public static class LeaveBillBean implements Serializable{
            /**
             * approveDate :
             * approveDesc :
             * approveStatus : 1
             * approver : 2dd87844-6595-4685-af35-6cc916042cae
             * billId : LZB170913000039
             * billType :
             * businessId : f28bcf52-09f9-4fdf-8c86-c2dcf915b41c
             * createTime :
             * depName :
             * description :
             * duration :
             * empId : 00025
             * employeeId : 2dd87844-6595-4685-af35-6cc916042cae
             * employeeName :
             * endDay :
             * endTime : 2017-09-13 16:24:45.0
             * entryDate :
             * handleItem : 阿狸了了
             * handler : 土豪
             * handlerName :
             * id : 9c2c06bd-22b6-4298-9217-9e917f88a289
             * leaveReason : 阿狸
             * positionName : 万店瑜伽高级教练
             * postionName :
             * startDay :
             * startTime : 2017-09-13 00:00:00.0
             * storeId : ee810841-977e-432d-9a8e-ee660563b302
             * storeName :
             * vacationType :
             */

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
            private String empId;
            private String employeeId;
            private String employeeName;
            private String endDay;
            private String endTime;
            private String entryDate;
            private String handleItem;
            private String handler;
            private String handlerName;
            private String id;
            private String leaveReason;
            private String positionName;
            private String postionName;
            private String startDay;
            private String startTime;
            private String storeId;
            private String storeName;
            private String vacationType;

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

            public String getHandleItem() {
                return handleItem;
            }

            public void setHandleItem(String handleItem) {
                this.handleItem = handleItem;
            }

            public String getHandler() {
                return handler;
            }

            public void setHandler(String handler) {
                this.handler = handler;
            }

            public String getHandlerName() {
                return handlerName;
            }

            public void setHandlerName(String handlerName) {
                this.handlerName = handlerName;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLeaveReason() {
                return leaveReason;
            }

            public void setLeaveReason(String leaveReason) {
                this.leaveReason = leaveReason;
            }

            public String getPositionName() {
                return positionName;
            }

            public void setPositionName(String positionName) {
                this.positionName = positionName;
            }

            public String getPostionName() {
                return postionName;
            }

            public void setPostionName(String postionName) {
                this.postionName = postionName;
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

            public String getStoreName() {
                return storeName;
            }

            public void setStoreName(String storeName) {
                this.storeName = storeName;
            }

            public String getVacationType() {
                return vacationType;
            }

            public void setVacationType(String vacationType) {
                this.vacationType = vacationType;
            }

            public LeaveBillBean() {
            }

            @Override
            public String toString() {
                return "LeaveBillBean{" +
                        "approveDate='" + approveDate + '\'' +
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
                        ", empId='" + empId + '\'' +
                        ", employeeId='" + employeeId + '\'' +
                        ", employeeName='" + employeeName + '\'' +
                        ", endDay='" + endDay + '\'' +
                        ", endTime='" + endTime + '\'' +
                        ", entryDate='" + entryDate + '\'' +
                        ", handleItem='" + handleItem + '\'' +
                        ", handler='" + handler + '\'' +
                        ", handlerName='" + handlerName + '\'' +
                        ", id='" + id + '\'' +
                        ", leaveReason='" + leaveReason + '\'' +
                        ", positionName='" + positionName + '\'' +
                        ", postionName='" + postionName + '\'' +
                        ", startDay='" + startDay + '\'' +
                        ", startTime='" + startTime + '\'' +
                        ", storeId='" + storeId + '\'' +
                        ", storeName='" + storeName + '\'' +
                        ", vacationType='" + vacationType + '\'' +
                        '}';
            }
        }
    }

    public static class ResultBean implements Serializable{
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
