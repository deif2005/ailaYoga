package com.dodsport.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/11.
 *
 * 课程类型列表
 */

public class CourseTypeListBean implements Serializable {


    /**
     * datas : {"courseTypeList":[{"businessId":"f28bcf52-09f9-4fdf-8c86-c2dcf915b41c","courseTypeName":"会员课","coursetypeSerialId":"002","createTime":"2017-09-04 19:58:09.0","creator":"bdc13c70-de9b-47e0-b59d-ea90e8926ee9","id":"08ba7331-c041-4e56-950a-917dea5233a3","status":"1"},{"businessId":"f28bcf52-09f9-4fdf-8c86-c2dcf915b41c","courseTypeName":"特色课","coursetypeSerialId":"009","createTime":"2017-08-24 11:32:35.0","creator":"bdc13c70-de9b-47e0-b59d-ea90e8926ee9","id":"1baadfb0-a3df-4aa1-886e-c4e29642773d","status":"1"},{"businessId":"f28bcf52-09f9-4fdf-8c86-c2dcf915b41c","courseTypeName":"小班课","coursetypeSerialId":"010","createTime":"2017-08-24 11:32:55.0","creator":"bdc13c70-de9b-47e0-b59d-ea90e8926ee9","id":"82711ecd-f54d-48f1-bfea-6c3118cbd1a5","status":"1"},{"businessId":"f28bcf52-09f9-4fdf-8c86-c2dcf915b41c","courseTypeName":"公开课","coursetypeSerialId":"007","createTime":"2017-08-23 17:16:49.0","creator":"bdc13c70-de9b-47e0-b59d-ea90e8926ee9","id":"8935dee8-d707-4d2e-8cd0-5ea13da350df","status":"1"}]}
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
        private List<CourseTypeList> courseTypeList;

        public List<CourseTypeList> getCourseTypeList() {
            return courseTypeList;
        }

        public void setCourseTypeList(List<CourseTypeList> courseTypeList) {
            this.courseTypeList = courseTypeList;
        }

        public DatasBean() {
        }

        public static class CourseTypeList implements Serializable{
            /**
             * businessId : f28bcf52-09f9-4fdf-8c86-c2dcf915b41c
             * courseTypeName : 会员课
             * coursetypeSerialId : 002
             * createTime : 2017-09-04 19:58:09.0
             * creator : bdc13c70-de9b-47e0-b59d-ea90e8926ee9
             * id : 08ba7331-c041-4e56-950a-917dea5233a3
             * status : 1
             */

            private String businessId;
            private String courseTypeName;
            private String coursetypeSerialId;
            private String createTime;
            private String creator;
            private String id;
            private String status;

            public String getBusinessId() {
                return businessId;
            }

            public void setBusinessId(String businessId) {
                this.businessId = businessId;
            }

            public String getCourseTypeName() {
                return courseTypeName;
            }

            public void setCourseTypeName(String courseTypeName) {
                this.courseTypeName = courseTypeName;
            }

            public String getCoursetypeSerialId() {
                return coursetypeSerialId;
            }

            public void setCoursetypeSerialId(String coursetypeSerialId) {
                this.coursetypeSerialId = coursetypeSerialId;
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

            public CourseTypeList() {
            }

            @Override
            public String toString() {
                return "CourseTypeList{" +
                        "businessId='" + businessId + '\'' +
                        ", courseTypeName='" + courseTypeName + '\'' +
                        ", coursetypeSerialId='" + coursetypeSerialId + '\'' +
                        ", createTime='" + createTime + '\'' +
                        ", creator='" + creator + '\'' +
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
