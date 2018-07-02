package com.dodsport.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/25.
 *
 * 个人信息 获取数据
 */

public class ResponseEmployeeBean implements Serializable {

    /**
     * datas : {"responseEmployee":{"birthday":"2017-07-04 15:02:37.0","businessAddress":"","businessId":"80a2aa90-2205-4453-8760-b395fb48746a","businessList":[],"businessName":"","courseList":[],"createTime":"","creator":"","depId":"858520b7-4f10-432b-8bdc-d09f517c5c83","depName":"","empHead":"http://192.168.1.186:8088/dod-static/upload/empPicture/dc3719b2-6f6f-4b38-9736-02e2d4e8936a/75d9e41d-f416-43d5-8358-ddbb540ebefa.jpg","empId":"dc3719b2-6f6f-4b38-9736-02e2d4e8936a","empPicture":"/upload/empPicture/dc3719b2-6f6f-4b38-9736-02e2d4e8936a/8260aba4-a5fc-48ad-a6bd-05ee41d2a715.jpg;/upload/empPicture/dc3719b2-6f6f-4b38-9736-02e2d4e8936a/70943a7b-347f-4419-8d11-6fa427f4647d.jpg","empRela":"1","empStatus":"","empType":"2","employeeName":"张三","employeeSerialId":"00019","entryDate":"2017-09-12 14:57:45.0","failurePunch":"","id":"ddcbc213-3145-4b5d-a068-144c0b06706f","idCard":"36242219981114443X","isCoach":"1","jobTitle":"","lasttimes":"","leaveDate":"","leavetimes":"","phoneNum":"15129982998","picPathList":["D:/work/tools/apache-tomcat-7.0.55-8088/webapps/dod-static/upload/empPicture/dc3719b2-6f6f-4b38-9736-02e2d4e8936a/8260aba4-a5fc-48ad-a6bd-05ee41d2a715.jpg","D:/work/tools/apache-tomcat-7.0.55-8088/webapps/dod-static/upload/empPicture/dc3719b2-6f6f-4b38-9736-02e2d4e8936a/70943a7b-347f-4419-8d11-6fa427f4647d.jpg"],"positionId":"e9f99e8e-7b7e-4a1c-9b85-aed441471501","positionName":"","registerType":"2","roleId":"afed24d9-1283-4e85-b49b-8de42e5d49a3","roleName":"","selfIntroduction":"","sex":"1","status":"1","storeAddress":"","storeId":"5565f926-eb45-47c5-a297-ad2101533b19","storeName":"","userRole":null,"workDuration":""}}
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

    public ResponseEmployeeBean() {
    }

    public static class DatasBean implements Serializable{
        /**
         * responseEmployee : {"birthday":"2017-07-04 15:02:37.0","businessAddress":"","businessId":"80a2aa90-2205-4453-8760-b395fb48746a","businessList":[],"businessName":"","courseList":[],"createTime":"","creator":"","depId":"858520b7-4f10-432b-8bdc-d09f517c5c83","depName":"","empHead":"http://192.168.1.186:8088/dod-static/upload/empPicture/dc3719b2-6f6f-4b38-9736-02e2d4e8936a/75d9e41d-f416-43d5-8358-ddbb540ebefa.jpg","empId":"dc3719b2-6f6f-4b38-9736-02e2d4e8936a","empPicture":"/upload/empPicture/dc3719b2-6f6f-4b38-9736-02e2d4e8936a/8260aba4-a5fc-48ad-a6bd-05ee41d2a715.jpg;/upload/empPicture/dc3719b2-6f6f-4b38-9736-02e2d4e8936a/70943a7b-347f-4419-8d11-6fa427f4647d.jpg","empRela":"1","empStatus":"","empType":"2","employeeName":"张三","employeeSerialId":"00019","entryDate":"2017-09-12 14:57:45.0","failurePunch":"","id":"ddcbc213-3145-4b5d-a068-144c0b06706f","idCard":"36242219981114443X","isCoach":"1","jobTitle":"","lasttimes":"","leaveDate":"","leavetimes":"","phoneNum":"15129982998","picPathList":["D:/work/tools/apache-tomcat-7.0.55-8088/webapps/dod-static/upload/empPicture/dc3719b2-6f6f-4b38-9736-02e2d4e8936a/8260aba4-a5fc-48ad-a6bd-05ee41d2a715.jpg","D:/work/tools/apache-tomcat-7.0.55-8088/webapps/dod-static/upload/empPicture/dc3719b2-6f6f-4b38-9736-02e2d4e8936a/70943a7b-347f-4419-8d11-6fa427f4647d.jpg"],"positionId":"e9f99e8e-7b7e-4a1c-9b85-aed441471501","positionName":"","registerType":"2","roleId":"afed24d9-1283-4e85-b49b-8de42e5d49a3","roleName":"","selfIntroduction":"","sex":"1","status":"1","storeAddress":"","storeId":"5565f926-eb45-47c5-a297-ad2101533b19","storeName":"","userRole":null,"workDuration":""}
         */

