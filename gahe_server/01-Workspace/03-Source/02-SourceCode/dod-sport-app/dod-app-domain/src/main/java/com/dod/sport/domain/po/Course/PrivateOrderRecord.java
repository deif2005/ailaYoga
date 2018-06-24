package com.dod.sport.domain.po.Course;

import java.io.Serializable;

/**
 * 获取会员已预约信息
 * Created by Administrator on 2017/10/14.
 */
public class PrivateOrderRecord implements Serializable {
    private static final long serialVersionUID = 7380392941513381469L;
    private String id;               //预约课程id
    private String iconPath;         //课程图片
    private String courseName;       //课程名称
    private String duration;         //课程时长
    private String coursesignId;     //课程签到表id
    private String classDate;        //开课日期
    private String classTime;        //开课时间
    private String orderStatus;      //预约状态
    private String employeeName;     //老师名称
    private String empHead;          //老师头像
    private String classDateTime;    //开课日期时间
    private String classroomName;    //教室名称
    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getClassDateTime() {
        return classDateTime;
    }

    public void setClassDateTime(String classDateTime) {
        this.classDateTime = classDateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCoursesignId() {
        return coursesignId;
    }

    public void setCoursesignId(String coursesignId) {
        this.coursesignId = coursesignId;
    }

    public String getClassDate() {
        return classDate;
    }

    public void setClassDate(String classDate) {
        this.classDate = classDate;
    }

    public String getClassTime() {
        return classTime;
    }

    public void setClassTime(String classTime) {
        this.classTime = classTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmpHead() {
        return empHead;
    }

    public void setEmpHead(String empHead) {
        this.empHead = empHead;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }
}
