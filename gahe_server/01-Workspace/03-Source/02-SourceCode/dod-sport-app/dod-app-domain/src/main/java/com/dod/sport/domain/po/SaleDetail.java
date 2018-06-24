package com.dod.sport.domain.po;

/**
 * Created by Administrator on 2017/11/10.
 * 订单详情pojo
 */
public class SaleDetail {

    private String id;
    private String orderId;           //订单id
    private String productId;         //商品id
    private String productPrice;      //商品单价
    private String procuctCount;      //商品数量
    private String discount;          //折扣
    private String amountMoney;       //金额
    private String saleType;          //销售类型：1:商品;2:店内服务;3:增值服务,9:其它
    private String createTime;        //创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProcuctCount() {
        return procuctCount;
    }

    public void setProcuctCount(String procuctCount) {
        this.procuctCount = procuctCount;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getAmountMoney() {
        return amountMoney;
    }

    public void setAmountMoney(String amountMoney) {
        this.amountMoney = amountMoney;
    }

    public String getSaleType() {
        return saleType;
    }

    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
