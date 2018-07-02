package com.dodsport.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Administrator
 * @date 2017/10/23
 * 签到规则
 */

public class SignScopeBean implements Serializable {


    /**
     * datas : {"signScope":{"createTime":"2017-10-24 17:19:53.0","creator":"ddcbc213-3145-4b5d-a068-144c0b06706f","id":"e251b167-6dd6-4908-8505-3f28c4935c8b","lat":"22.71431","lng":"114.24508","radius":"1000","reissueTimes":"9","signAdd":"广东省深圳市龙岗区德政路39号靠近万科广场(西埔西街)","storeId":"5565f926-eb45-47c5-a297-ad2101533b19","vacationDays":"4"},"StoreWifi":{"id":"542b44d3-abc5-477a-a4d3-dd0e8497b159","storeId":"5565f926-eb45-47c5-a297-ad2101533b19","wifiId":"02:95:8e:3b:d3:20"},"signTimeList":[{"createTime":"","creator":"1d3c6081-b4a8-4752-856a-267da26c6350","firstTime":"14:00:00","id":"117e65d5-1898-4a1d-ac40-1e111abe4c9c","lastTime":"21:00:00","schedulingType":2,"storeId":"5565f926-eb45-47c5-a297-ad2101533b19","timeScope":"30"},{"createTime":"","creator":"1d3c6081-b4a8-4752-856a-267da26c6350","firstTime":"09:00:00","id":"7c9993ff-e8ac-49b6-8e22-75fb4267734d","lastTime":"18:00:00","schedulingType":1,"storeId":"5565f926-eb45-47c5-a297-ad2101533b19","timeScope":"30"}]}
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

    public SignScopeBean() {
    }

    public static class DatasBean implements Serializable{
        /**
         * signScope : {"createTime":"2017-10-24 17:19:53.0","creator":"ddcbc213-3145-4b5d-a068-144c0b06706f","id":"e251b167-6dd6-4908-8505-3f28c4935c8b","lat":"22.71431","lng":"114.24508","radius":"1000","reissueTimes":"9","signAdd":"广东省深圳市龙岗区德政路39号靠近万科广场(西埔西街)","storeId":"5565f926-eb45-47c5-a297-ad2101533b19","vacationDays":"4"}
         * StoreWifi : {"id":"542b44d3-abc5-477a-a4d3-dd0e8497b159","storeId":"5565f926-eb45-47c5-a297-ad2101533b19","wifiId":"02:95:8e:3b:d3:20"}
         * signTimeList : [{"createTime":"","creator":"1d3c6081-b4a8-4752-856a-267da26c6350","firstTime":"14:00:00","id":"117e65d5-1898-4a1d-ac40-1e111abe4c9c","lastTime":"21:00:00","schedulingType":2,"storeId":"5565f926-eb45-47c5-a297-ad2101533b19","timeScope":"30"},{"createTime":"","creator":"1d3c6081-b4a8-4752-856a-267da26c6350","firstTime":"09:00:00","id":"7c9993ff-e8ac-49b6-8e22-75fb4267734d","lastTime":"18:00:00","schedulingType":1,"storeId":"5565f926-eb45-47c5-a297-ad2101533b19","timeScope":"30"}]
         */

        private SignScope signScope;
        private StoreWifiBean StoreWifi;
        private List<SignTimeListBean> signTimeList;

        public SignScope getSignScope() {
            return signScope;
        }

        public void setSignScope(SignScope signScope) {
            this.signScope = signScope;
        }

        public StoreWifiBean getStoreWifi() {
            return StoreWifi;
        }

        public void setStoreWifi(StoreWifiBean StoreWifi) {
            this.StoreWifi = StoreWifi;
        }

        public List<SignTimeListBean> getSignTimeList() {
            return signTimeList;
        }

        public void setSignTimeList(List<SignTimeListBean> signTimeList) {
            this.signTimeList = signTimeList;
        }

        public DatasBean(SignScope signScope) {
            this.signScope = signScope;
        }

        public DatasBean() {
        }

        @Override
        public String toString() {
            return "DatasBean{" +
                    "signScope=" + signScope +
                    ", StoreWifi=" + StoreWifi +
                    ", signTimeList=" + signTimeList +
                    '}';
        }

        public static class SignScope implements Serializable {
            /**
             * createTime : 2017-10-24 17:19:53.0
             * creator : ddcbc213-3145-4b5d-a068-144c0b06706f
             * id : e251b167-6dd6-4908-8505-3f28c4935c8b
             * lat : 22.71431
             * lng : 114.24508
             * radius : 1000
             * reissueTimes : 9
             * signAdd : 广东省深圳市龙岗区德政路39号靠近万科广场(西埔西街)
             * storeId : 5565f926-eb45-47c5-a297-ad2101533b19
             * vacationDays : 4
             */

