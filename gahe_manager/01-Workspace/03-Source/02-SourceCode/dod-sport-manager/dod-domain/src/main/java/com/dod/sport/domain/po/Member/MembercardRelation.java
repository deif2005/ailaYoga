package com.dod.sport.domain.po.Member;

/**
 * 会员卡关系表
 * Created by Administrator on 2017/8/25.
 */
public class MembercardRelation {
    private String id;
    private String opencardSerialId; //开卡流水号
    private String memberId;         //会员id
    private String userName;         //会员名称
    private String membcardId;       //会员卡id
    private String membcardName;     //会员卡名字
    private String cardtypeName;     //会员卡类型名
    private String membcardType;     //会员卡类型:1:次卡;2:期限卡
    private String validityType;     //是否开启有效期:1:是;2:否
    private String validityTime;     //有效期时间
    private String cardStatus;       //状态:1.正常,2.停卡,3.删除,4.请假
    private String specactTime;      //指定激活时间
    private String actStatus;        //1:未激活;2已激活
    private String actTime;          //激活时间
    private String times;            //次卡次数
    private String giveTimes;        //赠送次数/天数
    private String balance;          //余额
    private String flagType;         //1:未换卡;2:已换卡
    private String storeId;          //门店id
    private String remark;           //备注
    private String creator;          //创建人
    private String createTime;       //创建时间
    private String phoneNum;         //会员电话
    private String days;            //剩余天数
    private String rownum;          //序号
    private String price;           //单价

    public String getActStatus() {
        return actStatus;
    }

    public void setActStatus(String actStatus) {
        this.actStatus = actStatus;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFlagType() {
        return flagType;
    }

    public void setFlagType(String flagType) {
        this.flagType = flagType;
    }

    public String getRownum() {
        return rownum;
    }

    public void setRownum(String rownum) {
        this.rownum = rownum;
    }

    public String getMembcardType() {
        return membcardType;
    }

    public void setMembcardType(String membcardType) {
        this.membcardType = membcardType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getOpencardSerialId() {
        return opencardSerialId;
    }

    public void setOpencardSerialId(String opencardSerialId) {
        this.opencardSerialId = opencardSerialId;
    }

    public String getValidityType() {
        return validityType;
    }
    public void setValidityType(String validityType) {
        this.validityType = validityType;
    }
    public String getMembcardName() {
        return membcardName;
    }

    public void setMembcardName(String membcardName) {
        this.membcardName = membcardName;
    }

    public String getCardtypeName() {
        return cardtypeName;
    }

    public void setCardtypeName(String cardtypeName) {
        this.cardtypeName = cardtypeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMembcardId() {
        return membcardId;
    }

    public void setMembcardId(String membcardId) {
        this.membcardId = membcardId;
    }

    public String getValidityTime() {
        return validityTime;
    }

    public void setValidityTime(String validityTime) {
        this.validityTime = validityTime;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getSpecactTime() {
        return specactTime;
    }

    public void setSpecactTime(String specactTime) {
        this.specactTime = specactTime;
    }

    public String getActTime() {
        return actTime;
    }

    public void setActTime(String actTime) {
        this.actTime = actTime;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getGiveTimes() {
        return giveTimes;
    }

    public void setGiveTimes(String giveTimes) {
        this.giveTimes = giveTimes;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}
