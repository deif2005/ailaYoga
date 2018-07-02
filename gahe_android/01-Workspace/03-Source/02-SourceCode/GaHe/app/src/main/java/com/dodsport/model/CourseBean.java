package com.dodsport.model;

import java.io.Serializable;


/**
 * Created by Administrator on 2017/9/27.
 *
 * 课程数据 实体类
 */
public class CourseBean implements Serializable {

    /**
     * datas : {"course":{"businessId":"f28bcf52-09f9-4fdf-8c86-c2dcf915b41c","courseMeans":"2","courseName":"流瑜伽精进提升班\t\t\t\t\t\t\t\t","courseSerialId":"048","courseStatus":"1","courseTypeId":"82711ecd-f54d-48f1-bfea-6c3118cbd1a5","courseTypeName":"小班课","createTime":"2017-08-24 14:12:08.0","creator":"bdc13c70-de9b-47e0-b59d-ea90e8926ee9","duration":"1","iconPath":"","id":"4e4d2f37-b9da-4622-86af-36c23a5ff396","isExperience":"2","remark":"流瑜伽精进提升班\t\t\t\t\t\t\t\t\t\t","status":"1"}}
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

    public CourseBean() {
    }

    @Override
    public String toString() {
        return "CourseBean{" +
                "datas=" + datas +
                ", result=" + result +
                '}';
    }

    public static class DatasBean implements Serializable{
        /**
         * course : {"businessId":"f28bcf52-09f9-4fdf-8c86-c2dcf915b41c","courseMeans":"2","courseName":"流瑜伽精进提升班\t\t\t\t\t\t\t\t","courseSerialId":"048","courseStatus":"1","courseTypeId":"82711ecd-f54d-48f1-bfea-6c3118cbd1a5","courseTypeName":"小班课","createTime":"2017-08-24 14:12:08.0","creator":"bdc13c70-de9b-47e0-b59d-ea90e8926ee9","duration":"1","iconPath":"","id":"4e4d2f37-b9da-4622-86af-36c23a5ff396","isExperience":"2","remark":"流瑜伽精进提升班\t\t\t\t\t\t\t\t\t\t","status":"1"}
         */

        private Course course;

        public Course getCourse() {
            return course;
        }

        public void setCourse(Course course) {
            this.course = course;
        }

        public static class Course implements Serializable{
            /**
             * businessId : f28bcf52-09f9-4fdf-8c86-c2dcf915b41c
             * courseMeans : 2
             * courseName : 流瑜伽精进提升班
             * courseSerialId : 048
             * courseStatus : 1
             * courseTypeId : 82711ecd-f54d-48f1-bfea-6c3118cbd1a5
             * courseTypeName : 小班课
             * createTime : 2017-08-24 14:12:08.0
             * creator : bdc13c70-de9b-47e0-b59d-ea90e8926ee9
             * duration : 1
             * iconPath :
             * id : 4e4d2f37-b9da-4622-86af-36c23a5ff396
             * isExperience : 2
             * remark : 流瑜伽精进提升班
             * status : 1
             */

            private String businessId;
            private String courseMeans;
            private String courseName;
            private String courseSerialId;
            private String courseStatus;
            private String courseTypeId;
            private String courseTypeName;
            private String createTime;
            private String creator;
            private String duration;
            private String iconPath;
            private String id;
            private String isExperience;
            private String remark;
            private String status;

            public String getBusinessId() {
                return businessId;
            }

            public void setBusinessId(String businessId) {
                this.businessId = businessId;
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

            public String getCourseSerialId() {
                return courseSerialId;
            }

            public void setCourseSerialId(String courseSerialId) {
                this.courseSerialId = courseSerialId;
            }

            public String getCourseStatus() {
                return courseStatus;
            }

            public void setCourseStatus(String courseStatus) {
                this.courseStatus = courseStatus;
            }

            public String getCourseTypeId() {
                return courseTypeId;
            }

            public void setCourseTypeId(String courseTypeId) {
                this.courseTypeId = courseTypeId;
            }

            public String getCourseTypeName() {
                return courseTypeName;
            }

            public void setCourseTypeName(String courseTypeName) {
                this.courseTypeName = courseTypeName;
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

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
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

            public String getIsExperience() {
                return isExperience;
            }

            public void setIsExperience(String isExperience) {
                this.isExperience = isExperience;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public Course() {
            }

            @Override
            public String toString() {
                return "Course{" +
                        "businessId='" + businessId + '\'' +
                        ", courseMeans='" + courseMeans + '\'' +
                        ", courseName='" + courseName + '\'' +
                        ", courseSerialId='" + courseSerialId + '\'' +
                        ", courseStatus='" + courseStatus + '\'' +
                        ", courseTypeId='" + courseTypeId + '\'' +
                        ", courseTypeName='" + courseTypeName + '\'' +
                        ", createTime='" + createTime + '\'' +
                        ", creator='" + creator + '\'' +
                        ", duration='" + duration + '\'' +
                        ", iconPath='" + iconPath + '\'' +
                        ", id='" + id + '\'' +
                        ", isExperience='" + isExperience + '\'' +
                        ", remark='" + remark + '\'' +
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
