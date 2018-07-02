package com.dodsport.consumer.model;

import java.io.Serializable;
import java.util.List;

/**
 *  团课 课程列表
 * @author Administrator
 * @date 2017/11/1
 */

public class ClientCourseOrderListBean implements Serializable {
    /**
     * datas : {"clientCourseOrderList":[{"classDatetime":"20:00","classroomName":"最差的教室2","courseId":"8bfc836e-67bd-4996-ab9c-916cf4ac3f03","courseName":"2015怎么办","courseOrderId":"0466e6ad-74eb-4848-aa7d-081c20dcc1e3","courseplanId":"380d9ee2-2d6f-4ec8-8da4-04537b1b5a18","duration":"50","employeeName":"李二牛","iconPath":"","id":"1bb7c574-1bc7-4e52-ae35-14fb386db235","orderStatus":"1","permitPersons":"4"},{"classDatetime":"20:00","classroomName":"最差的教室2","courseId":"8bfc836e-67bd-4996-ab9c-916cf4ac3f03","courseName":"2015怎么办","courseOrderId":"150533d8-73f9-4781-ac0f-3375708a0efb","courseplanId":"380d9ee2-2d6f-4ec8-8da4-04537b1b5a18","duration":"50","employeeName":"李二牛","iconPath":"","id":"1bb7c574-1bc7-4e52-ae35-14fb386db235","orderStatus":"1","permitPersons":"4"},{"classDatetime":"20:00","classroomName":"最差的教室2","courseId":"8bfc836e-67bd-4996-ab9c-916cf4ac3f03","courseName":"2015怎么办","courseOrderId":"2d265cc3-618d-4528-abfa-9187b8f8678a","courseplanId":"380d9ee2-2d6f-4ec8-8da4-04537b1b5a18","duration":"50","employeeName":"李二牛","iconPath":"","id":"1bb7c574-1bc7-4e52-ae35-14fb386db235","orderStatus":"4","permitPersons":"4"},{"classDatetime":"20:00","classroomName":"最差的教室2","courseId":"8bfc836e-67bd-4996-ab9c-916cf4ac3f03","courseName":"2015怎么办","courseOrderId":"7bb1ad7f-7881-4b22-ad5c-7ea2e3243ddf","courseplanId":"380d9ee2-2d6f-4ec8-8da4-04537b1b5a18","duration":"50","employeeName":"李二牛","iconPath":"","id":"1bb7c574-1bc7-4e52-ae35-14fb386db235","orderStatus":"1","permitPersons":"4"},{"classDatetime":"20:00","classroomName":"最差的教室2","courseId":"8bfc836e-67bd-4996-ab9c-916cf4ac3f03","courseName":"2015怎么办","courseOrderId":"81582558-179d-428d-a2b6-8667f1bca264","courseplanId":"380d9ee2-2d6f-4ec8-8da4-04537b1b5a18","duration":"50","employeeName":"李二牛","iconPath":"","id":"1bb7c574-1bc7-4e52-ae35-14fb386db235","orderStatus":"1","permitPersons":"4"}]}
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
        private List<ClientCourseOrderList> clientCourseOrderList;

        public List<ClientCourseOrderList> getClientCourseOrderList() {
            return clientCourseOrderList;
        }

        public void setClientCourseOrderList(List<ClientCourseOrderList> clientCourseOrderList) {
            this.clientCourseOrderList = clientCourseOrderList;
        }

        public DatasBean() {
        }

        @Override
        public String toString() {
            return "DatasBean{" +
                    "clientCourseOrderList=" + clientCourseOrderList +
                    '}';
        }

        public static class ClientCourseOrderList implements Serializable{
            /**
             * classDatetime : 20:00
             * classroomName : 最差的教室2
             * courseId : 8bfc836e-67bd-4996-ab9c-916cf4ac3f03
             * courseName : 2015怎么办
             * courseOrderId : 0466e6ad-74eb-4848-aa7d-081c20dcc1e3
             * courseplanId : 380d9ee2-2d6f-4ec8-8da4-04537b1b5a18
             * duration : 50
             * employeeName : 李二牛
             * iconPath :
             * id : 1bb7c574-1bc7-4e52-ae35-14fb386db235
             * orderStatus : 1
             * permitPersons : 4
             */

            private String classDatetime;
            private String classroomName;
            private String courseId;
            private String courseName;
            private String courseOrderId;
            private String courseplanId;
            private String duration;
            private String employeeName;
            private String iconPath;
            private String id;
            private String orderStatus;
            private String permitPersons;

            public String getClassDatetime() {
                return classDatetime;
            }

            public void setClassDatetime(String classDatetime) {
                this.classDatetime = classDatetime;
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

            public String getCourseOrderId() {
                return courseOrderId;
            }

            public void setCourseOrderId(String courseOrderId) {
                this.courseOrderId = courseOrderId;
            }

            public String getCourseplanId() {
                return courseplanId;
            }

            public void setCourseplanId(String courseplanId) {
                this.courseplanId = courseplanId;
            }

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }

            public String getEmployeeName() {
                return employeeName;
            }

            public void setEmployeeName(String employeeName) {
                this.employeeName = employeeName;
            }

            public String getIconPath() {
                return iconPath;
            }

            public void setIconPath(String iconPath) {
                this.iconPath = iconPath;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(String orderStatus) {
                this.orderStatus = orderStatus;
            }

            public String getPermitPersons() {
                return permitPersons;
            }

            public void setPermitPersons(String permitPersons) {
                this.permitPersons = permitPersons;
            }

            public ClientCourseOrderList() {
            }

            @Override
            public String toString() {
                return "ClientCourseOrderList{" +
                        "classDatetime='" + classDatetime + '\'' +
                        ", classroomName='" + classroomName + '\'' +
                        ", courseId='" + courseId + '\'' +
                        ", courseName='" + courseName + '\'' +
                        ", courseOrderId='" + courseOrderId + '\'' +
                        ", courseplanId='" + courseplanId + '\'' +
                        ", duration='" + duration + '\'' +
                        ", employeeName='" + employeeName + '\'' +
                        ", iconPath='" + iconPath + '\'' +
                        ", id='" + id + '\'' +
                        ", orderStatus='" + orderStatus + '\'' +
                        ", permitPersons='" + permitPersons + '\'' +
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

    public ClientCourseOrderListBean() {
    }
}
