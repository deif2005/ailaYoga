package com.dodsport.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/11.
 */

public class ExpensesBean implements Serializable {


    /**
     * datas : {"showPathList":["http://192.168.1.186:8088/dod-static/upload/picture/expenseAccount/bdc13c70-de9b-47e0-b59d-ea90e8926ee9/b162e84e-38a6-41ff-89f2-91ef3a621e7e.jpg","http://192.168.1.186:8088/dod-static/upload/picture/expenseAccount/bdc13c70-de9b-47e0-b59d-ea90e8926ee9/29f6067d-2365-4ce1-bcfd-d72aaa921c09.jpg","http://192.168.1.186:8088/dod-static/upload/picture/expenseAccount/bdc13c70-de9b-47e0-b59d-ea90e8926ee9/83f5d463-d733-427e-ba36-9fe93e5193cc.jpg"]}
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

    public ExpensesBean() {
    }

    public static class DatasBean implements Serializable{
        private List<String> showPathList;

        public List<String> getShowPathList() {
            return showPathList;
        }

        public void setShowPathList(List<String> showPathList) {
            this.showPathList = showPathList;
        }

        public DatasBean() {
        }

        @Override
        public String toString() {
            return "DatasBean{" +
                    "showPathList=" + showPathList +
                    '}';
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

    @Override
    public String toString() {
        return "ExpensesBean{" +
                "datas=" + datas +
                ", result=" + result +
                '}';
    }
}
