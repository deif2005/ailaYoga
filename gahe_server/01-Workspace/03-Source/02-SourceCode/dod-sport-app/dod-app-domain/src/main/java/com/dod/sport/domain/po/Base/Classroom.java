package com.dod.sport.domain.po.Base;

import java.io.Serializable;

/**
 * 教室实体类
 * Created by Administrator on 2017/9/28.
 */
public class Classroom implements Serializable {
    private static final long serialVersionUID = -5616386941634682627L;
    private String id;                //
    private String classroomSerialId;
    private String classroomName;     //教室名称
    private String storeId;           //门店id
    private String storeName;
    private String remark;             //
    private String status;
    private String creator;            //
    private String createTime;        //

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassroomSerialId() {
        return classroomSerialId;
    }

    public void setClassroomSerialId(String classroomSerialId) {
        this.classroomSerialId = classroomSerialId;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
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
