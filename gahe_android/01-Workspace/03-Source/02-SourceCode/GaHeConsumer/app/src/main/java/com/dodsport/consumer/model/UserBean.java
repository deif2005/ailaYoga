package com.dodsport.consumer.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Administrator
 * @date 2017/10/31
 * 用户个人信息
 */

public class UserBean implements Serializable {

    /**
     * datas : {"loginClientUserInfo":{"balance":"0.00","birthday":"2002-10-30 00:00:00.0","businessId":"80a2aa90-2205-4453-8760-b395fb48746a","businessInfos":[],"businessName":"huz","createTime":"2017-10-30 16:32:25.0","creator":"bdc13c70-de9b-47e0-b59d-ea90e8926ee9","email":"","headPortrait":"","height":"","hobby":"","id":"fd893fb9-4a9d-49b0-aef1-9846a8cd36e8","list":[],"memberSerialId":"036","memberTags":"5","nickName":"红孩儿","phoneNum":"15180180485","remark":"","sex":"1","status":"1","storeId":"5565f926-eb45-47c5-a297-ad2101533b19","storeName":"最好的门店2","userId":"72f7532a-b157-4b08-9dd1-c0ddd455e90b","userName":"","userSerialId":"000008","weight":""}}
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

    public UserBean() {
    }

    public static class DatasBean implements Serializable{
        /**
         * loginClientUserInfo : {"balance":"0.00","birthday":"2002-10-30 00:00:00.0","businessId":"80a2aa90-2205-4453-8760-b395fb48746a","businessInfos":[],"businessName":"huz","createTime":"2017-10-30 16:32:25.0","creator":"bdc13c70-de9b-47e0-b59d-ea90e8926ee9","email":"","headPortrait":"","height":"","hobby":"","id":"fd893fb9-4a9d-49b0-aef1-9846a8cd36e8","list":[],"memberSerialId":"036","memberTags":"5","nickName":"红孩儿","phoneNum":"15180180485","remark":"","sex":"1","status":"1","storeId":"5565f926-eb45-47c5-a297-ad2101533b19","storeName":"最好的门店2","userId":"72f7532a-b157-4b08-9dd1-c0ddd455e90b","userName":"","userSerialId":"000008","weight":""}
         */

        private LoginClientUserInfoBean loginClientUserInfo;

        public LoginClientUserInfoBean getLoginClientUserInfo() {
            return loginClientUserInfo;
        }

        public void setLoginClientUserInfo(LoginClientUserInfoBean loginClientUserInfo) {
            this.loginClientUserInfo = loginClientUserInfo;
        }

        public DatasBean() {
        }

        public static class LoginClientUserInfoBean implements Serializable{
            /**
             * balance : 0.00
             * birthday : 2002-10-30 00:00:00.0
             * businessId : 80a2aa90-2205-4453-8760-b395fb48746a
             * businessInfos : []
             * businessName : huz
             * createTime : 2017-10-30 16:32:25.0
             * creator : bdc13c70-de9b-47e0-b59d-ea90e8926ee9
             * email :
             * headPortrait :
             * height :
             * hobby :
             * id : fd893fb9-4a9d-49b0-aef1-9846a8cd36e8
             * list : []
             * memberSerialId : 036
             * memberTags : 5
             * nickName : 红孩儿
             * phoneNum : 15180180485
             * remark :
             * sex : 1
             * status : 1
             * storeId : 5565f926-eb45-47c5-a297-ad2101533b19
             * storeName : 最好的门店2
             * userId : 72f7532a-b157-4b08-9dd1-c0ddd455e90b
             * userName :
             * userSerialId : 000008
             * weight :
             */

            private String balance;
            private String birthday;
            private String businessId;
            private String businessName;
            private String createTime;
            private String creator;
            private String email;
            private String headPortrait;
            private String height;
            private String hobby;
            private String id;
            private String memberSerialId;
            private String memberTags;
            private String nickName;
            private String phoneNum;
            private String remark;
            private String sex;
            private String status;
            private String storeId;
            private String storeName;
            private String userId;
            private String userName;
            private String userSerialId;
            private String weight;
            private List<?> businessInfos;
            private List<?> list;

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

            public String getBusinessName() {
                return businessName;
            }

            public void setBusinessName(String businessName) {
                this.businessName = businessName;
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

            public String getHeadPortrait() {
                return headPortrait;
            }

            public void setHeadPortrait(String headPortrait) {
                this.headPortrait = headPortrait;
            }

            public String getHeight() {
                return height;
            }

            public void setHeight(String height) {
                this.height = height;
            }

            public String getHobby() {
                return hobby;
            }

            public void setHobby(String hobby) {
                this.hobby = hobby;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMemberSerialId() {
                return memberSerialId;
            }

            public void setMemberSerialId(String memberSerialId) {
                this.memberSerialId = memberSerialId;
            }

            public String getMemberTags() {
                return memberTags;
            }

            public void setMemberTags(String memberTags) {
                this.memberTags = memberTags;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
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

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getStoreId() {
                return storeId;
            }

            public void setStoreId(String storeId) {
                this.storeId = storeId;
            }

            public String getStoreName() {
                return storeName;
            }

            public void setStoreName(String storeName) {
                this.storeName = storeName;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getUserSerialId() {
                return userSerialId;
            }

            public void setUserSerialId(String userSerialId) {
                this.userSerialId = userSerialId;
            }

            public String getWeight() {
                return weight;
            }

            public void setWeight(String weight) {
                this.weight = weight;
            }

            public List<?> getBusinessInfos() {
                return businessInfos;
            }

            public void setBusinessInfos(List<?> businessInfos) {
                this.businessInfos = businessInfos;
            }

            public List<?> getList() {
                return list;
            }

            public void setList(List<?> list) {
                this.list = list;
            }

            public LoginClientUserInfoBean() {
            }

            @Override
            public String toString() {
                return "LoginClientUserInfoBean{" +
                        "balance='" + balance + '\'' +
                        ", birthday='" + birthday + '\'' +
                        ", businessId='" + businessId + '\'' +
                        ", businessName='" + businessName + '\'' +
                        ", createTime='" + createTime + '\'' +
                        ", creator='" + creator + '\'' +
                        ", email='" + email + '\'' +
                        ", headPortrait='" + headPortrait + '\'' +
                        ", height='" + height + '\'' +
                        ", hobby='" + hobby + '\'' +
                        ", id='" + id + '\'' +
                        ", memberSerialId='" + memberSerialId + '\'' +
                        ", memberTags='" + memberTags + '\'' +
                        ", nickName='" + nickName + '\'' +
                        ", phoneNum='" + phoneNum + '\'' +
                        ", remark='" + remark + '\'' +
                        ", sex='" + sex + '\'' +
                        ", status='" + status + '\'' +
                        ", storeId='" + storeId + '\'' +
                        ", storeName='" + storeName + '\'' +
                        ", userId='" + userId + '\'' +
                        ", userName='" + userName + '\'' +
                        ", userSerialId='" + userSerialId + '\'' +
                        ", weight='" + weight + '\'' +
                        ", businessInfos=" + businessInfos +
                        ", list=" + list +
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

    @Override
    public String toString() {
        return "UserBean{" +
                "datas=" + datas +
                ", result=" + result +
                '}';
    }
}
