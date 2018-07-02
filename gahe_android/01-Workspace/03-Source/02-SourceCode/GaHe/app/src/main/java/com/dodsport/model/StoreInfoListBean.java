package com.dodsport.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/15.
 *
 * 门店信息和门店关联会员卡
 */

public class StoreInfoListBean implements Serializable {


    /**
     * datas : {"storeInfoListlist":[{"address":"","businessId":"","cid":"1c1e8ecb-ee4e-4931-ba8f-6057d84ccf56","create_time":null,"creator":"","id":"ee810841-977e-432d-9a8e-ee660563b302","status":1,"storeName":"万店瑜伽分馆1","storeSerialId":""},{"address":"","businessId":"","cid":"4d1ba89b-30f7-4371-af02-2e5b0e7e9c55","create_time":null,"creator":"","id":"57f22b9c-eb5c-45dc-8493-3203217aae3f","status":1,"storeName":"万店瑜伽分馆2","storeSerialId":""}]}
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

    public StoreInfoListBean() {
    }

    @Override
    public String toString() {
        return "StoreInfoListBean{" +
                "datas=" + datas +
                ", result=" + result +
                '}';
    }

    public static class DatasBean implements Serializable{
        private List<StoreInfoList> storeInfoListlist;

        public List<StoreInfoList> getStoreInfoListlist() {
            return storeInfoListlist;
        }

        public void setStoreInfoListlist(List<StoreInfoList> storeInfoListlist) {
            this.storeInfoListlist = storeInfoListlist;
        }

        public DatasBean() {
        }

        @Override
        public String toString() {
            return "DatasBean{" +
                    "storeInfoListlist=" + storeInfoListlist +
                    '}';
        }

        public static class StoreInfoList implements Serializable{
            /**
             * address :
             * businessId :
             * cid : 1c1e8ecb-ee4e-4931-ba8f-6057d84ccf56
             * create_time : null
             * creator :
             * id : ee810841-977e-432d-9a8e-ee660563b302
             * status : 1
             * storeName : 万店瑜伽分馆1
             * storeSerialId :
             */

            private String address;
            private String businessId;
            private String cid;
            private Object create_time;
            private String creator;
            private String id;
            private int status;
            private String storeName;
            private String storeSerialId;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getBusinessId() {
                return businessId;
            }

            public void setBusinessId(String businessId) {
                this.businessId = businessId;
            }

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public Object getCreate_time() {
                return create_time;
            }

            public void setCreate_time(Object create_time) {
                this.create_time = create_time;
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

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getStoreName() {
                return storeName;
            }

            public void setStoreName(String storeName) {
                this.storeName = storeName;
            }

            public String getStoreSerialId() {
                return storeSerialId;
            }

            public void setStoreSerialId(String storeSerialId) {
                this.storeSerialId = storeSerialId;
            }

            public StoreInfoList() {
            }

            @Override
            public String toString() {
                return "StoreInfoList{" +
                        "address='" + address + '\'' +
                        ", businessId='" + businessId + '\'' +
                        ", cid='" + cid + '\'' +
                        ", create_time=" + create_time +
                        ", creator='" + creator + '\'' +
                        ", id='" + id + '\'' +
                        ", status=" + status +
                        ", storeName='" + storeName + '\'' +
                        ", storeSerialId='" + storeSerialId + '\'' +
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
