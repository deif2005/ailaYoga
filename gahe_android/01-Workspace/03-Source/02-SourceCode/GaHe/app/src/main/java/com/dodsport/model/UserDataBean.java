package com.dodsport.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/22.
 */

public class UserDataBean implements Serializable {


    /**
     * datas : {"busiEmployee":{"birthday":null,"businessAddress":"龙岗龙城广场b栋3801","businessId":"f28bcf52-09f9-4fdf-8c86-c2dcf915b41c","businessList":[],"businessName":"万店瑜伽","coachExists":0,"createTime":"","creator":"","depId":"858520b7-4f10-432b-8bdc-d09f517c5c83","depName":"万店瑜伽部门2","empPicture":"","empRela":1,"empStatus":2,"empType":2,"employeeName":"理老师","employeeSerialId":"00004","entryDate":"2017-08-31 11:11:22.0","id":"b8aad399-3ced-4c0c-80a0-bb9616016ea5","idCard":"350622199211142511","jobTitle":1,"leaveDate":"","password":"","phoneNum":"15102752222","positionId":"e9f99e8e-7b7e-4a1c-9b85-aed441471501","positionName":"万店瑜伽中级老师","selfIntroduction":"","sex":2,"status":1,"storeAddress":"深圳市龙岗区吉祥地铁站旁万科大厦1101","storeId":"57f22b9c-eb5c-45dc-8493-3203217aae3f","storeName":"万店瑜伽分馆2","workDuration":"0.0"}}
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

    public UserDataBean() {
    }

    @Override
    public String toString() {
        return "UserDataBean{" +
                "datas=" + datas +
                ", result=" + result +
                '}';
    }

    public static class DatasBean implements Serializable {
        /**
         * busiEmployee : {"birthday":null,"businessAddress":"龙岗龙城广场b栋3801","businessId":"f28bcf52-09f9-4fdf-8c86-c2dcf915b41c","businessList":[],"businessName":"万店瑜伽","coachExists":0,"createTime":"","creator":"","depId":"858520b7-4f10-432b-8bdc-d09f517c5c83","depName":"万店瑜伽部门2","empPicture":"","empRela":1,"empStatus":2,"empType":2,"employeeName":"理老师","employeeSerialId":"00004","entryDate":"2017-08-31 11:11:22.0","id":"b8aad399-3ced-4c0c-80a0-bb9616016ea5","idCard":"350622199211142511","jobTitle":1,"leaveDate":"","password":"","phoneNum":"15102752222","positionId":"e9f99e8e-7b7e-4a1c-9b85-aed441471501","positionName":"万店瑜伽中级老师","selfIntroduction":"","sex":2,"status":1,"storeAddress":"深圳市龙岗区吉祥地铁站旁万科大厦1101","storeId":"57f22b9c-eb5c-45dc-8493-3203217aae3f","storeName":"万店瑜伽分馆2","workDuration":"0.0"}
         */

        private BusiEmployeeBean busiEmployee;

        public BusiEmployeeBean getBusiEmployee() {
            return busiEmployee;
        }

        public void setBusiEmployee(BusiEmployeeBean busiEmployee) {
            this.busiEmployee = busiEmployee;
        }

        public DatasBean() {
        }

        public static class BusiEmployeeBean implements Serializable{
            /**
             * birthday : null
             * businessAddress : 龙岗龙城广场b栋3801
             * businessId : f28bcf52-09f9-4fdf-8c86-c2dcf915b41c
             * businessList : []
             * businessName : 万店瑜伽
             * coachExists : 0
             * createTime :
             * creator :
             * depId : 858520b7-4f10-432b-8bdc-d09f517c5c83
             * depName : 万店瑜伽部门2
             * empPicture :
             * empRela : 1
             * empStatus : 2
             * empType : 2
             * employeeName : 理老师
             * employeeSerialId : 00004
             * entryDate : 2017-08-31 11:11:22.0
             * id : b8aad399-3ced-4c0c-80a0-bb9616016ea5
             * idCard : 350622199211142511
             * jobTitle : 1
             * leaveDate :
             * password :
             * phoneNum : 15102752222
             * positionId : e9f99e8e-7b7e-4a1c-9b85-aed441471501
             * positionName : 万店瑜伽中级老师
             * selfIntroduction :
             * sex : 2
             * status : 1
             * storeAddress : 深圳市龙岗区吉祥地铁站旁万科大厦1101
             * storeId : 57f22b9c-eb5c-45dc-8493-3203217aae3f
             * storeName : 万店瑜伽分馆2
             * workDuration : 0.0
             */

