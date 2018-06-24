package com.dod.sport.domain.po.Member;

import java.io.Serializable;

/**
 * 充值记录表
 * Created by Administrator on 2017/8/29.
 */
public class MembercardRechargeRecord implements Serializable {

    private static final long serialVersionUID = -1205960255270325517L;

    private String id;
    private String rechargeSerialId;        //充值编号
    private String membercardRelationId;    //会员卡关系表id
    private String times;                   //次数
    private String months;                  //月数
    private String nominalAmount;           //面值金额
    private String storeId;                 //门店id
    private String status;                  //状态:1:未完成;2:已完成:3:删除/其他
    private String rechargeTime;            //充值时间
    private String createTime;               //创建时间
    private String remark;                  //备注
    private String creator;                 //创建人

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRechargeSerialId() {
        return rechargeSerialId;
    }

    public void setRechargeSerialId(String rechargeSerialId) {
        this.rechargeSerialId = rechargeSerialId;
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

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(String rechargeTime) {
        this.rechargeTime = rechargeTime;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
