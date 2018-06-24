package com.dod.sport.domain.po;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/27.
 * 消费记录pojo
 */
public class ExpenseRecord implements Serializable {
    private static final long serialVersionUID = -3454700194538801535L;

    private String id;
    private String userId;              //消费者id
    private String expenseClassify;     //'1:对商家;2:对平台
    private String orderId;             //订单id
    private String payType;             //支付方式:1.零钱支付;2支付宝支付;3微信支付;4银行卡支付;5信用卡支付',
    private String payMoney;            //实际支付金额,
    private String payTime;             //支付时间,
    private String remark;               //备注,
    private String createTime;          //创建时间,

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
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
