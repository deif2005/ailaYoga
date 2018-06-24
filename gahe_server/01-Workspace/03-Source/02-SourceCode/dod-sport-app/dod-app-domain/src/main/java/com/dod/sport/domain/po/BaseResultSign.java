package com.dod.sport.domain.po;

/**
 * 返回签到记录
 * Created by Administrator on 2017/9/4.
 */
public class BaseResultSign {
    private String id;                                //签到id
    private String employeeId;                        //员工id
    private String signStatus;                           //签到状态:1正常;2:迟到;3:早退
    private String employeeSerialId;                  //员工工号
    private String employeeName;                      //员工姓名
    private String depName ;                          //部门名称
    private String positionName;                      //职位名称
    private String storeId;                           //门店id
    private String weekNum;                           //星期:1:周日;2:周一;3:周二;4:周三;5:周四;6:周五;7:周六
    private String signTime;                          //签到时间
    private String queryTime;                         //查询年月
    private String signType;                          //签到类型

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(String queryTime) {
        this.queryTime = queryTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(String signStatus) {
        this.signStatus = signStatus;
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

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(String weekNum) {
        this.weekNum = weekNum;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }
}
