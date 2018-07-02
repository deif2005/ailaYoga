package com.dodsport.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/21.
 *
 * 卡券操作 会员卡列表
 */

public class MemberMyCardListBean implements Serializable {


    /**
     * datas : {"membercardrelationList":[{"actTime":"","balance":"688.00","businessId":"f28bcf52-09f9-4fdf-8c86-c2dcf915b41c","cardStatus":1,"cardtypeId":"","cardtypeName":"","createTime":"2017-09-20 17:50:07.0","creator":"673eb481-02e0-418b-bd0a-9b780e40ce77","days":0,"giveTimes":0,"id":"81e7bf07-ff87-41df-a27b-0a8014b8a9e3","membcardId":"7f36aa0a-a371-4c15-a700-2a2d79f273f3","membcardName":"","membcardType":2,"memberId":"2bb4e3f3-4ba7-4bc8-92ea-80442b3648ab","memberName":"","opencardSerialId":"000000005","phoneNum":"","remark":"","rownum":"","specactTime":"2017-10-20 17:48:24.0","storeId":"57f22b9c-eb5c-45dc-8493-3203217aae3f","times":0,"validityTime":"2017-10-20 17:48:25.0","validityType":0}]}
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

    public MemberMyCardListBean() {
    }

    public static class DatasBean implements Serializable{
        private List<MembercardrelationListBean> membercardrelationList;

        public List<MembercardrelationListBean> getMembercardrelationList() {
            return membercardrelationList;
        }

        public void setMembercardrelationList(List<MembercardrelationListBean> membercardrelationList) {
            this.membercardrelationList = membercardrelationList;
        }

        public static class MembercardrelationListBean implements Serializable{


            /**
             * actTime :
             * balance : 4864.00
             * businessId : f28bcf52-09f9-4fdf-8c86-c2dcf915b41c
             * cardStatus : 1
             * cardtypeName :
             * createTime : 2017-09-20 19:05:15.0
             * creator : 673eb481-02e0-418b-bd0a-9b780e40ce77
             * days : 0
             * flagType : 1
             * giveTimes : 23
             * id : 2e1cb77b-394b-4518-a8a5-834304129681
             * membcardId : 7ea3f002-4e67-4a41-9342-db15eb5af254
             * membcardName : 至尊股东会员卡
             * membcardType : 1
             * memberCard : null
             * memberId : a28b21c5-58c7-4834-91ee-d89fb8a57bcb
             * memberName :
             * opencardSerialId : 000000007
             * phoneNum :
             * remark :
             * rownum :
             * specactTime : 2017-10-20 19:03:32.0
             * storeId : 57f22b9c-eb5c-45dc-8493-3203217aae3f
             * times : 43
             * validityTime :
             * validityType : 1
             */

            private String actTime;
            private String balance;
            private String businessId;
            private int cardStatus;
            private String cardtypeName;
            private String createTime;
            private String creator;
            private int days;
            private int flagType;
            private int giveTimes;
            private String id;
            private String membcardId;
            private String membcardName;
            private int membcardType;
            private Object memberCard;
            private String memberId;
            private String memberName;
            private String opencardSerialId;
            private String phoneNum;
            private String remark;
            private String rownum;
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

            public int getFlagType() {
                return flagType;
            }

            public void setFlagType(int flagType) {
                this.flagType = flagType;
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

            public Object getMemberCard() {
                return memberCard;
            }

            public void setMemberCard(Object memberCard) {
                this.memberCard = memberCard;
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

            public String getRownum() {
                return rownum;
            }

            public void setRownum(String rownum) {
                this.rownum = rownum;
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

            public MembercardrelationListBean() {
            }

            @Override
            public String toString() {
                return "MembercardrelationListBean{" +
                        "actTime='" + actTime + '\'' +
                        ", balance='" + balance + '\'' +
                        ", businessId='" + businessId + '\'' +
                        ", cardStatus=" + cardStatus +
                        ", cardtypeName='" + cardtypeName + '\'' +
                        ", createTime='" + createTime + '\'' +
                        ", creator='" + creator + '\'' +
                        ", days=" + days +
                        ", flagType=" + flagType +
                        ", giveTimes=" + giveTimes +
                        ", id='" + id + '\'' +
                        ", membcardId='" + membcardId + '\'' +
                        ", membcardName='" + membcardName + '\'' +
                        ", membcardType=" + membcardType +
                        ", memberCard=" + memberCard +
                        ", memberId='" + memberId + '\'' +
                        ", memberName='" + memberName + '\'' +
                        ", opencardSerialId='" + opencardSerialId + '\'' +
                        ", phoneNum='" + phoneNum + '\'' +
                        ", remark='" + remark + '\'' +
                        ", rownum='" + rownum + '\'' +
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
