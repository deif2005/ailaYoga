package com.dod.sport.domain.po.Member;

import java.io.Serializable;

/**
 * Created by defi on 2017-08-21.
 * 会员卡pojo
 */
public class MemberCard implements Serializable {
    private static final long serialVersionUID = -5207797643204278292L;
    private String id;
    private String businessId;            //商家id
    private String membcardSerialId;      //会员卡编号
    private String membcardName;          //会员卡名称
    private String membcardType;          //会员卡类型：1期限卡，2次卡
    private String membcardTypeName;     //会员卡名称
    private String status;              //数据状态：1正常，2删除，3停用或其它
    private String remark;
    private String creator;
    private String createTime;
    private String sumcard;        //持卡人数

    public String getMembcardType() {
        return membcardType;
    }

    public void setMembcardType(String membcardType) {
        this.membcardType = membcardType;
    }

    public String getSumcard() {
        return sumcard;
    }

    public void setSumcard(String sumcard) {
        this.sumcard = sumcard;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getMembcardSerialId() {
        return membcardSerialId;
    }

    public void setMembcardSerialId(String membcardSerialId) {
        this.membcardSerialId = membcardSerialId;
    }

    public String getMembcardName() {
        return membcardName;
    }

    public void setMembcardName(String membcardName) {
        this.membcardName = membcardName;
    }

    public String getMembcardTypeName() {
        return membcardTypeName;
    }

    public void setMembcardTypeName(String membcardTypeName) {
        this.membcardTypeName = membcardTypeName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
