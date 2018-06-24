package com.dod.sport.domain.po.Course;

import com.dod.sport.domain.po.Member.MembercardRelation;

import java.io.Serializable;
import java.util.List;

/**
 * Created by defi on 2017-10-11.
 * 约课课程信息展示po
 */
public class CourseOrderInfo implements Serializable {

    private static final long serialVersionUID = 8975526448796141361L;

    private String coursesignId;    //签到表id
    private String courseplanId;    //排课id
    private String employeeName;    //老师名称
    private String classRoomName;   //教室
    private String classDateTime;   //授课时间
    private String courseName;      //课程名称
    private String remark;          //课程备注
    private String orderPersons;    //已约人数
    private String lowPersons;      //最少开课人数
    private String orderStatus;     //预约状态
    private String cardrelationId;  //约课会员卡id
    private String membcardName;    //会员卡名称
    private String courseOrderId;   //课程预约id
    private List<MembercardRelation> memberCardList;  //可预约会员卡列表
    private String isExperience;    //可否申请体验课

    public String getCoursesignId() {
        return coursesignId;
    }

    public void setCoursesignId(String coursesignId) {
        this.coursesignId = coursesignId;
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

    public String getClassRoomName() {
        return classRoomName;
    }

    public void setClassRoomName(String classRoomName) {
        this.classRoomName = classRoomName;
    }

    public String getClassDateTime() {
        return classDateTime;
    }

    public void setClassDateTime(String classDateTime) {
        this.classDateTime = classDateTime;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrderPersons() {
        return orderPersons;
    }

    public void setOrderPersons(String orderPersons) {
        this.orderPersons = orderPersons;
    }

    public String getLowPersons() {
        return lowPersons;
    }

    public void setLowPersons(String lowPersons) {
        this.lowPersons = lowPersons;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCardrelationId() {
        return cardrelationId;
    }

    public void setCardrelationId(String cardrelationId) {
        this.cardrelationId = cardrelationId;
    }

    public List<MembercardRelation> getMemberCardList() {
        return memberCardList;
    }

    public String getCourseOrderId() {
        return courseOrderId;
    }

    public void setCourseOrderId(String courseOrderId) {
        this.courseOrderId = courseOrderId;
    }

    public void setMemberCardList(List<MembercardRelation> memberCardList) {
        this.memberCardList = memberCardList;
    }

    public String getIsExperience() {
        return isExperience;
    }

    public void setIsExperience(String isExperience) {
        this.isExperience = isExperience;
    }

    public String getMembcardName() {
        return membcardName;
    }

    public void setMembcardName(String membcardName) {
        this.membcardName = membcardName;
    }
}
