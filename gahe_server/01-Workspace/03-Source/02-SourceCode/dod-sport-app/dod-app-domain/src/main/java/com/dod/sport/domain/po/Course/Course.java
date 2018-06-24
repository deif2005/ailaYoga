package com.dod.sport.domain.po.Course;

import java.io.Serializable;

/**
 * Created by defi on 2017-08-21.
 * 课程pojo
 */
public class Course implements Serializable {

    private static final long serialVersionUID = -7517197906394082992L;

    private String id;
    private String courseSerialId; //课程编号
    private String courseName; //课程名称
    private String businessId; //商家id
    private String duration; //时长：分钟
    private String courseTypeId;//课程分类：1会员课，2特色课，3小班课，4公开课
    private String courseTypeName; //课程分类名称
    private String courseMeans;//授课形式：1私教，2团课 3私教&团课
    private String courseStatus;//开启状态：1启用，2禁用
    private String isExperience;//是否支持体验课：1支持，2不支持
    private String iconPath;  //课程封面图
    private String remark;//备注
    private String status;//数据状态：1正常，2删除，3停用或其它
    private String creator;//创建者
    private String createTime;//创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseSerialId() {
        return courseSerialId;
    }

    public void setCourseSerialId(String courseSerialId) {
        this.courseSerialId = courseSerialId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCourseTypeId() {
        return courseTypeId;
    }

    public void setCourseTypeId(String courseTypeId) {
        this.courseTypeId = courseTypeId;
    }

    public String getCourseTypeName() {
        return courseTypeName;
    }

    public void setCourseTypeName(String courseTypeName) {
        this.courseTypeName = courseTypeName;
    }

    public String getCourseMeans() {
        return courseMeans;
    }

    public void setCourseMeans(String courseMeans) {
        this.courseMeans = courseMeans;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public String getIsExperience() {
        return isExperience;
    }

    public void setIsExperience(String isExperience) {
        this.isExperience = isExperience;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
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
