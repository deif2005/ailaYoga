package com.dodsport.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/12.
 *
 * 老师关联课程
 */

public class TeacherAndCourseListBean implements Serializable {


    /**
     * datas : {"teacherAndCourseList":[{"courseId":"0240eeb9-4bc3-4f3b-a79e-02d8568ca243","courseMeans":"2","courseName":"美美美苏梅课","employeeId":"","employeeName":"","enable":"0","id":""},{"courseId":"1b6dbcd5-0795-4af7-beb0-fef9607587b0","courseMeans":"3","courseName":"OO","employeeId":"","employeeName":"","enable":"0","id":""},{"courseId":"34205c01-2a43-4f46-9d20-245e9da07a1e","courseMeans":"2","courseName":"美美美苏梅课","employeeId":"","employeeName":"","enable":"0","id":""},{"courseId":"4e4d2f37-b9da-4622-86af-36c23a5ff396","courseMeans":"2","courseName":"流瑜伽精进提升班\t\t\t\t\t\t\t\t","employeeId":"","employeeName":"","enable":"0","id":""},{"courseId":"71212f1d-f794-4609-a2fb-6ed4cd752c84","courseMeans":"2","courseName":"YY","employeeId":"","employeeName":"","enable":"0","id":""},{"courseId":"c960b53d-e552-4af3-bec5-0dca446747d8","courseMeans":"2","courseName":"美美美苏梅课","employeeId":"","employeeName":"","enable":"0","id":""},{"courseId":"e80aa8fd-2c3b-437d-b2eb-686134e32743","courseMeans":"2","courseName":"99","employeeId":"","employeeName":"","enable":"0","id":""},{"courseId":"ea867cc2-5e4d-435f-9bb5-0ecc291999b0","courseMeans":"3","courseName":"11","employeeId":"","employeeName":"","enable":"0","id":""}]}
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
        private List<TeacherAndCourseList> teacherAndCourseList;

        public List<TeacherAndCourseList> getTeacherAndCourseList() {
            return teacherAndCourseList;
        }

        public void setTeacherAndCourseList(List<TeacherAndCourseList> teacherAndCourseList) {
            this.teacherAndCourseList = teacherAndCourseList;
        }

        public DatasBean() {
        }

        public static class TeacherAndCourseList implements Serializable{
            /**
             * courseId : 0240eeb9-4bc3-4f3b-a79e-02d8568ca243
             * courseMeans : 2
             * courseName : 美美美苏梅课
             * employeeId :
             * employeeName :
             * enable : 0
             * id :
             */

            private String courseId;
            private String courseMeans;
            private String courseName;
            private String employeeId;
            private String employeeName;
            private String enable;
            private String id;
            private String status;

            public String getCourseId() {
                return courseId;
            }

            public void setCourseId(String courseId) {
                this.courseId = courseId;
            }

            public String getCourseMeans() {
                return courseMeans;
            }

            public void setCourseMeans(String courseMeans) {
                this.courseMeans = courseMeans;
            }

            public String getCourseName() {
                return courseName;
            }

            public void setCourseName(String courseName) {
                this.courseName = courseName;
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

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public TeacherAndCourseList() {
            }

            @Override
            public String toString() {
                return "TeacherAndCourseList{" +
                        "courseId='" + courseId + '\'' +
                        ", courseMeans='" + courseMeans + '\'' +
                        ", courseName='" + courseName + '\'' +
                        ", employeeId='" + employeeId + '\'' +
                        ", employeeName='" + employeeName + '\'' +
                        ", enable='" + enable + '\'' +
                        ", id='" + id + '\'' +
                        ", status='" + status + '\'' +
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
