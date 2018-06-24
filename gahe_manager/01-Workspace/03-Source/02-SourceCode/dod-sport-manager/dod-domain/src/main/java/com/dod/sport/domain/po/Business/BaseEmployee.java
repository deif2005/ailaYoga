package com.dod.sport.domain.po.Business;

import com.dod.sport.domain.po.System.UserRole;

import java.util.Date;

/**
 * 员工基础数据信息
 * Created by Administrator on 2017/8/17.
 */
public class BaseEmployee {

    private String id;
    private String employeeSerialId;            //员工编号
    private String employeeName;                //员工姓名
    private int sex;                            //性别:1男，2女，3未知
    private Date birthday;                      //生日
    private String phoneNum;                    //电话
    private String  password;
    private String idCard;                      //身份证号
    private Integer coachExists;                //是否是教练:1.否;2.是
    private Integer jobTitle;                   //教练职称;1:初级教练;2:中级教练;3:高级教练
    private String userRoleId;                  //角色代码
    private int empStatus;                      //激活状态,1:未激活,2已激活
    private UserRole userRole;                  //角色内容
    private int status;                         //员工状态:1正常，2删除，3,停用或其它
    private int empType;                        //雇佣状态：1正式员工，2非正式员工，3解聘员工，4其它
    private int empRela;                        //聘用关系：1全职，2兼职
    private String depId;           //部门编号
    private String depName;         //部门名称
    private String workDuration;    //工龄
    private String empHead;         //员工头像
    private String empPicture;       //员工图片
    private String positionId;       //职位编号
    private String positionName;     //职位名称
    private String selfIntroduction; //自我介绍
    private String entryDate;        //入职日期
    private String leaveDate;        //离职日期
    private String creator;          //创建人
    private String createTime;       //创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeSerialId() {
        return employeeSerialId;
    }

    public void setEmployeeSerialId(String employeeSerialId) {
        this.employeeSerialId = employeeSerialId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Integer getCoachExists() {
        return coachExists;
    }

    public void setCoachExists(Integer coachExists) {
        this.coachExists = coachExists;
    }

    public Integer getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(Integer jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(String userRoleId) {
        this.userRoleId = userRoleId;
    }

    public int getEmpStatus() {
        return empStatus;
    }

    public void setEmpStatus(int empStatus) {
        this.empStatus = empStatus;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getEmpType() {
        return empType;
    }

    public void setEmpType(int empType) {
        this.empType = empType;
    }

    public int getEmpRela() {
        return empRela;
    }

    public void setEmpRela(int empRela) {
        this.empRela = empRela;
    }

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getWorkDuration() {
        return workDuration;
    }

    public void setWorkDuration(String workDuration) {
        this.workDuration = workDuration;
    }

    public String getEmpHead() {
        return empHead;
    }

    public void setEmpHead(String empHead) {
        this.empHead = empHead;
    }

    public String getEmpPicture() {
        return empPicture;
    }

    public void setEmpPicture(String empPicture) {
        this.empPicture = empPicture;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getSelfIntroduction() {
        return selfIntroduction;
    }

    public void setSelfIntroduction(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(String leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
