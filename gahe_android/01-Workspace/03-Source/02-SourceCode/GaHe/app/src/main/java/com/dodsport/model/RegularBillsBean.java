package com.dodsport.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/13.
 * 转正单实体类
 */

public class RegularBillsBean implements Serializable {

    /**
     * datas : {"regularBills":{"approveDate":"","approveDesc":"","approveStatus":"1","approver":"ddcbc213-3145-4b5d-a068-144c0b06706f","billId":"ZZB170913000038","billType":"5","businessId":"f28bcf52-09f9-4fdf-8c86-c2dcf915b41c","createTime":"2017-09-13 10:37:13.0","depName":"","description":"","duration":"","employeeId":"ddcbc213-3145-4b5d-a068-144c0b06706f","employeeName":"张三","endDay":"","endTime":"","entryDate":"","id":"1cc416a3-9259-4ab0-a6cc-789c3a6b46df","positionDesc":"测试","positionName":"","startDay":"","startTime":"","storeId":"57f22b9c-eb5c-45dc-8493-3203217aae3f","storeName":"","suggestion":"call了了了","vacationType":"","workDesc":"阿狸"}}
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

    public RegularBillsBean() {
    }

    @Override
    public String toString() {
        return "RegularBillsBean{" +
                "datas=" + datas +
                ", result=" + result +
                '}';
    }

    public static class DatasBean implements Serializable{
        /**
         * regularBills : {"approveDate":"","approveDesc":"","approveStatus":"1","approver":"ddcbc213-3145-4b5d-a068-144c0b06706f","billId":"ZZB170913000038","billType":"5","businessId":"f28bcf52-09f9-4fdf-8c86-c2dcf915b41c","createTime":"2017-09-13 10:37:13.0","depName":"","description":"","duration":"","employeeId":"ddcbc213-3145-4b5d-a068-144c0b06706f","employeeName":"张三","endDay":"","endTime":"","entryDate":"","id":"1cc416a3-9259-4ab0-a6cc-789c3a6b46df","positionDesc":"测试","positionName":"","startDay":"","startTime":"","storeId":"57f22b9c-eb5c-45dc-8493-3203217aae3f","storeName":"","suggestion":"call了了了","vacationType":"","workDesc":"阿狸"}
         */

        private RegularBills regularBills;

        public RegularBills getRegularBills() {
            return regularBills;
        }

        public void setRegularBills(RegularBills regularBills) {
            this.regularBills = regularBills;
        }

        public static class RegularBills implements Serializable{
            /**
             * approveDate :
             * approveDesc :
             * approveStatus : 1
             * approver : ddcbc213-3145-4b5d-a068-144c0b06706f
             * billId : ZZB170913000038
             * billType : 5
             * businessId : f28bcf52-09f9-4fdf-8c86-c2dcf915b41c
             * createTime : 2017-09-13 10:37:13.0
             * depName :
             * description :
             * duration :
             * employeeId : ddcbc213-3145-4b5d-a068-144c0b06706f
             * employeeName : 张三
             * endDay :
             * endTime :
             * entryDate :
             * id : 1cc416a3-9259-4ab0-a6cc-789c3a6b46df
             * positionDesc : 测试
             * positionName :
             * startDay :
             * startTime :
             * storeId : 57f22b9c-eb5c-45dc-8493-3203217aae3f
             * storeName :
             * suggestion : call了了了
             * vacationType :
             * workDesc : 阿狸
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
            private String employeeId;
            private String employeeName;
            private String endDay;
            private String endTime;
            private String entryDate;
            private String id;
            private String positionDesc;
            private String positionName;
            private String startDay;
            private String startTime;
            private String storeId;
            private String storeName;
            private String suggestion;
            private String vacationType;
            private String workDesc;

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

            public String getPositionDesc() {
                return positionDesc;
            }

            public void setPositionDesc(String positionDesc) {
                this.positionDesc = positionDesc;
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

            public String getStoreName() {
                return storeName;
            }

            public void setStoreName(String storeName) {
                this.storeName = storeName;
            }

            public String getSuggestion() {
                return suggestion;
            }

            public void setSuggestion(String suggestion) {
                this.suggestion = suggestion;
            }

            public String getVacationType() {
                return vacationType;
            }

            public void setVacationType(String vacationType) {
                this.vacationType = vacationType;
            }

            public String getWorkDesc() {
                return workDesc;
            }

            public void setWorkDesc(String workDesc) {
                this.workDesc = workDesc;
            }

            public RegularBills() {
            }

            @Override
            public String toString() {
                return "RegularBills{" +
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
                        ", employeeId='" + employeeId + '\'' +
                        ", employeeName='" + employeeName + '\'' +
                        ", endDay='" + endDay + '\'' +
                        ", endTime='" + endTime + '\'' +
                        ", entryDate='" + entryDate + '\'' +
                        ", id='" + id + '\'' +
                        ", positionDesc='" + positionDesc + '\'' +
                        ", positionName='" + positionName + '\'' +
                        ", startDay='" + startDay + '\'' +
                        ", startTime='" + startTime + '\'' +
                        ", storeId='" + storeId + '\'' +
                        ", storeName='" + storeName + '\'' +
                        ", suggestion='" + suggestion + '\'' +
                        ", vacationType='" + vacationType + '\'' +
                        ", workDesc='" + workDesc + '\'' +
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
