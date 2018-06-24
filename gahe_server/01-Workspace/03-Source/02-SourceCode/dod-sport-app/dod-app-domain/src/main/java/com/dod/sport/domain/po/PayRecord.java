package com.dod.sport.domain.po;

/**
 * \* Description:
 * \  支付记录
 */
public class PayRecord {

    private String id;
    private String expenseSerialId;          //消费编号
    private String userId;                   //消费者id
    private String expenseClassify;          //'1:对商家;2:对平台'
    private String orderId;                  //订单id
    private String payMoney;                 //实际支付金额
    private String payMode;                 //支付方式(微信支付：1 ，支付宝支付：2)
    private String payTtime;                 //支付时间
    private String remark;                    //备注
    private String createTime;                //创建时间

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExpenseSerialId() {
        return expenseSerialId;
    }

    public void setExpenseSerialId(String expenseSerialId) {
        this.expenseSerialId = expenseSerialId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getExpenseClassify() {
        return expenseClassify;
    }

    public void setExpenseClassify(String expenseClassify) {
        this.expenseClassify = expenseClassify;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getPayTtime() {
        return payTtime;
    }

    public void setPayTtime(String payTtime) {
        this.payTtime = payTtime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}