        private ResponseEmployee responseEmployee;

        public ResponseEmployee getResponseEmployee() {
            return responseEmployee;
        }

        public void setResponseEmployee(ResponseEmployee responseEmployee) {
            this.responseEmployee = responseEmployee;
        }

        public static class ResponseEmployee implements Serializable{
            /**
             * birthday : 2017-07-04 15:02:37.0
             * businessAddress :
             * businessId : 80a2aa90-2205-4453-8760-b395fb48746a
             * businessList : []
             * businessName :
             * courseList : []
             * createTime :
             * creator :
             * depId : 858520b7-4f10-432b-8bdc-d09f517c5c83
             * depName :
             * empHead : http://192.168.1.186:8088/dod-static/upload/empPicture/dc3719b2-6f6f-4b38-9736-02e2d4e8936a/75d9e41d-f416-43d5-8358-ddbb540ebefa.jpg
             * empId : dc3719b2-6f6f-4b38-9736-02e2d4e8936a
             * empPicture : /upload/empPicture/dc3719b2-6f6f-4b38-9736-02e2d4e8936a/8260aba4-a5fc-48ad-a6bd-05ee41d2a715.jpg;/upload/empPicture/dc3719b2-6f6f-4b38-9736-02e2d4e8936a/70943a7b-347f-4419-8d11-6fa427f4647d.jpg
             * empRela : 1
             * empStatus :
             * empType : 2
             * employeeName : 张三
             * employeeSerialId : 00019
             * entryDate : 2017-09-12 14:57:45.0
             * failurePunch :
             * id : ddcbc213-3145-4b5d-a068-144c0b06706f
             * idCard : 36242219981114443X
             * isCoach : 1
             * jobTitle :
             * lasttimes :
             * leaveDate :
             * leavetimes :
             * phoneNum : 15129982998
             * picPathList : ["D:/work/tools/apache-tomcat-7.0.55-8088/webapps/dod-static/upload/empPicture/dc3719b2-6f6f-4b38-9736-02e2d4e8936a/8260aba4-a5fc-48ad-a6bd-05ee41d2a715.jpg","D:/work/tools/apache-tomcat-7.0.55-8088/webapps/dod-static/upload/empPicture/dc3719b2-6f6f-4b38-9736-02e2d4e8936a/70943a7b-347f-4419-8d11-6fa427f4647d.jpg"]
             * positionId : e9f99e8e-7b7e-4a1c-9b85-aed441471501
             * positionName :
             * registerType : 2
             * roleId : afed24d9-1283-4e85-b49b-8de42e5d49a3
             * roleName :
             * selfIntroduction :
             * sex : 1
             * status : 1
             * storeAddress :
             * storeId : 5565f926-eb45-47c5-a297-ad2101533b19
             * storeName :
             * userRole : null
             * workDuration :
             */

            private String birthday;
            private String businessAddress;
            private String businessId;
            private String businessName;
            private String createTime;
            private String creator;
            private String depId;
            private String depName;
            private String empHead;
            private String empId;
            private String empPicture;
            private String empRela;
            private String empStatus;
            private String empType;
            private String employeeName;
            private String employeeSerialId;
            private String entryDate;
            private String failurePunch;
            private String id;
            private String idCard;
            private String isCoach;
            private String jobTitle;
            private String lasttimes;
            private String leaveDate;
            private String leavetimes;
            private String phoneNum;
            private String positionId;
            private String positionName;
            private String registerType;
            private String roleId;
            private String roleName;
            private String selfIntroduction;
            private String sex;
            private String status;
            private String storeAddress;
            private String storeId;
            private String storeName;
            private Object userRole;
            private String workDuration;
            private List<?> businessList;
            private List<?> courseList;
            private List<String> picPathList;

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
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

            public String getEmpHead() {
                return empHead;
            }

            public void setEmpHead(String empHead) {
                this.empHead = empHead;
            }

            public String getEmpId() {
                return empId;
            }

            public void setEmpId(String empId) {
                this.empId = empId;
            }

