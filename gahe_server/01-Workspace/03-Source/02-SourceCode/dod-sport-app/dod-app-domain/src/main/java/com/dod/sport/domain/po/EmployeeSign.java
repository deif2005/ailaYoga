package com.dod.sport.domain.po;

import java.io.Serializable;

/**
 * 员工签到表
 * Created by Administrator on 2017/8/30.
 */
public class EmployeeSign implements Serializable {

    private static final long serialVersionUID = -7234296591615773710L;
    private String id;
    private String employeeId;         //员工id
    private String storeId;            //门店id
    private int signType;              //签到类型:1.签到,2.签退
    private String depId;              //部门id
    private String signTime;           //签到时间
    private int signStatus;            //签到状态1;正常,2:迟到;3早退
    private int overTime;              //超出时间分钟为单位
    private String signedAddr;        //签到位置

    public int getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(int signStatus) {
        this.signStatus = signStatus;
    }

    public String getSignedAddr() {
        return signedAddr;
    }

    public void setSignedAddr(String signedAddr) {
        this.signedAddr = signedAddr;
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

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public int getSignType() {
        return signType;
    }

    public void setSignType(int signType) {
        this.signType = signType;
    }

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public int getOverTime() {
        return overTime;
    }

    public void setOverTime(int overTime) {
        this.overTime = overTime;
    }
}
