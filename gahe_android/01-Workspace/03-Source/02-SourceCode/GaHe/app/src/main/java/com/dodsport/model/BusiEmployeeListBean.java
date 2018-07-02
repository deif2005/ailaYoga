package com.dodsport.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/30.
 *
 * 门店中的教练列表
 */

public class BusiEmployeeListBean implements Serializable{
    /**
     * datas : {"evalList":[{"avgEnvironmentlevel":"","avgServiceLevel":"","avgTeachLevel":"","birthday":"","businessAddress":"","businessId":"80a2aa90-2205-4453-8760-b395fb48746a","businessList":[],"businessName":"","courseList":[],"createTime":"","creator":"","depId":"8c479d4b-bf85-4f5e-8b1d-11c1ab845ed9","depName":"","empHead":"","empId":"ce13e16f-99c0-4ca0-906b-5d568ed42570","empPicture":"","empRela":"1","empStatus":"","empType":"2","employeeName":"李二牛","employeeSerialId":"000046","entryDate":"2017-10-19 00:00:00.0","failurePunch":"","id":"085c61e9-8e53-4839-8358-d9f06df41581","idCard":"152122198210091268","isCoach":"2","jobTitle":"2","lasttimes":"","leaveDate":"","leavetimes":"","memberEvaluateList":[],"phoneNum":"15129972997","picPathList":[],"positionId":"d78dc1ef-7db5-48b1-acbb-b79fbe5a5d0b","positionName":"","registerType":"1","roleId":"afed24d9-1283-4e85-b49b-8de42e5d49a3","roleName":"","selfIntroduction":"测试测试","sex":"2","status":"1","storeAddress":"","storeId":"5565f926-eb45-47c5-a297-ad2101533b19","storeName":"","sumAvgEnvironmentlevel":"","sumAvgServiceLevel":"","sumAvgTeachLevel":"","userRole":null,"workDuration":""},{"avgEnvironmentlevel":"","avgServiceLevel":"","avgTeachLevel":"","birthday":"","businessAddress":"","businessId":"80a2aa90-2205-4453-8760-b395fb48746a","businessList":[],"businessName":"","courseList":[],"createTime":"","creator":"","depId":"d0f8abbf-4860-4859-b013-2ce6965423ec","depName":"","empHead":"","empId":"c360c130-ced8-4b06-b8c9-dce0549ffe98","empPicture":"","empRela":"1","empStatus":"","empType":"2","employeeName":"coco ","employeeSerialId":"000041","entryDate":"2015-09-24 00:00:00.0","failurePunch":"","id":"2167d84e-d886-40ae-b290-555d22d63afa","idCard":"152122198210091268","isCoach":"2","jobTitle":"3","lasttimes":"","leaveDate":"","leavetimes":"","memberEvaluateList":[],"phoneNum":"15100000009","picPathList":[],"positionId":"e9f99e8e-7b7e-4a1c-9b85-aed441471501","positionName":"","registerType":"1","roleId":"afed24d9-1283-4e85-b49b-8de42e5d49a3","roleName":"","selfIntroduction":"ggggh ","sex":"1","status":"1","storeAddress":"","storeId":"5565f926-eb45-47c5-a297-ad2101533b19","storeName":"","sumAvgEnvironmentlevel":"","sumAvgServiceLevel":"","sumAvgTeachLevel":"","userRole":null,"workDuration":""},{"avgEnvironmentlevel":"","avgServiceLevel":"","avgTeachLevel":"","birthday":"","businessAddress":"","businessId":"80a2aa90-2205-4453-8760-b395fb48746a","businessList":[],"businessName":"","courseList":[],"createTime":"","creator":"","depId":"2080012a-95a6-4d31-9ce9-7edf868e4353","depName":"","empHead":"","empId":"21c07f85-f6c6-4290-b79c-d6531be38fd9","empPicture":"","empRela":"1","empStatus":"","empType":"2","employeeName":"胡鹏","employeeSerialId":"000044","entryDate":"2015-09-24 00:00:00.0","failurePunch":"","id":"273ee8c3-4e02-498c-9317-ea8dd7fab45e","idCard":"421023199400000000","isCoach":"2","jobTitle":"2","lasttimes":"","leaveDate":"","leavetimes":"","memberEvaluateList":[],"phoneNum":"15121353666","picPathList":[],"positionId":"01f1ff7c-610a-440a-b3e2-357ca70456ff","positionName":"","registerType":"1","roleId":"58d2985a-c230-4f44-9ce5-195d4ea5f2c6","roleName":"","selfIntroduction":"","sex":"2","status":"1","storeAddress":"","storeId":"5565f926-eb45-47c5-a297-ad2101533b19","storeName":"","sumAvgEnvironmentlevel":"","sumAvgServiceLevel":"","sumAvgTeachLevel":"","userRole":null,"workDuration":""},{"avgEnvironmentlevel":"","avgServiceLevel":"","avgTeachLevel":"","birthday":"","businessAddress":"","businessId":"80a2aa90-2205-4453-8760-b395fb48746a","businessList":[],"businessName":"","courseList":[],"createTime":"","creator":"","depId":"458724dd-5d14-464d-a0ae-c87c44d26fed","depName":"","empHead":"","empId":"5085eead-2627-4b58-9510-06e08ebda440","empPicture":"","empRela":"1","empStatus":"","empType":"2","employeeName":"马军","employeeSerialId":"000045","entryDate":"1994-01-06 00:00:00.0","failurePunch":"","id":"458724dd-5d14-464d-a0ae-c87c44d26fed","idCard":"421023199401060000","isCoach":"2","jobTitle":"3","lasttimes":"","leaveDate":"","leavetimes":"","memberEvaluateList":[],"phoneNum":"15102741251","picPathList":[],"positionId":"01f1ff7c-610a-440a-b3e2-357ca70456ff","positionName":"","registerType":"1","roleId":"58d2985a-c230-4f44-9ce5-195d4ea5f2c6","roleName":"","selfIntroduction":"","sex":"2","status":"1","storeAddress":"","storeId":"5565f926-eb45-47c5-a297-ad2101533b19","storeName":"","sumAvgEnvironmentlevel":"","sumAvgServiceLevel":"","sumAvgTeachLevel":"","userRole":null,"workDuration":""},{"avgEnvironmentlevel":"","avgServiceLevel":"","avgTeachLevel":"","birthday":"","businessAddress":"","businessId":"80a2aa90-2205-4453-8760-b395fb48746a","businessList":[],"businessName":"","courseList":[],"createTime":"","creator":"","depId":"858520b7-4f10-432b-8bdc-d09f517c5c83","depName":"","empHead":"","empId":"72f9ff14-df88-45fb-9bd4-ce3bf985e9c2","empPicture":"","empRela":"1","empStatus":"","empType":"2","employeeName":"方大痣","employeeSerialId":"000035","entryDate":"2017-09-30 14:28:49.0","failurePunch":"","id":"7b32a721-50d3-40a0-88d8-03f06926289b","idCard":"123456789123456789","isCoach":"2","jobTitle":"3","lasttimes":"","leaveDate":"","leavetimes":"","memberEvaluateList":[],"phoneNum":"16888886666","picPathList":[],"positionId":"32e133ef-4383-4421-afb9-f044f7e5e22e","positionName":"","registerType":"1","roleId":"3846d44b-1cc2-4af2-b712-398b21866994","roleName":"","selfIntroduction":"","sex":"1","status":"1","storeAddress":"","storeId":"5565f926-eb45-47c5-a297-ad2101533b19","storeName":"","sumAvgEnvironmentlevel":"","sumAvgServiceLevel":"","sumAvgTeachLevel":"","userRole":null,"workDuration":""},{"avgEnvironmentlevel":"","avgServiceLevel":"","avgTeachLevel":"","birthday":"","businessAddress":"","businessId":"80a2aa90-2205-4453-8760-b395fb48746a","businessList":[],"businessName":"","courseList":[],"createTime":"","creator":"","depId":"020f141e-b611-43da-a3f3-14522f80abd0","depName":"","empHead":"","empId":"731b7df7-bb0e-4c35-af12-80e267c2bb6d","empPicture":"","empRela":"1","empStatus":"","empType":"2","employeeName":"方大痣","employeeSerialId":"000038","entryDate":"2017-09-30 14:36:00.0","failurePunch":"","id":"822de679-4383-4325-83f6-2c5ab31b2a57","idCard":"123456789987654321","isCoach":"2","jobTitle":"3","lasttimes":"","leaveDate":"","leavetimes":"","memberEvaluateList":[],"phoneNum":"13536982536","picPathList":[],"positionId":"32e133ef-4383-4421-afb9-f044f7e5e22e","positionName":"","registerType":"1","roleId":"3846d44b-1cc2-4af2-b712-398b21866994","roleName":"","selfIntroduction":"很好很好很好\u2026\u2026","sex":"2","status":"1","storeAddress":"","storeId":"5565f926-eb45-47c5-a297-ad2101533b19","storeName":"","sumAvgEnvironmentlevel":"","sumAvgServiceLevel":"","sumAvgTeachLevel":"","userRole":null,"workDuration":""},{"avgEnvironmentlevel":"","avgServiceLevel":"","avgTeachLevel":"","birthday":"2017-07-04","businessAddress":"","businessId":"80a2aa90-2205-4453-8760-b395fb48746a","businessList":[],"businessName":"","courseList":[],"createTime":"","creator":"","depId":"8c479d4b-bf85-4f5e-8b1d-11c1ab845ed9","depName":"","empHead":"","empId":"cb149a85-d014-4620-88a6-2660f017b9cc","empPicture":"","empRela":"1","empStatus":"","empType":"2","employeeName":"李老师","employeeSerialId":"000047","entryDate":"2017-10-19 00:00:00.0","failurePunch":"","id":"c3a0d32f-a356-4eb1-9698-a8d7d1fafa55","idCard":"152122198210091268","isCoach":"2","jobTitle":"3","lasttimes":"","leaveDate":"","leavetimes":"","memberEvaluateList":[],"phoneNum":"15129962996","picPathList":[],"positionId":"bdc6b17d-c62f-4bf8-8dae-fb7ff8efed12","positionName":"","registerType":"1","roleId":"3846d44b-1cc2-4af2-b712-398b21866994","roleName":"","selfIntroduction":"啊姐姐今天","sex":"2","status":"1","storeAddress":"","storeId":"5565f926-eb45-47c5-a297-ad2101533b19","storeName":"","sumAvgEnvironmentlevel":"","sumAvgServiceLevel":"","sumAvgTeachLevel":"","userRole":null,"workDuration":""},{"avgEnvironmentlevel":"","avgServiceLevel":"","avgTeachLevel":"","birthday":"2017-07-04","businessAddress":"","businessId":"80a2aa90-2205-4453-8760-b395fb48746a","businessList":[],"businessName":"","courseList":[],"createTime":"","creator":"","depId":"d0f8abbf-4860-4859-b013-2ce6965423ec","depName":"","empHead":"","empId":"f2de8348-1cfd-446c-9566-1bf864234646","empPicture":"","empRela":"1","empStatus":"","empType":"2","employeeName":"孙悟空","employeeSerialId":"000043","entryDate":"2017-10-10 14:30:24.0","failurePunch":"","id":"c3c05047-3f14-4c88-ba9d-3aebf5e1918c","idCard":"510623199012228466","isCoach":"2","jobTitle":"1","lasttimes":"","leaveDate":"","leavetimes":"","memberEvaluateList":[],"phoneNum":"15100000011","picPathList":[],"positionId":"e9f99e8e-7b7e-4a1c-9b85-aed441471501","positionName":"","registerType":"1","roleId":"afed24d9-1283-4e85-b49b-8de42e5d49a3","roleName":"","selfIntroduction":"啦咯啦咯啦咯哦哦","sex":"1","status":"1","storeAddress":"","storeId":"5565f926-eb45-47c5-a297-ad2101533b19","storeName":"","sumAvgEnvironmentlevel":"","sumAvgServiceLevel":"","sumAvgTeachLevel":"","userRole":null,"workDuration":""}]}
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

    public static class DatasBean implements Serializable{
        private List<EvalListBean> evalList;

        public List<EvalListBean> getEvalList() {
            return evalList;
        }

        public void setEvalList(List<EvalListBean> evalList) {
            this.evalList = evalList;
        }

        public DatasBean() {
        }

        @Override
        public String toString() {
            return "DatasBean{" +
                    "evalList=" + evalList +
                    '}';
        }

        public static class EvalListBean implements Serializable{
            /**
             * avgEnvironmentlevel :
             * avgServiceLevel :
             * avgTeachLevel :
             * birthday :
             * businessAddress :
             * businessId : 80a2aa90-2205-4453-8760-b395fb48746a
             * businessList : []
             * businessName :
             * courseList : []
             * createTime :
             * creator :
             * depId : 8c479d4b-bf85-4f5e-8b1d-11c1ab845ed9
             * depName :
             * empHead :
             * empId : ce13e16f-99c0-4ca0-906b-5d568ed42570
             * empPicture :
             * empRela : 1
             * empStatus :
             * empType : 2
             * employeeName : 李二牛
             * employeeSerialId : 000046
             * entryDate : 2017-10-19 00:00:00.0
             * failurePunch :
             * id : 085c61e9-8e53-4839-8358-d9f06df41581
             * idCard : 152122198210091268
             * isCoach : 2
             * jobTitle : 2
             * lasttimes :
             * leaveDate :
             * leavetimes :
             * memberEvaluateList : []
             * phoneNum : 15129972997
             * picPathList : []
             * positionId : d78dc1ef-7db5-48b1-acbb-b79fbe5a5d0b
             * positionName :
             * registerType : 1
             * roleId : afed24d9-1283-4e85-b49b-8de42e5d49a3
             * roleName :
             * selfIntroduction : 测试测试
             * sex : 2
             * status : 1
             * storeAddress :
             * storeId : 5565f926-eb45-47c5-a297-ad2101533b19
             * storeName :
             * sumAvgEnvironmentlevel :
             * sumAvgServiceLevel :
             * sumAvgTeachLevel :
             * userRole : null
             * workDuration :
             */

