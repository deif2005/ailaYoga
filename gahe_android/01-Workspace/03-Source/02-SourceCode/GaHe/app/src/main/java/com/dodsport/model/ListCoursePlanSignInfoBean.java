package com.dodsport.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/13.
 *
 * 课程签到（团课列表）
 */

public class ListCoursePlanSignInfoBean implements Serializable{


    /**
     * datas : {"listCoursePlanSignInfo":[{"classTime":"13:19-14:09","classroomId":"","classroomName":"breakMyHeart","courseId":"","courseName":"美美美苏梅课","courseplanId":"23d4c4b4-fdde-483e-8af5-8706dbb99a75","employeeId":"","employeeName":"coco ","id":"82636e92-0f0f-44f9-92c3-807614b07644","persons":"0","signStatus":"1"},{"classTime":"16:32-17:22","classroomId":"","classroomName":"大教室","courseId":"","courseName":"99","courseplanId":"41fdc5f6-7aaf-4290-addf-b7899bc651bb","employeeId":"","employeeName":"coco ","id":"2a9cd22b-5c4d-49bb-a922-4e7bedd6c16f","persons":"0","signStatus":"1"}]}
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
        private List<ListCoursePlanSignInfo> listCoursePlanSignInfo;

        public List<ListCoursePlanSignInfo> getListCoursePlanSignInfo() {
            return listCoursePlanSignInfo;
        }

        public void setListCoursePlanSignInfo(List<ListCoursePlanSignInfo> listCoursePlanSignInfo) {
            this.listCoursePlanSignInfo = listCoursePlanSignInfo;
        }

        public DatasBean() {
        }

        @Override
        public String toString() {
            return "DatasBean{" +
                    "listCoursePlanSignInfo=" + listCoursePlanSignInfo +
                    '}';
        }


        public static class ListCoursePlanSignInfo implements Serializable{
            /**
             * classTime : 13:19-14:09
             * classroomId :
             * classroomName : breakMyHeart
             * courseId :
             * courseName : 美美美苏梅课
             * courseplanId : 23d4c4b4-fdde-483e-8af5-8706dbb99a75
             * employeeId :
             * employeeName : coco
             * id : 82636e92-0f0f-44f9-92c3-807614b07644
             * persons : 0
             * signStatus : 1
             */

            private String classTime;
            private String classroomId;
            private String classroomName;
            private String courseId;
            private String courseName;
            private String courseplanId;
            private String employeeId;
            private String employeeName;
            private String id;
            private String persons;
            private String signStatus;

            public String getClassTime() {
                return classTime;
            }

            public void setClassTime(String classTime) {
                this.classTime = classTime;
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

            public String getCourseplanId() {
                return courseplanId;
            }

            public void setCourseplanId(String courseplanId) {
                this.courseplanId = courseplanId;
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

            public String getPersons() {
                return persons;
            }

            public void setPersons(String persons) {
                this.persons = persons;
            }

            public String getSignStatus() {
                return signStatus;
            }

            public void setSignStatus(String signStatus) {
                this.signStatus = signStatus;
            }

            public ListCoursePlanSignInfo() {
            }

            @Override
            public String toString() {
                return "ListCoursePlanSignInfo{" +
                        "classTime='" + classTime + '\'' +
                        ", classroomId='" + classroomId + '\'' +
                        ", classroomName='" + classroomName + '\'' +
                        ", courseId='" + courseId + '\'' +
                        ", courseName='" + courseName + '\'' +
                        ", courseplanId='" + courseplanId + '\'' +
                        ", employeeId='" + employeeId + '\'' +
                        ", employeeName='" + employeeName + '\'' +
                        ", id='" + id + '\'' +
                        ", persons='" + persons + '\'' +
                        ", signStatus='" + signStatus + '\'' +
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
