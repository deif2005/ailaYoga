package com.dod.sport.domain.po.Bill;



/**
 * 入职单
 * Created by Administrator on 2017/9/6.
 */
public class EntryBill extends BaseBill {

    private String employeeSerialId;
    //private String phoneNum;
    private String idCard;
    private String empRela;
    private String depId;
    private String positionId;
    private String phoneNum;           //员工电话


    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    public String getPhoneNum() {
        return phoneNum;
    }



    public String getEmployeeSerialId() {
        return employeeSerialId;
    }

    public void setEmployeeSerialId(String employeeSerialId) {
        this.employeeSerialId = employeeSerialId;
    }

   /* public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }*/

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getEmpRela() {
        return empRela;
    }

    public void setEmpRela(String empRela) {
        this.empRela = empRela;
    }

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }
}
