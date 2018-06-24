package com.dod.sport.domain.po.Base;

/**
 * Created by defi on 2017-08-22.
 * 课程类型pojo
 */
public class CourseType {
    private String id;
    private String coursetypeSerialId;  //课程类型编号
    private String courseTypeName;//课程类型名称
    private String businessId;
    private String status;
    private String creator;
    private String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoursetypeSerialId() {
        return coursetypeSerialId;
    }

    public void setCoursetypeSerialId(String coursetypeSerialId) {
        this.coursetypeSerialId = coursetypeSerialId;
    }

    public String getCourseTypeName() {
        return courseTypeName;
    }

    public void setCourseTypeName(String courseTypeName) {
        this.courseTypeName = courseTypeName;
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
