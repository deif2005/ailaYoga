package com.dodsport.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/20.
 *
 * 平台用户的个人信息
 */

public class ClientUserBean implements Serializable{


    /**
     * datas : {"clientUser":{"balance":"0.00","birthday":"2017-10-25","createTime":"2017-10-10 14:14:53.0","email":"","headPortrait":"","height":"","hobby":"","id":"c65fb63d-bf6b-4492-86ff-e4b00df7c59f","nickName":"倍数","password":"","phoneNum":"15100000008","sex":"1","userName":"了了了了了了了了","userSerialId":"000003","weight":""}}
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
         * clientUser : {"balance":"0.00","birthday":"2017-10-25","createTime":"2017-10-10 14:14:53.0","email":"","headPortrait":"","height":"","hobby":"","id":"c65fb63d-bf6b-4492-86ff-e4b00df7c59f","nickName":"倍数","password":"","phoneNum":"15100000008","sex":"1","userName":"了了了了了了了了","userSerialId":"000003","weight":""}
         */

        private ClientUser clientUser;

        public ClientUser getClientUser() {
            return clientUser;
        }

        public void setClientUser(ClientUser clientUser) {
            this.clientUser = clientUser;
        }

        public DatasBean() {
        }

        public static class ClientUser implements Serializable{
            /**
             * balance : 0.00
             * birthday : 2017-10-25
             * createTime : 2017-10-10 14:14:53.0
             * email :
             * headPortrait :
             * height :
             * hobby :
             * id : c65fb63d-bf6b-4492-86ff-e4b00df7c59f
             * nickName : 倍数
             * password :
             * phoneNum : 15100000008
             * sex : 1
             * userName : 了了了了了了了了
             * userSerialId : 000003
             * weight :
             */

            private String balance;
            private String birthday;
            private String createTime;
            private String email;
            private String headPortrait;
            private String height;
            private String hobby;
            private String id;
            private String nickName;
            private String password;
            private String phoneNum;
            private String sex;
            private String userName;
            private String userSerialId;
            private String weight;

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

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
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

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getPhoneNum() {
                return phoneNum;
            }

            public void setPhoneNum(String phoneNum) {
                this.phoneNum = phoneNum;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
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

            public ClientUser() {
            }

            @Override
            public String toString() {
                return "ClientUser{" +
                        "balance='" + balance + '\'' +
                        ", birthday='" + birthday + '\'' +
                        ", createTime='" + createTime + '\'' +
                        ", email='" + email + '\'' +
                        ", headPortrait='" + headPortrait + '\'' +
                        ", height='" + height + '\'' +
                        ", hobby='" + hobby + '\'' +
                        ", id='" + id + '\'' +
                        ", nickName='" + nickName + '\'' +
                        ", password='" + password + '\'' +
                        ", phoneNum='" + phoneNum + '\'' +
                        ", sex='" + sex + '\'' +
                        ", userName='" + userName + '\'' +
                        ", userSerialId='" + userSerialId + '\'' +
                        ", weight='" + weight + '\'' +
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
