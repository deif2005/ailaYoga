package com.dod.sport.domain.po;

/**
 * Created by Administrator on 2017/10/30.
 */
public class ResponseExpenseRecord extends ExpenseRecord {

    private String storeName; //门店名称
    private String nickName;  //用户昵称
    private String theYear;   //支付年
    private String theMonth;  //支付月
    private String theDay;    //支付日
    private String theTime;   //支付时间
    private String payDateTimeStr; //格式2017年09月14日15:00
    private String payMonTimeStr;  // 09月14日15:00

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getTheYear() {
        return theYear;
    }

    public void setTheYear(String theYear) {
        this.theYear = theYear;
    }

    public String getTheMonth() {
        return theMonth;
    }

    public void setTheMonth(String theMonth) {
        this.theMonth = theMonth;
    }

    public String getTheDay() {
        return theDay;
    }

    public void setTheDay(String theDay) {
        this.theDay = theDay;
    }

    public String getTheTime() {
        return theTime;
    }

    public void setTheTime(String theTime) {
        this.theTime = theTime;
    }

    public String getPayDateTimeStr() {
        return payDateTimeStr;
    }

    public void setPayDateTimeStr(String payDateTimeStr) {
        this.payDateTimeStr = payDateTimeStr;
    }

    public String getPayMonTimeStr() {
        return payMonTimeStr;
    }

    public void setPayMonTimeStr(String payMonTimeStr) {
        this.payMonTimeStr = payMonTimeStr;
    }
}