            private String avgEnvironmentlevel;
            private String avgServiceLevel;
            private String avgTeachLevel;
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
            private String sumAvgEnvironmentlevel;
            private String sumAvgServiceLevel;
            private String sumAvgTeachLevel;
            private Object userRole;
            private String workDuration;
            private List<?> businessList;
            private List<?> courseList;
            private List<?> memberEvaluateList;
            private List<?> picPathList;

            public String getAvgEnvironmentlevel() {
                return avgEnvironmentlevel;
            }

            public void setAvgEnvironmentlevel(String avgEnvironmentlevel) {
                this.avgEnvironmentlevel = avgEnvironmentlevel;
            }

            public String getAvgServiceLevel() {
                return avgServiceLevel;
            }

            public void setAvgServiceLevel(String avgServiceLevel) {
                this.avgServiceLevel = avgServiceLevel;
            }

            public String getAvgTeachLevel() {
                return avgTeachLevel;
            }

            public void setAvgTeachLevel(String avgTeachLevel) {
                this.avgTeachLevel = avgTeachLevel;
            }

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

            public String getSumAvgEnvironmentlevel() {
                return sumAvgEnvironmentlevel;
            }

            public void setSumAvgEnvironmentlevel(String sumAvgEnvironmentlevel) {
                this.sumAvgEnvironmentlevel = sumAvgEnvironmentlevel;
            }

