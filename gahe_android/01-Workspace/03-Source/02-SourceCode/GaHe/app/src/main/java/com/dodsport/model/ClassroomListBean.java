package com.dodsport.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/29.
 *
 * 门店教室
 */

public class ClassroomListBean implements Serializable {


    /**
     * datas : {"classroomList":[{"classroomName":"大会堂","createTime":"2017-09-29 11:10:58.0","creator":"1","id":"ee810841-977e-432d-9a8e-ee660563b3a1","remark":"","storeId":"ee810841-977e-432d-9a8e-ee660563b302","storeName":""},{"classroomName":"大教室","createTime":"2017-09-21 17:53:51.0","creator":"1","id":"ee810841-977e-432d-9a8e-ee660563b3aa","remark":"","storeId":"ee810841-977e-432d-9a8e-ee660563b302","storeName":""}]}
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

    public ClassroomListBean() {
    }

    public static class DatasBean  implements Serializable{
        private List<ClassroomList> classroomList;

        public List<ClassroomList> getClassroomList() {
            return classroomList;
        }

        public void setClassroomList(List<ClassroomList> classroomList) {
            this.classroomList = classroomList;
        }

        public DatasBean() {
        }

        public static class ClassroomList implements Serializable{
            /**
             * classroomName : 大会堂
             * createTime : 2017-09-29 11:10:58.0
             * creator : 1
             * id : ee810841-977e-432d-9a8e-ee660563b3a1
             * remark :
             * storeId : ee810841-977e-432d-9a8e-ee660563b302
             * storeName :
             */

            private String classroomName;
            private String createTime;
            private String creator;
            private String id;
            private String remark;
            private String storeId;
            private String storeName;

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

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public String getStoreName() {
                return storeName;
            }

            public void setStoreName(String storeName) {
                this.storeName = storeName;
            }

            public ClassroomList() {
            }

            @Override
            public String toString() {
                return "ClassroomList{" +
                        "classroomName='" + classroomName + '\'' +
                        ", createTime='" + createTime + '\'' +
                        ", creator='" + creator + '\'' +
                        ", id='" + id + '\'' +
                        ", remark='" + remark + '\'' +
                        ", storeId='" + storeId + '\'' +
                        ", storeName='" + storeName + '\'' +
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
