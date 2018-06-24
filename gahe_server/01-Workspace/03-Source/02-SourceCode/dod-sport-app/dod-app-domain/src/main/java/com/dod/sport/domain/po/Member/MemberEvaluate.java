package com.dod.sport.domain.po.Member;

import java.io.Serializable;

/**
 * 会员评价实体类
 * Created by Administrator on 2017/9/26.
 */
public class MemberEvaluate implements Serializable {
    private static final long serialVersionUID = -1857567819258228611L;
    private String id;
    private String evaluateSerialId;       //会员评价编号
    private String memberId;               //会员id
    private String memberName;             //会员名称
    private String empHead;                //教练头像
    private String empId;                 //教练id
    private String empName;                 //教练名称
    private String courseId;               //课程id
    private String courseName;              //课程名称
    private String teachLevel;             //教学水平
    private String serviceLevel;           //服务态度
    private String environmentLevel;       //场馆环境评价
    private String evaluateStr;            //会员评价
    private String weekNum;                 //第几周记录
    private String avgTeachLevel;           //第几周教学水平平均数
    private String avgServiceLevel;          //第几服务态度平均数
    private String avgEnvironmentlevel;       //第几场馆环境评价平均数

    public String getEmpHead() {
        return empHead;
    }

    public void setEmpHead(String empHead) {
        this.empHead = empHead;
    }

    public String getEvaluateSerialId() {
        return evaluateSerialId;
    }

    public void setEvaluateSerialId(String evaluateSerialId) {
        this.evaluateSerialId = evaluateSerialId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
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

    public String getTeachLevel() {
        return teachLevel;
    }

    public void setTeachLevel(String teachLevel) {
        this.teachLevel = teachLevel;
    }

    public String getServiceLevel() {
        return serviceLevel;
    }

    public void setServiceLevel(String serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    public String getEnvironmentLevel() {
        return environmentLevel;
    }

    public void setEnvironmentLevel(String environmentLevel) {
        this.environmentLevel = environmentLevel;
    }

    public String getEvaluateStr() {
        return evaluateStr;
    }

    public void setEvaluateStr(String evaluateStr) {
        this.evaluateStr = evaluateStr;
    }

    public String getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(String weekNum) {
        this.weekNum = weekNum;
    }

    public String getAvgTeachLevel() {
        return avgTeachLevel;
    }

    public void setAvgTeachLevel(String avgTeachLevel) {
        this.avgTeachLevel = avgTeachLevel;
    }

    public String getAvgServiceLevel() {
        return avgServiceLevel;
    }

    public void setAvgServiceLevel(String avgServiceLevel) {
        this.avgServiceLevel = avgServiceLevel;
    }

    public String getAvgEnvironmentlevel() {
        return avgEnvironmentlevel;
    }

    public void setAvgEnvironmentlevel(String avgEnvironmentlevel) {
        this.avgEnvironmentlevel = avgEnvironmentlevel;
    }
}
