package com.dod.sport.domain.po;

import com.dod.sport.domain.po.Base.BaseBusinessInfo;
import com.dod.sport.domain.po.Base.EmpBusinessRelation;
import com.dod.sport.domain.po.Course.Course;
import com.dod.sport.domain.po.Member.MemberEvaluate;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/23.
 * 员工与商家业务返回pojo
 */
public class ResponseEmployee extends EmpBusinessRelation implements Serializable {
    private static final long serialVersionUID = -2671323149816966960L;
    private String employeeName;                //员工名称
    private String sex;                         //员工性别
    private String birthday;                    //员工生日
    private String phoneNum;                    //员工电话
    private String idCard;                      //身份证
    private String empHead;                     //员工头像
    private String registerType;                //1.未注册,2,已注册
    private String workDuration;                //工龄
    private String businessName;                //商家名称
    private String businessAddress;             //商家地址
    private String storeName;                   //门店名称
    private String storeAddress;                //门店地址
    private String lasttimes;                   //迟到次数
    private String leavetimes;                  //早退次数
    private String failurePunch;                //未打卡次数
    private String roleName;                    //角色名称
    private UserRole userRole;                  //角色内容
    private List<String> picPathList;            //教学照片绝对路径集合
    private List<BaseBusinessInfo> businessList;  //商家列表
    private List<Course>courseList;               //课程列表
    private String depName;                       //部门名称
    private String positionName;                  //职位名称

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getWorkDuration() {
        return workDuration;
    }

    public void setWorkDuration(String workDuration) {
        this.workDuration = workDuration;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmpHead() {
        return empHead;
    }

    public void setEmpHead(String empHead) {
        this.empHead = empHead;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getRegisterType() {
        return registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType;
    }


    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getLasttimes() {
        return lasttimes;
    }

    public void setLasttimes(String lasttimes) {
        this.lasttimes = lasttimes;
    }

    public String getLeavetimes() {
        return leavetimes;
    }

    public void setLeavetimes(String leavetimes) {
        this.leavetimes = leavetimes;
    }

    public String getFailurePunch() {
        return failurePunch;
    }

    public void setFailurePunch(String failurePunch) {
        this.failurePunch = failurePunch;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }


    public List<String> getPicPathList() {
        return picPathList;
    }

    public void setPicPathList(List<String> picPathList) {
        this.picPathList = picPathList;
    }

    public List<BaseBusinessInfo> getBusinessList() {
        return businessList;
    }

    public void setBusinessList(List<BaseBusinessInfo> businessList) {
        this.businessList = businessList;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

}
