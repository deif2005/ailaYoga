package com.dod.sport.domain.po;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Administrator
 * \* Date: 2017/9/17
 *   考勤工资
 * \
 */
public class SalarySetting {

    private String id;
    private String storeId;                                        //门店ID
    private String basePay;                                        //无责底薪
    private String positionPay;                                   //岗位工资
    private String seniorityPay;                                  //工龄工资
    private String tendancePay;                                   //考勤工资
    private String housingSubsidies;                              //房贴
    private String mealAllowance;                                 //餐补
    private String phoneSubsidy;                                  //话补
    private String travelAllowance;                               //交通补助
    private String attendanceReward;                              //全勤奖
    private String managementAllowance;                           //管理津贴
    private String other;                                         //其他
    private String overtimePay;                                    //加班费
    private String weekendOvertimepay;                            //周末加班费
    private String holidayOvertimepay;                             //节假日加班
    private String achievementPercentage;                         //业绩提成%
    private String achievementId ;                                //业绩提成额度比例Id
    private String juniorCoach;                                   //初级教练
    private String intermediateCoach;                             //中级教练
    private String seniorCoach;                                   //高级教练
    private String concurrentPost;                                //兼职教练
    private String privateConcurrentPost;                         //兼职私教提成
    private String privateJuniorCoach;                            //私教初级教练
    private String privateIntermediatePoach;                      //私教中级教练
    private String privateSeniorCoach;                             //私教高级教练
    private String ocialSecurity;                                 //代扣社保
    private String accumulationFund;                               //代扣公积金
    private String personalTax ;                                  //个人所得税

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getBasePay() {
        return basePay;
    }

    public void setBasePay(String basePay) {
        this.basePay = basePay;
    }

    public String getPositionPay() {
        return positionPay;
    }

    public void setPositionPay(String positionPay) {
        this.positionPay = positionPay;
    }

    public String getSeniorityPay() {
        return seniorityPay;
    }

    public void setSeniorityPay(String seniorityPay) {
        this.seniorityPay = seniorityPay;
    }

    public String getTendancePay() {
        return tendancePay;
    }

    public void setTendancePay(String tendancePay) {
        this.tendancePay = tendancePay;
    }

    public String getHousingSubsidies() {
        return housingSubsidies;
    }

    public void setHousingSubsidies(String housingSubsidies) {
        this.housingSubsidies = housingSubsidies;
    }

    public String getMealAllowance() {
        return mealAllowance;
    }

    public void setMealAllowance(String mealAllowance) {
        this.mealAllowance = mealAllowance;
    }

    public String getPhoneSubsidy() {
        return phoneSubsidy;
    }

    public void setPhoneSubsidy(String phoneSubsidy) {
        this.phoneSubsidy = phoneSubsidy;
    }

    public String getTravelAllowance() {
        return travelAllowance;
    }

    public void setTravelAllowance(String travelAllowance) {
        this.travelAllowance = travelAllowance;
    }

    public String getAttendanceReward() {
        return attendanceReward;
    }

    public void setAttendanceReward(String attendanceReward) {
        this.attendanceReward = attendanceReward;
    }

    public String getManagementAllowance() {
        return managementAllowance;
    }

    public void setManagementAllowance(String managementAllowance) {
        this.managementAllowance = managementAllowance;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getOvertimePay() {
        return overtimePay;
    }

    public void setOvertimePay(String overtimePay) {
        this.overtimePay = overtimePay;
    }

    public String getWeekendOvertimepay() {
        return weekendOvertimepay;
    }

    public void setWeekendOvertimepay(String weekendOvertimepay) {
        this.weekendOvertimepay = weekendOvertimepay;
    }

    public String getHolidayOvertimepay() {
        return holidayOvertimepay;
    }

    public void setHolidayOvertimepay(String holidayOvertimepay) {
        this.holidayOvertimepay = holidayOvertimepay;
    }

    public String getAchievementPercentage() {
        return achievementPercentage;
    }

    public void setAchievementPercentage(String achievementPercentage) {
        this.achievementPercentage = achievementPercentage;
    }

    public String getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(String achievementId) {
        this.achievementId = achievementId;
    }

    public String getJuniorCoach() {
        return juniorCoach;
    }

    public void setJuniorCoach(String juniorCoach) {
        this.juniorCoach = juniorCoach;
    }

    public String getIntermediateCoach() {
        return intermediateCoach;
    }

    public void setIntermediateCoach(String intermediateCoach) {
        this.intermediateCoach = intermediateCoach;
    }

    public String getSeniorCoach() {
        return seniorCoach;
    }

    public void setSeniorCoach(String seniorCoach) {
        this.seniorCoach = seniorCoach;
    }

    public String getConcurrentPost() {
        return concurrentPost;
    }

    public void setConcurrentPost(String concurrentPost) {
        this.concurrentPost = concurrentPost;
    }

    public String getPrivateConcurrentPost() {
        return privateConcurrentPost;
    }

    public void setPrivateConcurrentPost(String privateConcurrentPost) {
        this.privateConcurrentPost = privateConcurrentPost;
    }

    public String getPrivateJuniorCoach() {
        return privateJuniorCoach;
    }

    public void setPrivateJuniorCoach(String privateJuniorCoach) {
        this.privateJuniorCoach = privateJuniorCoach;
    }

    public String getPrivateIntermediatePoach() {
        return privateIntermediatePoach;
    }

    public void setPrivateIntermediatePoach(String privateIntermediatePoach) {
        this.privateIntermediatePoach = privateIntermediatePoach;
    }

    public String getPrivateSeniorCoach() {
        return privateSeniorCoach;
    }

    public void setPrivateSeniorCoach(String privateSeniorCoach) {
        this.privateSeniorCoach = privateSeniorCoach;
    }

    public String getOcialSecurity() {
        return ocialSecurity;
    }

    public void setOcialSecurity(String ocialSecurity) {
        this.ocialSecurity = ocialSecurity;
    }

    public String getAccumulationFund() {
        return accumulationFund;
    }

    public void setAccumulationFund(String accumulationFund) {
        this.accumulationFund = accumulationFund;
    }

    public String getPersonalTax() {
        return personalTax;
    }

    public void setPersonalTax(String personalTax) {
        this.personalTax = personalTax;
    }
}