            public String getEmpPicture() {
                return empPicture;
            }

            public void setEmpPicture(String empPicture) {
                this.empPicture = empPicture;
            }

            public String getEmpRela() {
                return empRela;
            }

            public void setEmpRela(String empRela) {
                this.empRela = empRela;
            }

            public String getEmpStatus() {
                return empStatus;
            }

            public void setEmpStatus(String empStatus) {
                this.empStatus = empStatus;
            }

            public String getEmpType() {
                return empType;
            }

            public void setEmpType(String empType) {
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

            public String getFailurePunch() {
                return failurePunch;
            }

            public void setFailurePunch(String failurePunch) {
                this.failurePunch = failurePunch;
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

            public String getLasttimes() {
                return lasttimes;
            }

            public void setLasttimes(String lasttimes) {
                this.lasttimes = lasttimes;
            }

            public String getLeaveDate() {
                return leaveDate;
            }

            public void setLeaveDate(String leaveDate) {
                this.leaveDate = leaveDate;
            }

            public String getLeavetimes() {
                return leavetimes;
            }

            public void setLeavetimes(String leavetimes) {
                this.leavetimes = leavetimes;
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

            public String getRegisterType() {
                return registerType;
            }

            public void setRegisterType(String registerType) {
                this.registerType = registerType;
            }

            public String getRoleId() {
                return roleId;
            }

            public void setRoleId(String roleId) {
                this.roleId = roleId;
            }

            public String getRoleName() {
                return roleName;
            }

            public void setRoleName(String roleName) {
                this.roleName = roleName;
            }

            public String getSelfIntroduction() {
                return selfIntroduction;
            }

            public void setSelfIntroduction(String selfIntroduction) {
                this.selfIntroduction = selfIntroduction;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
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

            public Object getUserRole() {
                return userRole;
            }

            public void setUserRole(Object userRole) {
                this.userRole = userRole;
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

            public List<?> getCourseList() {
                return courseList;
            }

            public void setCourseList(List<?> courseList) {
                this.courseList = courseList;
            }

            public List<String> getPicPathList() {
                return picPathList;
            }

            public void setPicPathList(List<String> picPathList) {
                this.picPathList = picPathList;
            }

            public ResponseEmployee() {
            }

            @Override
            public String toString() {
                return "ResponseEmployee{" +
                        "birthday='" + birthday + '\'' +
                        ", businessAddress='" + businessAddress + '\'' +
                        ", businessId='" + businessId + '\'' +
                        ", businessName='" + businessName + '\'' +
                        ", createTime='" + createTime + '\'' +
                        ", creator='" + creator + '\'' +
                        ", depId='" + depId + '\'' +
                        ", depName='" + depName + '\'' +
                        ", empHead='" + empHead + '\'' +
                        ", empId='" + empId + '\'' +
                        ", empPicture='" + empPicture + '\'' +
                        ", empRela='" + empRela + '\'' +
                        ", empStatus='" + empStatus + '\'' +
                        ", empType='" + empType + '\'' +
                        ", employeeName='" + employeeName + '\'' +
                        ", employeeSerialId='" + employeeSerialId + '\'' +
                        ", entryDate='" + entryDate + '\'' +
                        ", failurePunch='" + failurePunch + '\'' +
                        ", id='" + id + '\'' +
                        ", idCard='" + idCard + '\'' +
                        ", isCoach='" + isCoach + '\'' +
                        ", jobTitle='" + jobTitle + '\'' +
                        ", lasttimes='" + lasttimes + '\'' +
                        ", leaveDate='" + leaveDate + '\'' +
                        ", leavetimes='" + leavetimes + '\'' +
                        ", phoneNum='" + phoneNum + '\'' +
                        ", positionId='" + positionId + '\'' +
                        ", positionName='" + positionName + '\'' +
                        ", registerType='" + registerType + '\'' +
                        ", roleId='" + roleId + '\'' +
                        ", roleName='" + roleName + '\'' +
                        ", selfIntroduction='" + selfIntroduction + '\'' +
                        ", sex='" + sex + '\'' +
                        ", status='" + status + '\'' +
                        ", storeAddress='" + storeAddress + '\'' +
                        ", storeId='" + storeId + '\'' +
                        ", storeName='" + storeName + '\'' +
                        ", userRole=" + userRole +
                        ", workDuration='" + workDuration + '\'' +
                        ", businessList=" + businessList +
                        ", courseList=" + courseList +
                        ", picPathList=" + picPathList +
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
