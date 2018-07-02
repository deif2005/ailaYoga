package com.dodsport.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/15.
 *
 * 会员卡价格阶梯
 */

public class PricesListBean implements Serializable {


    /**
     * datas : {"list":[{"createTime":"2017-09-15 10:44:56.0","creator":"ddcbc213-3145-4b5d-a068-144c0b06706f","enabled":"1","id":"f13170e5-a115-4107-9883-8d2c6b01a9a1","membcardId":"1ec5b898-fa6d-4cf3-a5e4-17e4d79fc05f","months":0,"nominalAmount":"668.00","times":1},{"createTime":"2017-09-15 10:50:16.0","creator":"ddcbc213-3145-4b5d-a068-144c0b06706f","enabled":"1","id":"bcbc5ae2-c457-47e0-908c-44008081cbfe","membcardId":"1ec5b898-fa6d-4cf3-a5e4-17e4d79fc05f","months":0,"nominalAmount":"1088.00","times":5}]}
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

    public PricesListBean() {
    }

    @Override
    public String toString() {
        return "PricesListBean{" +
                "datas=" + datas +
                ", result=" + result +
                '}';
    }

    public static class DatasBean implements Serializable{
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable{
            /**
             * createTime : 2017-09-15 10:44:56.0
             * creator : ddcbc213-3145-4b5d-a068-144c0b06706f
             * enabled : 1
             * id : f13170e5-a115-4107-9883-8d2c6b01a9a1
             * membcardId : 1ec5b898-fa6d-4cf3-a5e4-17e4d79fc05f
             * months : 0
             * nominalAmount : 668.00
             * times : 1
             */

            private String createTime;
            private String creator;
            private String enabled;
            private String id;
            private String membcardId;
            private int months;
            private String nominalAmount;
            private int times;

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

            public String getEnabled() {
                return enabled;
            }

            public void setEnabled(String enabled) {
                this.enabled = enabled;
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

            public int getMonths() {
                return months;
            }

            public void setMonths(int months) {
                this.months = months;
            }

            public String getNominalAmount() {
                return nominalAmount;
            }

            public void setNominalAmount(String nominalAmount) {
                this.nominalAmount = nominalAmount;
            }

            public int getTimes() {
                return times;
            }

            public void setTimes(int times) {
                this.times = times;
            }

            public ListBean() {
            }

            @Override
            public String toString() {
                return "ListBean{" +
                        "createTime='" + createTime + '\'' +
                        ", creator='" + creator + '\'' +
                        ", enabled='" + enabled + '\'' +
                        ", id='" + id + '\'' +
                        ", membcardId='" + membcardId + '\'' +
                        ", months=" + months +
                        ", nominalAmount='" + nominalAmount + '\'' +
                        ", times=" + times +
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
