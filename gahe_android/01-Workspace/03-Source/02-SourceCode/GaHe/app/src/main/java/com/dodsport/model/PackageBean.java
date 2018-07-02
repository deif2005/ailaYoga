package com.dodsport.model;

import java.io.Serializable;

/**
 *
 * 微信支付回调
 * Created by Administrator on 2017/11/15.
 */

public class PackageBean implements Serializable{


    /**
     * datas : {"package":"Sign=WXPay","appid":"wxc03f87c55f4f27f9","sign":"75518532D2D61243B18082237AFCD528","partnerid":"1490064182","prepayid":"wx20171115142250bcfebf78c40123460847","noncestr":"evtE3RGI6VCHvsHQyuCtODWW1pec4SGw","timestamp":"1510726966"}
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
        /**
         * package : Sign=WXPay
         * appid : wxc03f87c55f4f27f9
         * sign : 75518532D2D61243B18082237AFCD528
         * partnerid : 1490064182
         * prepayid : wx20171115142250bcfebf78c40123460847
         * noncestr : evtE3RGI6VCHvsHQyuCtODWW1pec4SGw
         * timestamp : 1510726966
         */

        private String appid;
        private String sign;
        private String partnerid;
        private String prepayid;
        private String noncestr;
        private String timestamp;




        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public DatasBean() {
        }

        @Override
        public String toString() {
            return "DatasBean{" +
                    ", appid='" + appid + '\'' +
                    ", sign='" + sign + '\'' +
                    ", partnerid='" + partnerid + '\'' +
                    ", prepayid='" + prepayid + '\'' +
                    ", noncestr='" + noncestr + '\'' +
                    ", timestamp='" + timestamp + '\'' +
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
}
