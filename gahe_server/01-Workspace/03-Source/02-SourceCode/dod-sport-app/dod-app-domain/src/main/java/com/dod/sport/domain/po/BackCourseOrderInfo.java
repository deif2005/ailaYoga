package com.dod.sport.domain.po;

import com.dod.sport.domain.po.Course.CourseOrder;

import java.io.Serializable;

/**
 * 返回预约课程详情pojo
 * Created by Administrator on 2017/10/21.
 */
public class BackCourseOrderInfo extends CourseOrder implements Serializable {
    private static final long serialVersionUID = 1745913073532846166L;
    private String courseName;      //课程名称
    private String employeeName;    //老师名称
    private String classroomName;   //教室名称
    private String classDatetime;   //上课时间
    private String duration;        //上课时长(分钟)
    private String courseMeans;     //授课形式：1私教，2团课，3私教&团课
    private String storeName;       //分店名称
    private String lowPerson;       //开课最少人数
    private String orderedPerson;   //已预约人数

    public String getOrderedPerson() {
        return orderedPerson;
    }

    public void setOrderedPerson(String orderedPerson) {
        this.orderedPerson = orderedPerson;
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

    public String getCourseMeans() {
        return courseMeans;
    }

    public void setCourseMeans(String courseMeans) {
        this.courseMeans = courseMeans;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getLowPerson() {
        return lowPerson;
    }

    public void setLowPerson(String lowPerson) {
        this.lowPerson = lowPerson;
    }

}
