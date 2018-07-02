package com.dodsport.model;

import java.io.Serializable;


/**
 * Created by Administrator on 2017/9/6.
 * 部门信息
 */

public class DepartmentsBean implements Serializable {

    /**
     * businessId : fbf416e6-3d6e-406a-9cde-16c6f3de8b01
     * createTime : 2017-09-05 16:41:52.0
     * creator : 43b117b0-7c15-4145-9ff9-e8141aea7073
     * depName : 技术创新部
     * depSerialId : 019
     * id : d0f8abbf-4860-4859-b013-2ce6965423ec
     * status : 1
     */

    private String businessId;
    private String createTime;
    private String creator;
    private String depName;
    private String depSerialId;
    private String id;
    private String status;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
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

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getDepSerialId() {
        return depSerialId;
    }

    public void setDepSerialId(String depSerialId) {
        this.depSerialId = depSerialId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DepartmentsBean() {
    }

    @Override
    public String toString() {
        return "DepartmentsBean{" +
                "businessId='" + businessId + '\'' +
                ", createTime='" + createTime + '\'' +
                ", creator='" + creator + '\'' +
                ", depName='" + depName + '\'' +
                ", depSerialId='" + depSerialId + '\'' +
                ", id='" + id + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
