package com.dod.sport.domain.po.Base;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/23.
 * 员工与商家关系pojo
 */
public class EmpBusinessRelation implements Serializable {

    private static final long serialVersionUID = 2840229338128700868L;
    private String id;                  //员工关系id
    private String employeeSerialId;    //员工编号
    private String empId;               //员工id
    private String businessId;          //商家id
    private String positionId;          //职位id
    private String storeId;             //门店id
    private String depId;               //部门id
    private String empType;             //雇佣状态：1正式员工，2非正式员工，3解聘员工，4其它
    private String empRela;             //聘用关系：1全职，2兼职
    private String status;              //员工状态:1正常，2删除，3,停用或其它
    private String empPicture;          //教学图片
    private String isCoach;             //是否是教练:1.否;2.是
    private String jobTitle;            //教练职称;1:初级教练;2:中级教练;3:高级教练
    private String entryDate;           //入职日期
    private String leaveDate;           //离职日期
    private String creator;             //创建人
    private String selfIntroduction;    //自我介绍
    private String roleId;              //角色id
    private String roleName;            //角色名称
    private String createTime;          //创建时间

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

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public String getEmpType() {
        return empType;
    }

    public void setEmpType(String empType) {
        this.empType = empType;
    }

    public String getEmpRela() {
        return empRela;
    }

    public void setEmpRela(String empRela) {
        this.empRela = empRela;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmpPicture() {
        return empPicture;
    }

    public void setEmpPicture(String empPicture) {
        this.empPicture = empPicture;
    }

    public String getIsCoach() {
        return isCoach;
    }

    public void setIsCoach(String isCoach) {
        this.isCoach = isCoach;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
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

    public String getSelfIntroduction() {
        return selfIntroduction;
    }

    public void setSelfIntroduction(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
