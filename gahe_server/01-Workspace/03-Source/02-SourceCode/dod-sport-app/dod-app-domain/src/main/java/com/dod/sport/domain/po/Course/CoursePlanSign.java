package com.dod.sport.domain.po.Course;

import java.io.Serializable;
import java.security.PrivateKey;

/**
 * Created by defi on 2017-09-11.
 * 排课-课程签到关系pojo
 */
public class CoursePlanSign implements Serializable {
    private static final long serialVersionUID = 4352148942553927877L;

    private String id;
    private String courseplanId; //排课id
    private String employeeId;   //教师编号
    private String courseId;     //课程编号
    private String classroomId;  //教室编号
//    private String beginTime;    //开始时间
//    private String endTime;      //结束时间
    private String classTime;    //时间段 由开始时间和结束时间组合
    private String persons;      //预约or消费人数
    private String signStatus;   //签到状态：1未签到，2店长签到，3老师签到
    private String courseName;   //课程名称
    private String classroomName;  //教室名称
    private String employeeName;  //教师名称

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

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getClassTime() {
        return classTime;
    }

    public void setClassTime(String classTime) {
        this.classTime = classTime;
    }

    public String getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(String classroomId) {
        this.classroomId = classroomId;
    }

    public String getPersons() {
        return persons;
    }

    public void setPersons(String persons) {
        this.persons = persons;
    }

    public String getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(String signStatus) {
        this.signStatus = signStatus;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}
