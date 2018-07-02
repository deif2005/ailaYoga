package com.dodsport.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/28.
 *
 * 团课 一天的排课列表
 */

public class CoursePlanListBean implements Serializable {


    /**
     * datas : {"coursePlanList":[{"classDatetime":"2017-09-30 09:00:00.0","classRoomName":"大教室","classroomId":"ee810841-977e-432d-9a8e-ee660563b3aa","courseId":"3fa76e06-4e33-4c8b-9554-cd6484510884","courseName":"阳性瑜伽","creator":"","duration":"1","employeeId":"bdc13c70-de9b-47e0-b59d-ea90e8926ee9","employeeName":"张高春","enable":"1","id":"d246dcf8-2359-4adc-b4b4-7becfb4b2b8b","isExperience":"1","lowPersons":"1","remark":"30号高春上课","storeId":"ee810841-977e-432d-9a8e-ee660563b302","upperPersons":"5"}]}
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

    public CoursePlanListBean() {
    }

    public static class DatasBean implements  Serializable{
        private List<CoursePlanList> coursePlanList;

        public List<CoursePlanList> getCoursePlanList() {
            return coursePlanList;
        }

        public void setCoursePlanList(List<CoursePlanList> coursePlanList) {
            this.coursePlanList = coursePlanList;
        }

        public DatasBean() {
        }

        public static class CoursePlanList implements Serializable {
            /**
             * classDatetime : 2017-09-30 09:00:00.0
             * classRoomName : 大教室
             * classroomId : ee810841-977e-432d-9a8e-ee660563b3aa
             * courseId : 3fa76e06-4e33-4c8b-9554-cd6484510884
             * courseName : 阳性瑜伽
             * creator :
             * duration : 1
             * employeeId : bdc13c70-de9b-47e0-b59d-ea90e8926ee9
             * employeeName : 张高春
             * enable : 1
             * id : d246dcf8-2359-4adc-b4b4-7becfb4b2b8b
             * isExperience : 1
             * lowPersons : 1
             * remark : 30号高春上课
             * storeId : ee810841-977e-432d-9a8e-ee660563b302
             * upperPersons : 5
             */


            private String classDatetime;
            private String classRoomName;
            private String classroomId;
            private String courseId;
            private String courseName;
            private String creator;
            private String duration;
            private String employeeId;
            private String employeeName;
            private String enable;
            private String id;
            private String isExperience;
            private String lowPersons;
            private String remark;
            private String storeId;
            private String upperPersons;

            public String getClassDatetime() {
                return classDatetime;
            }

            public void setClassDatetime(String classDatetime) {
                this.classDatetime = classDatetime;
            }

            public String getClassRoomName() {
                return classRoomName;
            }

            public void setClassRoomName(String classRoomName) {
                this.classRoomName = classRoomName;
            }

            public String getClassroomId() {
                return classroomId;
            }

            public void setClassroomId(String classroomId) {
                this.classroomId = classroomId;
            }

            public String getCourseId() {
                return courseId;
            }

            public void setCourseId(String courseId) {
                this.courseId = courseId;
            }

            public String getCourseName() {
                return courseName;
            }

            public void setCourseName(String courseName) {
                this.courseName = courseName;
            }

            public String getCreator() {
                return creator;
            }

            public void setCreator(String creator) {
                this.creator = creator;
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

            public String getEnable() {
                return enable;
            }

            public void setEnable(String enable) {
                this.enable = enable;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getIsExperience() {
                return isExperience;
            }

            public void setIsExperience(String isExperience) {
                this.isExperience = isExperience;
            }

            public String getLowPersons() {
                return lowPersons;
            }

            public void setLowPersons(String lowPersons) {
                this.lowPersons = lowPersons;
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

            public String getUpperPersons() {
                return upperPersons;
            }

            public void setUpperPersons(String upperPersons) {
                this.upperPersons = upperPersons;
            }

            public CoursePlanList() {
            }

            @Override
            public String toString() {
                return "CoursePlanList{" +
                        "classDatetime='" + classDatetime + '\'' +
                        ", classRoomName='" + classRoomName + '\'' +
                        ", classroomId='" + classroomId + '\'' +
                        ", courseId='" + courseId + '\'' +
                        ", courseName='" + courseName + '\'' +
                        ", creator='" + creator + '\'' +
                        ", duration='" + duration + '\'' +
                        ", employeeId='" + employeeId + '\'' +
                        ", employeeName='" + employeeName + '\'' +
                        ", enable='" + enable + '\'' +
                        ", id='" + id + '\'' +
                        ", isExperience='" + isExperience + '\'' +
                        ", lowPersons='" + lowPersons + '\'' +
                        ", remark='" + remark + '\'' +
                        ", storeId='" + storeId + '\'' +
                        ", upperPersons='" + upperPersons + '\'' +
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
