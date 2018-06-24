package com.dod.sport.domain.po.Base;

import java.io.Serializable;

/**
 * User
 * 用户类
 * Created by defi on 2017/7/22.
 */

public class ClientUser implements Serializable{
    //alt+enter键生成serialVersionUID
    private static final long serialVersionUID = 5006229427457141160L;

    private String id;
    private String userSerialId;          //用户编号
    private String userName;              //用户名称
    private String password;              //密码
    private String headPortrait;         //头像
    private String height;               //身高
    private String weight;               //体重
    private String birthday;             //生日
    private String sex;                  //性别:1男，2女，3未知
    private String nickName;            //昵称
    private String hobby;               //兴趣爱好
    private String phoneNum;            //电话
    private String balance;             //余额
    private String createTime;          //创建时间
    private String email;               //邮箱
    private String registerType;        //注册类型
    private String loginTime;           //登录时间

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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

    public String getUserSerialId() {
        return userSerialId;
    }

    public void setUserSerialId(String userSerialId) {
        this.userSerialId = userSerialId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
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

    public String getRegisterType() {
        return registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }
}
