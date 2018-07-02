package com.dodsport.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/18.
 *
 * 会员卡 列表
 */

public class MemberCardRelationListBean implements Serializable {


    /**
     * datas : {"membercardrelationList":[{"actTime":"","balance":"","businessId":"","cardStatus":1,"cardtypeId":"","cardtypeName":"","createTime":"","creator":"","days":0,"giveTimes":0,"id":"9828a429-eeab-469a-925b-642c16f12d19","membcardId":"c47658bb-4b81-42fe-a156-4ef04d706476","membcardName":"VIP特色课程尊享卡","membcardType":1,"memberId":"2bb4e3f3-4ba7-4bc8-92ea-80442b3648ab","memberName":"马军1","opencardSerialId":"000013","phoneNum":"15100000000","remark":"","specactTime":"","storeId":"57f22b9c-eb5c-45dc-8493-3203217aae3f","times":100,"validityTime":"","validityType":0},{"actTime":"","balance":"","businessId":"","cardStatus":1,"cardtypeId":"","cardtypeName":"","createTime":"","creator":"","days":360,"giveTimes":0,"id":"e62b8740-edff-440a-a093-eae178890036","membcardId":"fa51e1d4-da4c-4c44-908d-0874fc3c8d78","membcardName":"VIP钻石会员卡","membcardType":2,"memberId":"2bb4e3f3-4ba7-4bc8-92ea-80442b3648ab","memberName":"马军1","opencardSerialId":"000006","phoneNum":"15100000000","remark":"","specactTime":"","storeId":"57f22b9c-eb5c-45dc-8493-3203217aae3f","times":0,"validityTime":"2018-09-13 16:14:05.0","validityType":0},{"actTime":"","balance":"","businessId":"","cardStatus":1,"cardtypeId":"","cardtypeName":"","createTime":"","creator":"","days":8,"giveTimes":0,"id":"1","membcardId":"fa51e1d4-da4c-4c44-908d-0874fc3c8d78","membcardName":"VIP钻石会员卡","membcardType":2,"memberId":"2bb4e3f3-4ba7-4bc8-92ea-80442b3648ab","memberName":"马军1","opencardSerialId":"000005","phoneNum":"15100000000","remark":"","specactTime":"","storeId":"57f22b9c-eb5c-45dc-8493-3203217aae3f","times":0,"validityTime":"2017-09-26 15:57:15.0","validityType":0}]}
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
        private List<MembercardRelationList> membercardrelationList;

        public List<MembercardRelationList> getMembercardrelationList() {
            return membercardrelationList;
        }

        public void setMembercardrelationList(List<MembercardRelationList> membercardrelationList) {
            this.membercardrelationList = membercardrelationList;
        }

        public DatasBean() {
        }

        @Override
        public String toString() {
            return "DatasBean{" +
                    "membercardrelationList=" + membercardrelationList +
                    '}';
        }

        public static class MembercardRelationList implements Serializable{
            /**
             * actTime :
             * balance :
             * businessId :
             * cardStatus : 1
             * cardtypeId :
             * cardtypeName :
             * createTime :
             * creator :
             * days : 0
             * giveTimes : 0
             * id : 9828a429-eeab-469a-925b-642c16f12d19
             * membcardId : c47658bb-4b81-42fe-a156-4ef04d706476
             * membcardName : VIP特色课程尊享卡
             * membcardType : 1
             * memberId : 2bb4e3f3-4ba7-4bc8-92ea-80442b3648ab
             * memberName : 马军1
             * opencardSerialId : 000013
             * phoneNum : 15100000000
             * remark :
             * specactTime :
             * storeId : 57f22b9c-eb5c-45dc-8493-3203217aae3f
             * times : 100
             * validityTime :
             * validityType : 0
             */

            private String actTime;
            private String balance;
            private String businessId;
            private int cardStatus;
            private String cardtypeId;
            private String cardtypeName;
            private String createTime;
            private String creator;
            private int days;
            private int giveTimes;
            private String id;
            private String membcardId;
            private String membcardName;
            private int membcardType;
            private String memberId;
            private String memberName;
            private String opencardSerialId;
            private String phoneNum;
            private String remark;
            private String specactTime;
            private String storeId;
            private int times;
            private String validityTime;
            private int validityType;

