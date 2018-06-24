package com.dod.sport.domain.po.Base;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/23.
 * 商家员工pojo
 */
public class EmployeeInfo implements Serializable {
    private static final long serialVersionUID = 8307176656284369203L;

    private String id;            //员工id
    private String baseEmployeeSerialId; //
    private String employeeName;  //员工名称
    private String sex;           //员工性别
    private String birthday;      //生日
    private String phoneNum;      //电话
    private String password;      //密码
    private String idCard;        //身份证
    private String empHead;       //头像
    private String registerType;  //1未注册,已注册
    private String status;
    private String createTime;    //创建时间

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBaseEmployeeSerialId() {
        return baseEmployeeSerialId;
    }

    public void setBaseEmployeeSerialId(String baseEmployeeSerialId) {
        this.baseEmployeeSerialId = baseEmployeeSerialId;
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

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getEmpHead() {
        return empHead;
    }

    public void setEmpHead(String empHead) {
        this.empHead = empHead;
    }

    public String getRegisterType() {
        return registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
