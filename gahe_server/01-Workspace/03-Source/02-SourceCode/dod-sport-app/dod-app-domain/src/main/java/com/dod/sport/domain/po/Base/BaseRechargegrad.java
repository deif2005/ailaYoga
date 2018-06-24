package com.dod.sport.domain.po.Base;

import java.io.Serializable;
import java.util.Date;

/**
 *充值阶梯表
 * Created by Administrator on 2017/8/25.
 */
public class BaseRechargegrad implements Serializable {

    private static final long serialVersionUID = 7133043260328057475L;
    private String id;
    private String membcardId;     //会员卡id
    private String times;             //次数
    private String months;            //天数
    private String nominalAmount;  //面值金额
    private String enabled;         //状态:1.启用,2.停止
    private String creator;        //创建人id
    private String createTime;     //创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMembcardId() {
        return membcardId;
    }

    public void setMembcardId(String membcardId) {
        this.membcardId = membcardId;
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

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
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
