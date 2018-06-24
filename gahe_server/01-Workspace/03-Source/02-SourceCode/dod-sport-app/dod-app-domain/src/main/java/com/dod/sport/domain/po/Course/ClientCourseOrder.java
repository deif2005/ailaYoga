package com.dod.sport.domain.po.Course;

import java.io.Serializable;

/**
 * Created by defi on 2017-10-09.
 * 客户端课程预约列表pojo
 */
public class ClientCourseOrder implements Serializable {

    private static final long serialVersionUID = 3140303329002851389L;

    private String id;              //签到表id
    private String courseplanId;    //排课计划id
    private String employeeName;    //老师名称
    private String courseId;        //课程id
    private String courseName;      //课程名称
    private String iconPath;        //课图路径
    private String duration;        //课程时长
    private String classroomName;   //教室名称
    private String orderStatus;     //预约状态:1已预约，2已签到，3爽约，4取消
    private String classDatetime;   //开课时间
    private String permitPersons;   //可预约人数
    private String courseOrderId;   //课程预约id
    private String cardrelationId;  //会员卡关系id

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

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
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

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getClassDatetime() {
        return classDatetime;
    }

    public void setClassDatetime(String classDatetime) {
        this.classDatetime = classDatetime;
    }

    public String getPermitPersons() {
        return permitPersons;
    }

    public void setPermitPersons(String permitPersons) {
        this.permitPersons = permitPersons;
    }

    public String getCourseOrderId() {
        return courseOrderId;
    }

    public void setCourseOrderId(String courseOrderId) {
        this.courseOrderId = courseOrderId;
    }

    public String getCardrelationId() {
        return cardrelationId;
    }

    public void setCardrelationId(String cardrelationId) {
        this.cardrelationId = cardrelationId;
    }
}