            private Object birthday;
            private String businessAddress;
            private String businessId;
            private String businessName;
            private int coachExists;
            private String createTime;
            private String creator;
            private String depId;
            private String depName;
            private String empPicture;
            private int empRela;
            private int empStatus;
            private int empType;
            private String employeeName;
            private String employeeSerialId;
            private String entryDate;
            private String id;
            private String idCard;
            private int jobTitle;
            private String leaveDate;
            private String password;
            private String phoneNum;
            private String positionId;
            private String positionName;
            private String selfIntroduction;
            private int sex;
            private int status;
            private String storeAddress;
            private String storeId;
            private String storeName;
            private String workDuration;
            private List<?> businessList;

            public Object getBirthday() {
                return birthday;
            }

            public void setBirthday(Object birthday) {
                this.birthday = birthday;
            }

            public String getBusinessAddress() {
                return businessAddress;
            }

            public void setBusinessAddress(String businessAddress) {
                this.businessAddress = businessAddress;
            }

            public String getBusinessId() {
                return businessId;
            }

            public void setBusinessId(String businessId) {
                this.businessId = businessId;
            }

            public String getBusinessName() {
                return businessName;
            }

            public void setBusinessName(String businessName) {
                this.businessName = businessName;
            }

            public int getCoachExists() {
                return coachExists;
            }

            public void setCoachExists(int coachExists) {
                this.coachExists = coachExists;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getCreator() {
                return creator;
            }

            public void setCreator(String creator) {
                this.creator = creator;
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

            public String getEmpPicture() {
                return empPicture;
            }

            public void setEmpPicture(String empPicture) {
                this.empPicture = empPicture;
            }

            public int getEmpRela() {
                return empRela;
            }

            public void setEmpRela(int empRela) {
                this.empRela = empRela;
            }

            public int getEmpStatus() {
                return empStatus;
            }

            public void setEmpStatus(int empStatus) {
                this.empStatus = empStatus;
            }

            public int getEmpType() {
                return empType;
            }

            public void setEmpType(int empType) {
                this.empType = empType;
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

            public int getJobTitle() {
                return jobTitle;
            }

            public void setJobTitle(int jobTitle) {
                this.jobTitle = jobTitle;
            }

            public String getLeaveDate() {
                return leaveDate;
            }

            public void setLeaveDate(String leaveDate) {
                this.leaveDate = leaveDate;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getPhoneNum() {
                return phoneNum;
            }

            public void setPhoneNum(String phoneNum) {
                this.phoneNum = phoneNum;
            }

            public String getPositionId() {
                return positionId;
            }

            public void setPositionId(String positionId) {
                this.positionId = positionId;
            }

            public String getPositionName() {
                return positionName;
            }

            public void setPositionName(String positionName) {
                this.positionName = positionName;
            }

            public String getSelfIntroduction() {
                return selfIntroduction;
            }

            public void setSelfIntroduction(String selfIntroduction) {
                this.selfIntroduction = selfIntroduction;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getStoreAddress() {
                return storeAddress;
            }

            public void setStoreAddress(String storeAddress) {
                this.storeAddress = storeAddress;
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

            public String getWorkDuration() {
                return workDuration;
            }

            public void setWorkDuration(String workDuration) {
                this.workDuration = workDuration;
            }

            public List<?> getBusinessList() {
                return businessList;
            }

            public void setBusinessList(List<?> businessList) {
                this.businessList = businessList;
            }

            public BusiEmployeeBean() {
            }

            @Override
            public String toString() {
                return "BusiEmployeeBean{" +
                        "birthday=" + birthday +
                        ", businessAddress='" + businessAddress + '\'' +
                        ", businessId='" + businessId + '\'' +
                        ", businessName='" + businessName + '\'' +
                        ", coachExists=" + coachExists +
                        ", createTime='" + createTime + '\'' +
                        ", creator='" + creator + '\'' +
                        ", depId='" + depId + '\'' +
                        ", depName='" + depName + '\'' +
                        ", empPicture='" + empPicture + '\'' +
                        ", empRela=" + empRela +
                        ", empStatus=" + empStatus +
                        ", empType=" + empType +
                        ", employeeName='" + employeeName + '\'' +
                        ", employeeSerialId='" + employeeSerialId + '\'' +
                        ", entryDate='" + entryDate + '\'' +
                        ", id='" + id + '\'' +
                        ", idCard='" + idCard + '\'' +
                        ", jobTitle=" + jobTitle +
                        ", leaveDate='" + leaveDate + '\'' +
                        ", password='" + password + '\'' +
                        ", phoneNum='" + phoneNum + '\'' +
                        ", positionId='" + positionId + '\'' +
                        ", positionName='" + positionName + '\'' +
                        ", selfIntroduction='" + selfIntroduction + '\'' +
                        ", sex=" + sex +
                        ", status=" + status +
                        ", storeAddress='" + storeAddress + '\'' +
                        ", storeId='" + storeId + '\'' +
                        ", storeName='" + storeName + '\'' +
                        ", workDuration='" + workDuration + '\'' +
                        ", businessList=" + businessList +
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
