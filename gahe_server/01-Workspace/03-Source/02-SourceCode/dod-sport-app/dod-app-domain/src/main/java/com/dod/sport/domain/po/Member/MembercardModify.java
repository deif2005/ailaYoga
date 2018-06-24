package com.dod.sport.domain.po.Member;

import java.io.Serializable;

/**
 * 会员卡变更记录
 * Created by Administrator on 2017/8/29.
 */
public class MembercardModify implements Serializable {

    private static final long serialVersionUID = 5580086131862619937L;
    private String id;
    private String modifySerialId;        //变更编号
    private String modifyType;            //变更类型:1.过户,2.停卡,3.换卡,4,赠送,5校准,6.启用,7.开卡,8.删除,9请假,10充值
    private String memberId;              //会员id
    private String tarmemberId;           //目标会员id
    private String membercardRelationId;  //会员卡关系表id
    private String times;                   //次数
    private String months;                  //月数
    private String nominalAmount;           //面值金额
    private String oldcardId;             //旧卡id
    private String newcardId;             //新卡id
    private String giveTimes;             //赠送次数/天数
    private String validityType;          //次卡是否开启有效期:1:是;2:否
    private String newTimes;              //校准后的次数
    private String oldTimes;              //校准前的次数
    private String residueTimes;         //剩余次数/天数
    private String discountPrice;         //折算价值(换卡折算)
    private String priceSpread;           //差价(换卡补差价)
    private String rechargeId;            //充值id(换卡中选择充值阶梯)
    private String newValidityTime;       //校准后有效期时间
    private String oldValidityTime;          //校准前有效期时间
    private String storeId;               //门店id
    private String modifyTime;            //变更时间
    private String remark;                //备注
    private String creator;
    private String status;                //1:未完成;2:已完成:3:删除/其他
    private String createTime;            //创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModifySerialId() {
        return modifySerialId;
    }

    public void setModifySerialId(String modifySerialId) {
        this.modifySerialId = modifySerialId;
    }

    public String getModifyType() {
        return modifyType;
    }

    public void setModifyType(String modifyType) {
        this.modifyType = modifyType;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getTarmemberId() {
        return tarmemberId;
    }

    public void setTarmemberId(String tarmemberId) {
        this.tarmemberId = tarmemberId;
    }

    public String getMembercardRelationId() {
        return membercardRelationId;
    }

    public void setMembercardRelationId(String membercardRelationId) {
        this.membercardRelationId = membercardRelationId;
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

    public String getOldcardId() {
        return oldcardId;
    }

    public void setOldcardId(String oldcardId) {
        this.oldcardId = oldcardId;
    }

    public String getNewcardId() {
        return newcardId;
    }

    public void setNewcardId(String newcardId) {
        this.newcardId = newcardId;
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

    public String getRechargeId() {
        return rechargeId;
    }

    public void setRechargeId(String rechargeId) {
        this.rechargeId = rechargeId;
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

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
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
}
