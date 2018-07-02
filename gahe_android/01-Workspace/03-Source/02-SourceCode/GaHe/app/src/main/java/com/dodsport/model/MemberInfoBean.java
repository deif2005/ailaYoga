package com.dodsport.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/20.
 *
 * 会员信息
 */

public class MemberInfoBean implements Serializable {


    /**
     * datas : {"baseMember":{"balance":"","birthday":"2017-09-13 00:00:00.0","businessId":"f28bcf52-09f9-4fdf-8c86-c2dcf915b41c","createTime":"2017-09-11 19:56:56.0","creator":"bdc13c70-de9b-47e0-b59d-ea90e8926ee9","email":"","id":"2bb4e3f3-4ba7-4bc8-92ea-80442b3648ab","idCard":"","memberHead":"","memberName":"马军1","memberSerialId":"041","memberTags":0,"phoneNum":"15100000000","remark":"更改了马军的信息","sex":1,"storeId":"57f22b9c-eb5c-45dc-8493-3203217aae3f"}}
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

    public MemberInfoBean() {
    }

    @Override
    public String toString() {
        return "MemberInfoBean{" +
                "datas=" + datas +
                ", result=" + result +
                '}';
    }

    public static class DatasBean implements Serializable{
        /**
         * baseMember : {"balance":"","birthday":"2017-09-13 00:00:00.0","businessId":"f28bcf52-09f9-4fdf-8c86-c2dcf915b41c","createTime":"2017-09-11 19:56:56.0","creator":"bdc13c70-de9b-47e0-b59d-ea90e8926ee9","email":"","id":"2bb4e3f3-4ba7-4bc8-92ea-80442b3648ab","idCard":"","memberHead":"","memberName":"马军1","memberSerialId":"041","memberTags":0,"phoneNum":"15100000000","remark":"更改了马军的信息","sex":1,"storeId":"57f22b9c-eb5c-45dc-8493-3203217aae3f"}
         */

        private BaseMemberBean baseMember;

        public BaseMemberBean getBaseMember() {
            return baseMember;
        }

        public void setBaseMember(BaseMemberBean baseMember) {
            this.baseMember = baseMember;
        }

        public DatasBean() {
        }

        @Override
        public String toString() {
            return "DatasBean{" +
                    "baseMember=" + baseMember +
                    '}';
        }

        public static class BaseMemberBean implements Serializable{
            /**
             * balance :
             * birthday : 2017-09-13 00:00:00.0
             * businessId : f28bcf52-09f9-4fdf-8c86-c2dcf915b41c
             * createTime : 2017-09-11 19:56:56.0
             * creator : bdc13c70-de9b-47e0-b59d-ea90e8926ee9
             * email :
             * id : 2bb4e3f3-4ba7-4bc8-92ea-80442b3648ab
             * idCard :
             * memberHead :
             * memberName : 马军1
             * memberSerialId : 041
             * memberTags : 0
             * phoneNum : 15100000000
             * remark : 更改了马军的信息
             * sex : 1
             * storeId : 57f22b9c-eb5c-45dc-8493-3203217aae3f
             */

            private String balance;
            private String birthday;
            private String businessId;
            private String createTime;
            private String creator;
            private String email;
            private String id;
            private String idCard;
            private String memberHead;
            private String memberName;
            private String memberSerialId;
            private int memberTags;
            private String phoneNum;
            private String remark;
            private int sex;
            private String storeId;

            public String getBalance() {
                return balance;
            }

            public void setBalance(String balance) {
                this.balance = balance;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getBusinessId() {
                return businessId;
            }

            public void setBusinessId(String businessId) {
                this.businessId = businessId;
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

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getIdCard() {
                return idCard;
            }

            public void setIdCard(String idCard) {
                this.idCard = idCard;
            }

            public String getMemberHead() {
                return memberHead;
            }

            public void setMemberHead(String memberHead) {
                this.memberHead = memberHead;
            }

            public String getMemberName() {
                return memberName;
            }

            public void setMemberName(String memberName) {
                this.memberName = memberName;
            }

            public String getMemberSerialId() {
                return memberSerialId;
            }

            public void setMemberSerialId(String memberSerialId) {
                this.memberSerialId = memberSerialId;
            }

            public int getMemberTags() {
                return memberTags;
            }

            public void setMemberTags(int memberTags) {
                this.memberTags = memberTags;
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

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public String getStoreId() {
                return storeId;
            }

            public void setStoreId(String storeId) {
                this.storeId = storeId;
            }

            public BaseMemberBean() {
            }

            @Override
            public String toString() {
                return "BaseMemberBean{" +
                        "balance='" + balance + '\'' +
                        ", birthday='" + birthday + '\'' +
                        ", businessId='" + businessId + '\'' +
                        ", createTime='" + createTime + '\'' +
                        ", creator='" + creator + '\'' +
                        ", email='" + email + '\'' +
                        ", id='" + id + '\'' +
                        ", idCard='" + idCard + '\'' +
                        ", memberHead='" + memberHead + '\'' +
                        ", memberName='" + memberName + '\'' +
                        ", memberSerialId='" + memberSerialId + '\'' +
                        ", memberTags=" + memberTags +
                        ", phoneNum='" + phoneNum + '\'' +
                        ", remark='" + remark + '\'' +
                        ", sex=" + sex +
                        ", storeId='" + storeId + '\'' +
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
