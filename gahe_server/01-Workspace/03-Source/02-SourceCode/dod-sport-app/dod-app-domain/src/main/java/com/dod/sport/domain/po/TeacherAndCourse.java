package com.dod.sport.domain.po;

import java.io.Serializable;

/**
 * Created by defi on 2017-09-25.
 * 老师-课程关联pojo
 */
public class TeacherAndCourse implements Serializable {

    private static final long serialVersionUID = 5566908526939934265L;

    private String id;
    private String courseId;      //课程id
    private String employeeId;    //会员卡id
    private String employeeName;  //会员卡名称
    private String courseName;    //课程名称
    private String courseMeans;   //授课类型
    private String enable;        //是否启用1未启用，2启用

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
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

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseMeans() {
        return courseMeans;
    }

    public void setCourseMeans(String courseMeans) {
        this.courseMeans = courseMeans;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }
}