            public String getActTime() {
                return actTime;
            }

            public void setActTime(String actTime) {
                this.actTime = actTime;
            }

            public String getBalance() {
                return balance;
            }

            public void setBalance(String balance) {
                this.balance = balance;
            }

            public String getBusinessId() {
                return businessId;
            }

            public void setBusinessId(String businessId) {
                this.businessId = businessId;
            }

            public int getCardStatus() {
                return cardStatus;
            }

            public void setCardStatus(int cardStatus) {
                this.cardStatus = cardStatus;
            }

            public String getCardtypeId() {
                return cardtypeId;
            }

            public void setCardtypeId(String cardtypeId) {
                this.cardtypeId = cardtypeId;
            }

            public String getCardtypeName() {
                return cardtypeName;
            }

            public void setCardtypeName(String cardtypeName) {
                this.cardtypeName = cardtypeName;
            }

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

            public int getDays() {
                return days;
            }

            public void setDays(int days) {
                this.days = days;
            }

            public int getGiveTimes() {
                return giveTimes;
            }

            public void setGiveTimes(int giveTimes) {
                this.giveTimes = giveTimes;
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

            public String getMembcardName() {
                return membcardName;
            }

            public void setMembcardName(String membcardName) {
                this.membcardName = membcardName;
            }

            public int getMembcardType() {
                return membcardType;
            }

            public void setMembcardType(int membcardType) {
                this.membcardType = membcardType;
            }

            public String getMemberId() {
                return memberId;
            }

            public void setMemberId(String memberId) {
                this.memberId = memberId;
            }

            public String getMemberName() {
                return memberName;
            }

            public void setMemberName(String memberName) {
                this.memberName = memberName;
            }

            public String getOpencardSerialId() {
                return opencardSerialId;
            }

            public void setOpencardSerialId(String opencardSerialId) {
                this.opencardSerialId = opencardSerialId;
            }

            public String getPhoneNum() {
                return phoneNum;
            }

            public void setPhoneNum(String phoneNum) {
                this.phoneNum = phoneNum;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getSpecactTime() {
                return specactTime;
            }

            public void setSpecactTime(String specactTime) {
                this.specactTime = specactTime;
            }

            public String getStoreId() {
                return storeId;
            }

            public void setStoreId(String storeId) {
                this.storeId = storeId;
            }

            public int getTimes() {
                return times;
            }

            public void setTimes(int times) {
                this.times = times;
            }

            public String getValidityTime() {
                return validityTime;
            }

            public void setValidityTime(String validityTime) {
                this.validityTime = validityTime;
            }

            public int getValidityType() {
                return validityType;
            }

            public void setValidityType(int validityType) {
                this.validityType = validityType;
            }

            public MembercardRelationList() {
            }

            @Override
            public String toString() {
                return "MembercardrelationListBean{" +
                        "actTime='" + actTime + '\'' +
                        ", balance='" + balance + '\'' +
                        ", businessId='" + businessId + '\'' +
                        ", cardStatus=" + cardStatus +
                        ", cardtypeId='" + cardtypeId + '\'' +
                        ", cardtypeName='" + cardtypeName + '\'' +
                        ", createTime='" + createTime + '\'' +
                        ", creator='" + creator + '\'' +
                        ", days=" + days +
                        ", giveTimes=" + giveTimes +
                        ", id='" + id + '\'' +
                        ", membcardId='" + membcardId + '\'' +
                        ", membcardName='" + membcardName + '\'' +
                        ", membcardType=" + membcardType +
                        ", memberId='" + memberId + '\'' +
                        ", memberName='" + memberName + '\'' +
                        ", opencardSerialId='" + opencardSerialId + '\'' +
                        ", phoneNum='" + phoneNum + '\'' +
                        ", remark='" + remark + '\'' +
                        ", specactTime='" + specactTime + '\'' +
                        ", storeId='" + storeId + '\'' +
                        ", times=" + times +
                        ", validityTime='" + validityTime + '\'' +
                        ", validityType=" + validityType +
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
