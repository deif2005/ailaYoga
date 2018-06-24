package com.dod.sport.domain.po;

import com.dod.sport.domain.po.Base.BusinessInfo;
import com.dod.sport.domain.po.Member.MembercardRelation;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/27.
 * 会员返回pojo
 *
 */
public class ResponseMember implements Serializable {
    private static final long serialVersionUID = -7814273030842125465L;

    private String id;                 //会员Id
    private String memberSerialId;     //会员编号
    private String businessId;         //商家id
    private String storeId;            //门店id
    private String memberTags;         //客户标签:1潜在客户;2:意向客户;3:目标客户;4:待签约客户;5:签约客户
    private String creator;            //会员创建人
    private String status;             //1:正常;2:删除;3:停用或其他
    private String createTime;         //创建时间
    private String remark;             //会员备注
    private String userId;             //用户id
    private String userSerialId;       //用户编号
    private String userName;           //用户名称(会员名称)
    private String headPortrait;       //头像
    private String height;             //身高
    private String weight;             //体重
    private String birthday;           //生日
    private String sex;                //性别:1男，2女，3未知
    private String nickName;           //昵称
    private String hobby;              //爱好
    private String phoneNum;           //电话
    private String balance;            //余额
    private String email;              //邮箱
    private String businessName;   //商家名称
    private String storeName;      //门店名称
    private List<MembercardRelation> list;
    private List<BusinessInfo> businessInfos;

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

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getMemberTags() {
        return memberTags;
    }

    public void setMemberTags(String memberTags) {
        this.memberTags = memberTags;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserSerialId() {
        return userSerialId;
    }

    public void setUserSerialId(String userSerialId) {
        this.userSerialId = userSerialId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public List<MembercardRelation> getList() {
        return list;
    }

    public void setList(List<MembercardRelation> list) {
        this.list = list;
    }

    public List<BusinessInfo> getBusinessInfos() {
        return businessInfos;
    }

    public void setBusinessInfos(List<BusinessInfo> businessInfos) {
        this.businessInfos = businessInfos;
    }
}
