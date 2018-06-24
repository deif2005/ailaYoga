package com.dod.sport.domain.po.Course;

import java.io.Serializable;

/**
 * Created by defi on 2017-09-06.
 * 课程设置pojo
 */
public class CoursePlan implements Serializable {

    private static final long serialVersionUID = -3343607425048802432L;

    private String id;
    private String storeId;   //门店id
    private String storeName; //门店名称
    private String courseId;  //课程id
    private String courseName; //课程名称
    private String classroomId; //教室id
    private String classRoomName; //教室名称
    private String employeeId;   //教师id
    private String employeeName;  //教师名称
    private String lowPersons;   //最少上课人数
    private String upperPersons;  //最多上课人数
    private String classDatetime; //开课时间
    private String duration;      //时长（分钟）
    private String enable;         //是否禁用:1启用,2禁用
    private String isExperience;  //是否体验课:1否，2是
    private String classStatus;   //课程状态：1未开课，2已开课，3已取消'
    private String remark;         //备注
    private String creator;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(String classroomId) {
        this.classroomId = classroomId;
    }

    public String getClassRoomName() {
        return classRoomName;
    }

    public void setClassRoomName(String classRoomName) {
        this.classRoomName = classRoomName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getLowPersons() {
        return lowPersons;
    }

    public void setLowPersons(String lowPersons) {
        this.lowPersons = lowPersons;
    }

    public String getUpperPersons() {
        return upperPersons;
    }

    public void setUpperPersons(String upperPersons) {
        this.upperPersons = upperPersons;
    }

    public String getClassDatetime() {
        return classDatetime;
    }

    public void setClassDatetime(String classDatetime) {
        this.classDatetime = classDatetime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getIsExperience() {
        return isExperience;
    }

    public void setIsExperience(String isExperience) {
        this.isExperience = isExperience;
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

    public String getClassStatus() {
        return classStatus;
    }

    public void setClassStatus(String classStatus) {
        this.classStatus = classStatus;
    }
}