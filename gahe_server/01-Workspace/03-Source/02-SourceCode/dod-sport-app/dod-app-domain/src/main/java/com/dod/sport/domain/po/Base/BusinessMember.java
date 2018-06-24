package com.dod.sport.domain.po.Base;

import com.dod.sport.domain.po.Member.MembercardRelation;

import java.io.Serializable;
import java.util.List;

/**
 * 会员基本信息
 * Created by Administrator on 2017/8/25.
 */
public class BusinessMember extends ClientUser implements Serializable {

    private static final long serialVersionUID = -823117190794914071L;
    private String id;
    private String memberSerialId; //会员编号
    private String businessId;     //商家id
    private String storeId;        //门店id
    private String creator;        //创建人
    private String createTime;     //创建时间
    private String memberTags;     //客户标签:1潜在客户;2:意向客户;3:目标客户;4:待签约客户;5:签约客户
    private String remark;         //备注
    private String status;         //1:正常;2:删除;3:停用或其他
    private String userId;         //用户id

    @Override
    public String getId() {
        return id;
    }

    @Override
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public String getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMemberTags() {
        return memberTags;
    }

    public void setMemberTags(String memberTags) {
        this.memberTags = memberTags;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