            private String createTime;
            private String creator;
            private String id;
            private String lat;
            private String lng;
            private String radius;
            private String reissueTimes;
            private String signAdd;
            private String storeId;
            private String vacationDays;

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

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }

            public String getRadius() {
                return radius;
            }

            public void setRadius(String radius) {
                this.radius = radius;
            }

            public String getReissueTimes() {
                return reissueTimes;
            }

            public void setReissueTimes(String reissueTimes) {
                this.reissueTimes = reissueTimes;
            }

            public String getSignAdd() {
                return signAdd;
            }

            public void setSignAdd(String signAdd) {
                this.signAdd = signAdd;
            }

            public String getStoreId() {
                return storeId;
            }

            public void setStoreId(String storeId) {
                this.storeId = storeId;
            }

            public String getVacationDays() {
                return vacationDays;
            }

            public void setVacationDays(String vacationDays) {
                this.vacationDays = vacationDays;
            }

            public SignScope() {
            }

            @Override
            public String toString() {
                return "SignScope{" +
                        "createTime='" + createTime + '\'' +
                        ", creator='" + creator + '\'' +
                        ", id='" + id + '\'' +
                        ", lat='" + lat + '\'' +
                        ", lng='" + lng + '\'' +
                        ", radius='" + radius + '\'' +
                        ", reissueTimes='" + reissueTimes + '\'' +
                        ", signAdd='" + signAdd + '\'' +
                        ", storeId='" + storeId + '\'' +
                        ", vacationDays='" + vacationDays + '\'' +
                        '}';
            }
        }

        public static class StoreWifiBean implements Serializable{
            /**
             * id : 542b44d3-abc5-477a-a4d3-dd0e8497b159
             * storeId : 5565f926-eb45-47c5-a297-ad2101533b19
             * wifiId : 02:95:8e:3b:d3:20
             */

            private String id;
            private String storeId;
            private String wifiId;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getStoreId() {
                return storeId;
            }

            public void setStoreId(String storeId) {
                this.storeId = storeId;
            }

            public String getWifiId() {
                return wifiId;
            }

            public void setWifiId(String wifiId) {
                this.wifiId = wifiId;
            }

            public StoreWifiBean() {
            }

            @Override
            public String toString() {
                return "StoreWifiBean{" +
                        "id='" + id + '\'' +
                        ", storeId='" + storeId + '\'' +
                        ", wifiId='" + wifiId + '\'' +
                        '}';
            }
        }

        public static class SignTimeListBean implements Serializable{
            /**
             * createTime :
             * creator : 1d3c6081-b4a8-4752-856a-267da26c6350
             * firstTime : 14:00:00
             * id : 117e65d5-1898-4a1d-ac40-1e111abe4c9c
             * lastTime : 21:00:00
             * schedulingType : 2
             * storeId : 5565f926-eb45-47c5-a297-ad2101533b19
             * timeScope : 30
             */

            private String createTime;
            private String creator;
            private String firstTime;
            private String id;
            private String lastTime;
            private int schedulingType;
            private String storeId;
            private String timeScope;

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

            public String getFirstTime() {
                return firstTime;
            }

            public void setFirstTime(String firstTime) {
                this.firstTime = firstTime;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLastTime() {
                return lastTime;
            }

            public void setLastTime(String lastTime) {
                this.lastTime = lastTime;
            }

            public int getSchedulingType() {
                return schedulingType;
            }

            public void setSchedulingType(int schedulingType) {
                this.schedulingType = schedulingType;
            }

            public String getStoreId() {
                return storeId;
            }

            public void setStoreId(String storeId) {
                this.storeId = storeId;
            }

            public String getTimeScope() {
                return timeScope;
            }

            public void setTimeScope(String timeScope) {
                this.timeScope = timeScope;
            }

            public SignTimeListBean() {
            }

            @Override
            public String toString() {
                return "SignTimeListBean{" +
                        "createTime='" + createTime + '\'' +
                        ", creator='" + creator + '\'' +
                        ", firstTime='" + firstTime + '\'' +
                        ", id='" + id + '\'' +
                        ", lastTime='" + lastTime + '\'' +
                        ", schedulingType=" + schedulingType +
                        ", storeId='" + storeId + '\'' +
                        ", timeScope='" + timeScope + '\'' +
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
