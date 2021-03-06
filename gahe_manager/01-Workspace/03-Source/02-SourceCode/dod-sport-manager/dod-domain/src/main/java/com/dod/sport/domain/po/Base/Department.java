package com.dod.sport.domain.po.Base;

import java.io.Serializable;

/**
 * Created by defi on 2017-08-14.
 * 部门信息pojo
 */
public class Department implements Serializable{

    private static final long serialVersionUID = -5245761217947086121L;
    private String id;
    private String platformDepId;       //平台部门id
    private String depSerialId;         //部门编号
    private String depName;             //部门名称
    private String businessId;          //商家id
    private String status;              //状态：1正常，2删除，3停用或其它
    private String creator;             //创建人
    private String createTime;          //创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlatformDepId() {
        return platformDepId;
    }

    public void setPlatformDepId(String platformDepId) {
        this.platformDepId = platformDepId;
    }

    public String getDepSerialId() {
        return depSerialId;
    }

    public void setDepSerialId(String depSerialId) {
        this.depSerialId = depSerialId;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
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
