package com.dodsport.consumer.model;

import java.io.Serializable;

/**
 *
 * Token 实体类
 * @author Administrator
 * @date 2017/10/30
 */

public class TokenBean implements Serializable{


    /**
     * datas : {"token":"650a32522a8d456ebded49307b9586bb"}
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

    public TokenBean() {
    }

    public static class DatasBean implements Serializable{
        /**
         * token : 650a32522a8d456ebded49307b9586bb
         */

        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public DatasBean() {
        }

        @Override
        public String toString() {
            return "DatasBean{" +
                    "token='" + token + '\'' +
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
        return "TokenBean{" +
                "datas=" + datas +
                ", result=" + result +
                '}';
    }
}
