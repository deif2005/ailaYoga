package com.dod.sport.domain.po;

import java.io.Serializable;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Administrator
 * \* Date: 2017/9/15
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class PersonalIncome implements Serializable {

    private static final long serialVersionUID = -201604831622080982L;

    private String storeId;
    private String employeeId;
    private String basePay;          //底薪
    private String commission;       //提成
    private String courseCommission; //课程提成
    private String allowance;        //津贴
    private String incomeTime;       //月份
    private String total;             //合计
    private String bonus;             //奖金
    private String achievements;     //绩效
    private String receiveDividents; //分红
    private String fine;              //罚款

    public String getCourseCommission() {
        return courseCommission;
    }

    public void setCourseCommission(String courseCommission) {
        this.courseCommission = courseCommission;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getAllowance() {
        return allowance;
    }

    public void setAllowance(String allowance) {
        this.allowance = allowance;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getBasePay() {
        return basePay;
    }

    public void setBasePay(String basePay) {
        this.basePay = basePay;
    }

    public String getIncomeTime() {
        return incomeTime;
    }

    public void setIncomeTime(String incomeTime) {
        this.incomeTime = incomeTime;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }

    public String getReceiveDividents() {
        return receiveDividents;
    }

    public void setReceiveDividents(String receiveDividents) {
        this.receiveDividents = receiveDividents;
    }

    public String getFine() {
        return fine;
    }

    public void setFine(String fine) {
        this.fine = fine;
    }
}