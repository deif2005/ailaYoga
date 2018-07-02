package com.dodsport.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Administrator
 * @date 2017/10/25
 *
 * 门店中的会员列表
 */

public class BaseMemberListBean implements Serializable{

    /**
     * datas : {"responseMemberList":[{"balance":"0.00","birthday":"2017-10-04","businessId":"80a2aa90-2205-4453-8760-b395fb48746a","businessInfos":[],"businessName":"huz1","createTime":"2017-10-10 14:14:53.0","creator":"8ba07a96-d3bb-4e16-b58b-90c6e239b57d","email":"","headPortrait":"","height":"","hobby":"","id":"61ffe525-c65b-42f6-ac1e-ca2293b6b8d1","list":[],"memberSerialId":"021","memberTags":"5","nickName":"","phoneNum":"15100000008","remark":"","sex":"2","status":"1","storeId":"5565f926-eb45-47c5-a297-ad2101533b19","storeName":"最好的门店2","userId":"c65fb63d-bf6b-4492-86ff-e4b00df7c59f","userName":"了了了了了了了了","userSerialId":"000003","weight":""},{"balance":"0.00","birthday":"2017-10-04","businessId":"80a2aa90-2205-4453-8760-b395fb48746a","businessInfos":[],"businessName":"huz1","createTime":"2017-10-30 15:29:32.0","creator":"673eb481-02e0-418b-bd0a-9b780e40ce77","email":"","headPortrait":"","height":"","hobby":"","id":"2802265d-5f07-4273-b246-46c5095ca2a3","list":[],"memberSerialId":"030","memberTags":"5","nickName":"","phoneNum":"18316753438","remark":"","sex":"1","status":"1","storeId":"5565f926-eb45-47c5-a297-ad2101533b19","storeName":"最好的门店2","userId":"9b52d4a6-b387-48da-ba81-f58dc43c4e58","userName":"老朱","userSerialId":"000005","weight":""},{"balance":"0.00","birthday":"2017-11-10","businessId":"80a2aa90-2205-4453-8760-b395fb48746a","businessInfos":[],"businessName":"huz1","createTime":"2017-10-30 16:25:44.0","creator":"bdc13c70-de9b-47e0-b59d-ea90e8926ee9","email":"","headPortrait":"","height":"170","hobby":"","id":"fce31641-5abb-4925-8aec-bd02ed120c8c","list":[],"memberSerialId":"032","memberTags":"5","nickName":"","phoneNum":"15180180483","remark":"","sex":"1","status":"1","storeId":"5565f926-eb45-47c5-a297-ad2101533b19","storeName":"最好的门店2","userId":"1566fc2f-83ca-461e-b4bd-77fc214fe163","userName":"","userSerialId":"000006","weight":"46"},{"balance":"0.00","birthday":"","businessId":"80a2aa90-2205-4453-8760-b395fb48746a","businessInfos":[],"businessName":"huz1","createTime":"2017-10-30 16:28:58.0","creator":"bdc13c70-de9b-47e0-b59d-ea90e8926ee9","email":"","headPortrait":"","height":"","hobby":"","id":"9eaef82d-2dca-45d2-be35-54a5cec0a85e","list":[],"memberSerialId":"034","memberTags":"5","nickName":"","phoneNum":"15180180484","remark":"","sex":"2","status":"1","storeId":"5565f926-eb45-47c5-a297-ad2101533b19","storeName":"最好的门店2","userId":"d7eb3e6a-756c-42ec-aa0f-1660e5a7dc09","userName":"","userSerialId":"000007","weight":""},{"balance":"0.00","birthday":"2002-10-30","businessId":"80a2aa90-2205-4453-8760-b395fb48746a","businessInfos":[],"businessName":"huz1","createTime":"2017-10-30 16:32:25.0","creator":"bdc13c70-de9b-47e0-b59d-ea90e8926ee9","email":"","headPortrait":"","height":"","hobby":"","id":"fd893fb9-4a9d-49b0-aef1-9846a8cd36e8","list":[],"memberSerialId":"036","memberTags":"5","nickName":"","phoneNum":"15180180485","remark":"","sex":"1","status":"1","storeId":"5565f926-eb45-47c5-a297-ad2101533b19","storeName":"最好的门店2","userId":"72f7532a-b157-4b08-9dd1-c0ddd455e90b","userName":"234","userSerialId":"000008","weight":""},{"balance":"0.00","birthday":"","businessId":"80a2aa90-2205-4453-8760-b395fb48746a","businessInfos":[],"businessName":"huz1","createTime":"2017-11-06 17:47:44.0","creator":"2167d84e-d886-40ae-b290-555d22d63afa","email":"","headPortrait":"","height":"","hobby":"","id":"8584ee24-a23f-4788-b296-a48eb7b54a9b","list":[],"memberSerialId":"039","memberTags":"1","nickName":"","phoneNum":"15180149981","remark":"","sex":"1","status":"1","storeId":"5565f926-eb45-47c5-a297-ad2101533b19","storeName":"最好的门店2","userId":"02a3c02b-e574-4a7c-9ddf-9efebf14a533","userName":"","userSerialId":"000013","weight":""},{"balance":"0.00","birthday":"","businessId":"80a2aa90-2205-4453-8760-b395fb48746a","businessInfos":[],"businessName":"huz1","createTime":"2017-11-06 18:03:59.0","creator":"2167d84e-d886-40ae-b290-555d22d63afa","email":"","headPortrait":"","height":"","hobby":"","id":"76b05446-f1cc-4165-852d-29b0c4900272","list":[],"memberSerialId":"040","memberTags":"1","nickName":"","phoneNum":"15180149989","remark":"","sex":"1","status":"1","storeId":"5565f926-eb45-47c5-a297-ad2101533b19","storeName":"最好的门店2","userId":"b2631e63-5426-45be-b1c4-2e2d789f9812","userName":"","userSerialId":"000014","weight":""},{"balance":"0.00","birthday":"2017-11-06","businessId":"80a2aa90-2205-4453-8760-b395fb48746a","businessInfos":[],"businessName":"huz1","createTime":"2017-11-06 19:25:36.0","creator":"2167d84e-d886-40ae-b290-555d22d63afa","email":"","headPortrait":"","height":"","hobby":"","id":"d4c3c2e3-f634-43cf-92c6-b7a3978efb40","list":[],"memberSerialId":"041","memberTags":"1","nickName":"","phoneNum":"15180147889","remark":"","sex":"1","status":"1","storeId":"5565f926-eb45-47c5-a297-ad2101533b19","storeName":"最好的门店2","userId":"6ac8ed49-ac8f-45af-b88e-45d03c4c2c3a","userName":"","userSerialId":"000015","weight":""}]}
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
        private List<ResponseMemberList> responseMemberList;

        public List<ResponseMemberList> getResponseMemberList() {
            return responseMemberList;
        }

        public void setResponseMemberList(List<ResponseMemberList> responseMemberList) {
            this.responseMemberList = responseMemberList;
        }

        public DatasBean() {
        }


        public static class ResponseMemberList {
            /**
             * balance : 0.00
             * birthday : 2017-10-04
             * businessId : 80a2aa90-2205-4453-8760-b395fb48746a
             * businessInfos : []
             * businessName : huz1
             * createTime : 2017-10-10 14:14:53.0
             * creator : 8ba07a96-d3bb-4e16-b58b-90c6e239b57d
             * email :
             * headPortrait :
             * height :
             * hobby :
             * id : 61ffe525-c65b-42f6-ac1e-ca2293b6b8d1
             * list : []
             * memberSerialId : 021
             * memberTags : 5
             * nickName :
             * phoneNum : 15100000008
             * remark :
             * sex : 2
             * status : 1
             * storeId : 5565f926-eb45-47c5-a297-ad2101533b19
             * storeName : 最好的门店2
             * userId : c65fb63d-bf6b-4492-86ff-e4b00df7c59f
             * userName : 了了了了了了了了
             * userSerialId : 000003
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

            public ResponseMemberList() {
            }

            @Override
            public String toString() {
                return "ResponseMemberList{" +
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
}
