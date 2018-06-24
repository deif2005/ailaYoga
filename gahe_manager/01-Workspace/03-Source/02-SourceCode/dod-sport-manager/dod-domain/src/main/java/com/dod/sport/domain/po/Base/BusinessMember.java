package com.dod.sport.domain.po.Base;

import java.io.Serializable;

/**
 * 会员基本信息
 * Created by Administrator on 2017/8/25.
 */
public class BusinessMember extends ClientUser implements Serializable {

    private String memberId;
    private String memberSerialId; //会员编号
    private String businessId;     //商家id
    private String storeId;        //门店id
    private String creator;        //创建人
    private String createTime;     //创建时间
    private String memberTags;     //客户标签:1潜在客户;2:意向客户;3:目标客户;4:待签约客户;5:签约客户
    private String remark;         //备注
    private String status;         //1:正常;2:删除;3:停用或其他
    private String userId;         //用户id

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getMemberTags() {
        return memberTags;
    }

    public void setMemberTags(String memberTags) {
        this.memberTags = memberTags;
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

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getMemberSerialId() {
        return memberSerialId;
    }

    public void setMemberSerialId(String memberSerialId) {
        this.memberSerialId = memberSerialId;
    }
}
