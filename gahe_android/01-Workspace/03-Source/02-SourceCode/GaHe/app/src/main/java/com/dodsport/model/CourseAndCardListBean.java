package com.dodsport.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/12.
 *
 * 课程关联卡 列表
 */

public class CourseAndCardListBean implements Serializable {


    /**
     * datas : {"courseAndCardList":[{"courseId":"0240eeb9-4bc3-4f3b-a79e-02d8568ca243","enable":"1","id":"","membcardId":"1118bfae-d531-407d-be37-7dcf19114bc3","membcardName":"大宝贝儿"},{"courseId":"","enable":"0","id":"","membcardId":"6fae4ade-fcff-44bb-9b78-0818d2bf5919","membcardName":"yyy "},{"courseId":"","enable":"0","id":"","membcardId":"a2a30a41-b135-4c76-9618-31c46dc803d4","membcardName":"青春靓丽卡"}]}
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
        private List<CourseAndCardList> courseAndCardList;

        public List<CourseAndCardList> getCourseAndCardList() {
            return courseAndCardList;
        }

        public void setCourseAndCardList(List<CourseAndCardList> courseAndCardList) {
            this.courseAndCardList = courseAndCardList;
        }

        public DatasBean() {
        }

        public static class CourseAndCardList implements Serializable{

            /**
             * courseId : 0240eeb9-4bc3-4f3b-a79e-02d8568ca243
             * enable : 1
             * id :
             * membcardId : 1118bfae-d531-407d-be37-7dcf19114bc3
             * membcardName : 大宝贝儿
             * membcardType : 2
             */

            private String courseId;
            private String enable;
            private String id;
            private String membcardId;
            private String membcardName;
            private int membcardType;
            private String status;

            public String getCourseId() {
                return courseId;
            }

            public void setCourseId(String courseId) {
                this.courseId = courseId;
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

            public String getMembcardId() {
                return membcardId;
            }

            public void setMembcardId(String membcardId) {
                this.membcardId = membcardId;
            }

            public String getMembcardName() {
                return membcardName;
            }

            public void setMembcardName(String membcardName) {
                this.membcardName = membcardName;
            }

            public int getMembcardType() {
                return membcardType;
            }

            public void setMembcardType(int membcardType) {
                this.membcardType = membcardType;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public CourseAndCardList() {
            }

            @Override
            public String toString() {
                return "CourseAndCardList{" +
                        "courseId='" + courseId + '\'' +
                        ", enable='" + enable + '\'' +
                        ", id='" + id + '\'' +
                        ", membcardId='" + membcardId + '\'' +
                        ", membcardName='" + membcardName + '\'' +
                        ", membcardType=" + membcardType +
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
