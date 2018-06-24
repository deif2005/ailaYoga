package com.dod.sport.domain.po.Course;

import java.io.Serializable;

/**
 * Created by defi on 2017-09-08.
 * 课程签到pojo
 */
public class CourseSign implements Serializable{

    private static final long serialVersionUID = -108002904482653743L;

    private String id;
    private String courseplanId;   //排课计划id
    private String employeeId;     //教师编号
    private String orderPersons;   //预约人数
    private String signStatus;     //签到状态：1未签到，2店长签到，3老师签到
    private String signTime1;      //店长签到时间
    private String signTime2;      //老师签到时间
    private String signPersons;    //签到人数
    private String cancelPersons;  //取消人数
    private String nosignPersons;  //未签到人数
    private String courseName;     //课程名称
    private String employeeName;   //排课老师
    private String classroomName;  //排课教室
    private String classStatus;    //课程状态：1未开课，2已开课，3已取消'

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseplanId() {
        return courseplanId;
    }

    public void setCourseplanId(String courseplanId) {
        this.courseplanId = courseplanId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getOrderPersons() {
        return orderPersons;
    }

    public void setOrderPersons(String orderPersons) {
        this.orderPersons = orderPersons;
    }

    public String getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(String signStatus) {
        this.signStatus = signStatus;
    }

    public String getSignTime1() {
        return signTime1;
    }

    public void setSignTime1(String signTime1) {
        this.signTime1 = signTime1;
    }

    public String getSignTime2() {
        return signTime2;
    }

    public void setSignTime2(String signTime2) {
        this.signTime2 = signTime2;
    }

    public String getSignPersons() {
        return signPersons;
    }

    public void setSignPersons(String signPersons) {
        this.signPersons = signPersons;
    }

    public String getCancelPersons() {
        return cancelPersons;
    }

    public void setCancelPersons(String cancelPersons) {
        this.cancelPersons = cancelPersons;
    }

    public String getNosignPersons() {
        return nosignPersons;
    }

    public void setNosignPersons(String nosignPersons) {
        this.nosignPersons = nosignPersons;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public String getClassStatus() {
        return classStatus;
    }

    public void setClassStatus(String classStatus) {
        this.classStatus = classStatus;
    }
}