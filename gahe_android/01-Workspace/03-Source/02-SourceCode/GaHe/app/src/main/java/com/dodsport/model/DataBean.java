package com.dodsport.model;

/**
 * Created by Administrator on 2017/8/23.
 */

public class DataBean {

    /**
     * datas : {"resultStr":"sign_flase"}
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

    public static class DatasBean {
        /**
         * resultStr : sign_flase
         */

        private String resultStr;

        public String getResultStr() {
            return resultStr;
        }

        public void setResultStr(String resultStr) {
            this.resultStr = resultStr;
        }
    }

    public static class ResultBean {
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
    }
}
