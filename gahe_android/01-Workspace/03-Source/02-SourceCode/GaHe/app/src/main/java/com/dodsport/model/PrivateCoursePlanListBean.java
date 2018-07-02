package com.dodsport.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/28.
 *
 * 私教排课列表
 */
public class PrivateCoursePlanListBean implements Serializable{


    /**
     * datas : {"privateCoursePlanList":[{"classDate":"2017-09-28","classDatetime":"09:00-10:00,12:00-13:00","classroomId":"ee810841-977e-432d-9a8e-ee660563b3aa","classroomName":"大教室","createTime":"2017-09-22 17:52:04.0","creator":"bdc13c70-de9b-47e0-b59d-ea90e8926ee9","employeeId":"bdc13c70-de9b-47e0-b59d-ea90e8926ee9","employeeName":"张高春","id":"177712d2-d156-49e9-8d5a-e337bbfbe93c","jobTitle":"1","remark":"胡鹏老师的30号私教排期","storeId":"ee810841-977e-432d-9a8e-ee660563b302","timeList":[]}]}
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

    public static class DatasBean {
        private List<PrivateCoursePlanList> privateCoursePlanList;

        public List<PrivateCoursePlanList> getPrivateCoursePlanList() {
            return privateCoursePlanList;
        }

        public void setPrivateCoursePlanList(List<PrivateCoursePlanList> privateCoursePlanList) {
            this.privateCoursePlanList = privateCoursePlanList;
        }

        public DatasBean() {
        }

        @Override
        public String toString() {
            return "DatasBean{" +
                    "privateCoursePlanList=" + privateCoursePlanList +
                    '}';
        }

        public static class PrivateCoursePlanList implements Serializable{
            /**
             * classDate : 2017-09-28
             * classDatetime : 09:00-10:00,12:00-13:00
             * classroomId : ee810841-977e-432d-9a8e-ee660563b3aa
             * classroomName : 大教室
             * createTime : 2017-09-22 17:52:04.0
             * creator : bdc13c70-de9b-47e0-b59d-ea90e8926ee9
             * employeeId : bdc13c70-de9b-47e0-b59d-ea90e8926ee9
             * employeeName : 张高春
             * id : 177712d2-d156-49e9-8d5a-e337bbfbe93c
             * jobTitle : 1
             * remark : 胡鹏老师的30号私教排期
             * storeId : ee810841-977e-432d-9a8e-ee660563b302
             * timeList : []
             */

            private String classDate;
            private String classDatetime;
            private String classroomId;
            private String classroomName;
            private String createTime;
            private String creator;
            private String employeeId;
            private String employeeName;
            private String id;
            private String jobTitle;
            private String remark;
            private String storeId;
            private List<?> timeList;

            public String getClassDate() {
                return classDate;
            }

            public void setClassDate(String classDate) {
                this.classDate = classDate;
            }

            public String getClassDatetime() {
                return classDatetime;
            }

            public void setClassDatetime(String classDatetime) {
                this.classDatetime = classDatetime;
            }

            public String getClassroomId() {
                return classroomId;
            }

            public void setClassroomId(String classroomId) {
                this.classroomId = classroomId;
            }

            public String getClassroomName() {
                return classroomName;
            }

            public void setClassroomName(String classroomName) {
                this.classroomName = classroomName;
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

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getJobTitle() {
                return jobTitle;
            }

            public void setJobTitle(String jobTitle) {
                this.jobTitle = jobTitle;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getStoreId() {
                return storeId;
            }

            public void setStoreId(String storeId) {
                this.storeId = storeId;
            }

            public List<?> getTimeList() {
                return timeList;
            }

            public void setTimeList(List<?> timeList) {
                this.timeList = timeList;
            }

            public PrivateCoursePlanList() {
            }

            @Override
            public String toString() {
                return "PrivateCoursePlanList{" +
                        "classDate='" + classDate + '\'' +
                        ", classDatetime='" + classDatetime + '\'' +
                        ", classroomId='" + classroomId + '\'' +
                        ", classroomName='" + classroomName + '\'' +
                        ", createTime='" + createTime + '\'' +
                        ", creator='" + creator + '\'' +
                        ", employeeId='" + employeeId + '\'' +
                        ", employeeName='" + employeeName + '\'' +
                        ", id='" + id + '\'' +
                        ", jobTitle='" + jobTitle + '\'' +
                        ", remark='" + remark + '\'' +
                        ", storeId='" + storeId + '\'' +
                        ", timeList=" + timeList +
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
