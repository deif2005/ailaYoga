package com.dod.sport.domain.po.Base;

import java.io.Serializable;

/**
 * Created by defi on 2017-08-23.
 * 职位pojo
 */
public class Position implements Serializable {
    private static final long serialVersionUID = 4946494964213099734L;

    private String id;
    private String platformPositionId; //品台职位id
    private String positionSerialId;  //职位编号',
    private String positionName; //职位名称',
    private String businessId;   //商家id
    private String status;        //数据状态：1正常，2删除，3停用或其它',
    private String creator;       //创建者',
    private String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlatformPositionId() {
        return platformPositionId;
    }

    public void setPlatformPositionId(String platformPositionId) {
        this.platformPositionId = platformPositionId;
    }

    public String getPositionSerialId() {
        return positionSerialId;
    }

    public void setPositionSerialId(String positionSerialId) {
        this.positionSerialId = positionSerialId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
