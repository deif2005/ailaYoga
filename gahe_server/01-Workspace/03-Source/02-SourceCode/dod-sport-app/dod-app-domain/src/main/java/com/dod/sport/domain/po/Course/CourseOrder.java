package com.dod.sport.domain.po.Course;

import java.io.Serializable;

/**
 * Created by defi on 2017-09-09.
 * 约课人员信息pojo
 */
public class CourseOrder implements Serializable {

    private static final long serialVersionUID = 9194225360728286057L;

    private String id;
    private String courseplanId;      //排课id
    private String coursesignId;      //签到id 课程的唯一标识代替原来的courseplanId
    private String courseId;          //课程id
    private String memberId;          //会员id
    private String sex;               //会员性别
    private String cardrelationId;    //会员卡会员关系id
    private String orderTime;         //预订时间
    private String orderStatus;       //预约状态:1已预约，2已签到，3爽约，4取消
    private String membcardName;      //会员卡名称
    private String classDate;         //课程日期
    private String classTime;         //用于私课预约时间段判断
    private String remark;
    private String employeeId;        //老师id用于前端判断权限
    private String userName;         //会员名称
    private String nickName;         //会员昵称
    private String headPortrait;     //会员头像


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

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

    public String getCoursesignId() {
        return coursesignId;
    }

    public void setCoursesignId(String coursesignId) {
        this.coursesignId = coursesignId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getCardrelationId() {
        return cardrelationId;
    }

    public void setCardrelationId(String cardrelationId) {
        this.cardrelationId = cardrelationId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getMembcardName() {
        return membcardName;
    }

    public void setMembcardName(String membcardName) {
        this.membcardName = membcardName;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }
}
