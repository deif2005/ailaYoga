package com.dod.sport.domain.po.Base;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/11.
 * 教练基础信息
 */
public class CoachInfo implements Serializable {

    private static final long serialVersionUID = -2050423910058380692L;
    private String id;                           //员工关系表id
    private String empId;                       //员工基础表id
    private String employeeName;                //员工姓名
    private Integer jobTitle;                   //教练职称;1:初级教练;2:中级教练;3:高级教练
    private String empHead;                     //员工头像
    private String selfIntroduction;            //自我介绍
    private List<String> picPathList;           //教学照片绝对路径集合

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getSelfIntroduction() {
        return selfIntroduction;
    }

    public void setSelfIntroduction(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Integer getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(Integer jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getEmpHead() {
        return empHead;
    }

    public void setEmpHead(String empHead) {
        this.empHead = empHead;
    }

    public List<String> getPicPathList() {
        return picPathList;
    }

    public void setPicPathList(List<String> picPathList) {
        this.picPathList = picPathList;
    }
}
