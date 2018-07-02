package com.dodsport.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/17.
 */

public class AliPayBean implements Serializable {
    /**
     * datas : app_id%3D2017100609158754%26biz_content%3D%7B%22out_trade_no%22%3A%2220171116134596000000000%22%2C%22total_amount%22%3A%220.01%22%2C%22subject%22%3A%221%22%2C%22timeout_express%22%3A%2230m%22%2C%22body%22%3A%22%E6%B5%8B%E8%AF%95%E6%94%AF%E4%BB%98%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22seller_id%22%3A%222088821277083684%22%7D%26charset%3Dutf-8%26format%3Djson%26method%3Dalipay.trade.app.pay%26notify_url%3Dhttps%3A%2F%2Fdocs.open.alipay.com%2F399%2F106844%2F%26timestamp%3D2017-11-17+14%3A19%3A47%26version%3D1.0%26sign_type%3DRSA2%26sign%3DRAPFw843ePnBPs8XdMLdke9xuEahcyCncS2FumdxyxdQ27KBCZzRIvgwA6m4IwddRT2qDB6NuX6scP0kJqCp0rkD0umm7ACnzv8S0jKQK6yux8l1O%2Bk3xGOaP3RB2yFACEiat26h%2F7rD9fDlHLbamGYW%2BF6MNOYNRpstVVxo4B4%3D
     * result : {"code":"0","msg":"sys_ok"}
     */

    private String datas;
    private ResultBean result;

    public String getDatas() {
        return datas;
    }

    public void setDatas(String datas) {
        this.datas = datas;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public AliPayBean() {
    }

    @Override
    public String toString() {
        return "AliPayBean{" +
                "datas='" + datas + '\'' +
                ", result=" + result +
                '}';
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

    /**
     * datas : app_id%3D2017100609158754%26biz_content%3D%7B%22out_trade_no%22%3A%22201711161345900000000%22%2C%22total_amount%22%3A%220.01%22%2C%22subject%22%3A%221%22%2C%22timeout_express%22%3A%2230m%22%2C%22body%22%3A%22%E6%B5%8B%E8%AF%95%E6%94%AF%E4%BB%98%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22seller_id%22%3A%222088821277083684%22%7D%26charset%3Dutf-8%26format%3Djson%26method%3Dalipay.trade.app.pay%26notify_url%3Dhttps%3A%2F%2Fdocs.open.alipay.com%2F399%2F106844%2F%26timestamp%3D2017-11-17+10%3A23%3A09%26version%3D1.0%26sign_type%3DRSA2%26sign%3DaRJeFpezfL%2F7XI5%2FSFD0vJt9XgAQJxB2VRLFoYUtvN0J%2FjebIdravJa7flYpQFFHsupSJotgjg2FviHi8lH%2Bl%2B%2FqUE7aA5eq1zQlXkiOAvAIZkl%2FUpCgCHq5k92JwS3Hc014NaKPvRFezNZqhu17ln0pmAsyqE8vmSuV2E5IFcg%3D
     * result : {"code":"0","msg":"sys_ok"}
     */





}
