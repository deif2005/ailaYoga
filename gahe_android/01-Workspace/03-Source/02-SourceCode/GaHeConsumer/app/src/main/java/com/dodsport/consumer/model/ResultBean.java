package com.dodsport.consumer.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/31.
 * 返回状态
 */

public class ResultBean implements Serializable{


    /**
     * result : {"code":"0","msg":"sys_ok"}
     */

    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public ResultBean() {
    }

    @Override
    public String toString() {
        return "ResultBean{" +
                "result=" + result +
                '}';
    }

    public static class Result implements Serializable{
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

        public Result() {
        }

        @Override
        public String toString() {
            return "Result{" +
                    "code='" + code + '\'' +
                    ", msg='" + msg + '\'' +
                    '}';
        }
    }
}
