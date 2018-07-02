package com.dodsport.consumer.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Administrator
 * @date 2017/11/2
 *
 * 可预约老师的列表
 */

public class CoachInfoListBean implements Serializable {


    /**
     * datas : {"coachInfoList":[{"empHead":"","empId":"","employeeName":"李二牛","id":"085c61e9-8e53-4839-8358-d9f06df41581","jobTitle":2,"picPathList":[],"selfIntroduction":""},{"empHead":"","empId":"","employeeName":"胡鹏","id":"273ee8c3-4e02-498c-9317-ea8dd7fab45e","jobTitle":2,"picPathList":[],"selfIntroduction":""}]}
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
        private List<CoachInfoList> coachInfoList;

        public List<CoachInfoList> getCoachInfoList() {
            return coachInfoList;
        }

        public void setCoachInfoList(List<CoachInfoList> coachInfoList) {
            this.coachInfoList = coachInfoList;
        }

        public DatasBean() {
        }

        @Override
        public String toString() {
            return "DatasBean{" +
                    "coachInfoList=" + coachInfoList +
                    '}';
        }

        public static class CoachInfoList implements Serializable{
            /**
             * empHead :
             * empId :
             * employeeName : 李二牛
             * id : 085c61e9-8e53-4839-8358-d9f06df41581
             * jobTitle : 2
             * picPathList : []
             * selfIntroduction :
             */

            private String empHead;
            private String empId;
            private String employeeName;
            private String id;
            private int jobTitle;
            private String selfIntroduction;
            private List<?> picPathList;

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

            public int getJobTitle() {
                return jobTitle;
            }

            public void setJobTitle(int jobTitle) {
                this.jobTitle = jobTitle;
            }

            public String getSelfIntroduction() {
                return selfIntroduction;
            }

            public void setSelfIntroduction(String selfIntroduction) {
                this.selfIntroduction = selfIntroduction;
            }

            public List<?> getPicPathList() {
                return picPathList;
            }

            public void setPicPathList(List<?> picPathList) {
                this.picPathList = picPathList;
            }

            public CoachInfoList() {
            }

            @Override
            public String toString() {
                return "CoachInfoList{" +
                        "empHead='" + empHead + '\'' +
                        ", empId='" + empId + '\'' +
                        ", employeeName='" + employeeName + '\'' +
                        ", id='" + id + '\'' +
                        ", jobTitle=" + jobTitle +
                        ", selfIntroduction='" + selfIntroduction + '\'' +
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

    public CoachInfoListBean() {
    }


}
