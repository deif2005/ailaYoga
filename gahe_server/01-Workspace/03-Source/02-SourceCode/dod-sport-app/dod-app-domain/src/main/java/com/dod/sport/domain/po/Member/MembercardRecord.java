package com.dod.sport.domain.po.Member;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/23.
 */
public class MembercardRecord implements Serializable {

    private static final long serialVersionUID = -5272403043765343340L;
    private String tarmemberName;       //会员卡过户对象
    private String modifyTime;          //变更时间/预约时间
    private String employeeName;        //创建人/老师
    private String orderStatus;         //预约状态:1已预约，2已签到，3爽约，4取消
    private String courseName;          //课程名称
    private String modifyType;          //变更类型
    private String times;               //充值次数
    private String months;              //充值月数
    private String nominalAmount;       //充值金额
    private String giveTimes;           //赠送次数/天数
    private String validityType;        //次卡是否开启有效期时间
    private String newTimes;            //校准后的次数
    private String oldTimes;            //校准前的次数
    private String residueTimes;        //剩余次数/天数
    private String newValidityTime;     //校准后的有效期时间
    private String oldValidityTime;     //校准前的有效期时间
    private String discountPrice;       //会员卡折算价值
    private String priceSpread;         //换卡补的差价
    private String newcardName;         //新卡名称(换卡)
    private String oldcardName;         //旧卡名称(换卡)
    private String contentStr;          //内容
    private String membcardType;        //会员卡类型
    private String storeName;           //门店名称
    public String getTarmemberName() {
        return tarmemberName;
    }

    public void setTarmemberName(String tarmemberName) {
        this.tarmemberName = tarmemberName;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getModifyType() {
        return modifyType;
    }

    public void setModifyType(String modifyType) {
        this.modifyType = modifyType;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public String getNominalAmount() {
        return nominalAmount;
    }

    public void setNominalAmount(String nominalAmount) {
        this.nominalAmount = nominalAmount;
    }

    public String getGiveTimes() {
        return giveTimes;
    }

    public void setGiveTimes(String giveTimes) {
        this.giveTimes = giveTimes;
    }

    public String getValidityType() {
        return validityType;
    }

    public void setValidityType(String validityType) {
        this.validityType = validityType;
    }

    public String getNewTimes() {
        return newTimes;
    }

    public void setNewTimes(String newTimes) {
        this.newTimes = newTimes;
    }

    public String getOldTimes() {
        return oldTimes;
    }

    public void setOldTimes(String oldTimes) {
        this.oldTimes = oldTimes;
    }

    public String getResidueTimes() {
        return residueTimes;
    }

    public void setResidueTimes(String residueTimes) {
        this.residueTimes = residueTimes;
    }

    public String getNewValidityTime() {
        return newValidityTime;
    }

    public void setNewValidityTime(String newValidityTime) {
        this.newValidityTime = newValidityTime;
    }

    public String getOldValidityTime() {
        return oldValidityTime;
    }

    public void setOldValidityTime(String oldValidityTime) {
        this.oldValidityTime = oldValidityTime;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getPriceSpread() {
        return priceSpread;
    }

    public void setPriceSpread(String priceSpread) {
        this.priceSpread = priceSpread;
    }

    public String getNewcardName() {
        return newcardName;
    }

    public void setNewcardName(String newcardName) {
        this.newcardName = newcardName;
    }

    public String getOldcardName() {
        return oldcardName;
    }

    public void setOldcardName(String oldcardName) {
        this.oldcardName = oldcardName;
    }

    public String getContentStr() {
        return contentStr;
    }

    public void setContentStr(String contentStr) {
        this.contentStr = contentStr;
    }

    public String getMembcardType() {
        return membcardType;
    }

    public void setMembcardType(String membcardType) {
        this.membcardType = membcardType;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