            public String getSumAvgServiceLevel() {
                return sumAvgServiceLevel;
            }

            public void setSumAvgServiceLevel(String sumAvgServiceLevel) {
                this.sumAvgServiceLevel = sumAvgServiceLevel;
            }

            public String getSumAvgTeachLevel() {
                return sumAvgTeachLevel;
            }

            public void setSumAvgTeachLevel(String sumAvgTeachLevel) {
                this.sumAvgTeachLevel = sumAvgTeachLevel;
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

            public List<?> getMemberEvaluateList() {
                return memberEvaluateList;
            }

            public void setMemberEvaluateList(List<?> memberEvaluateList) {
                this.memberEvaluateList = memberEvaluateList;
            }

            public List<?> getPicPathList() {
                return picPathList;
            }

            public void setPicPathList(List<?> picPathList) {
                this.picPathList = picPathList;
            }

            public EvalListBean() {
            }

            @Override
            public String toString() {
                return "EvalListBean{" +
                        "avgEnvironmentlevel='" + avgEnvironmentlevel + '\'' +
                        ", avgServiceLevel='" + avgServiceLevel + '\'' +
                        ", avgTeachLevel='" + avgTeachLevel + '\'' +
                        ", birthday='" + birthday + '\'' +
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
                        ", sumAvgEnvironmentlevel='" + sumAvgEnvironmentlevel + '\'' +
                        ", sumAvgServiceLevel='" + sumAvgServiceLevel + '\'' +
                        ", sumAvgTeachLevel='" + sumAvgTeachLevel + '\'' +
                        ", userRole=" + userRole +
                        ", workDuration='" + workDuration + '\'' +
                        ", businessList=" + businessList +
                        ", courseList=" + courseList +
                        ", memberEvaluateList=" + memberEvaluateList +
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
