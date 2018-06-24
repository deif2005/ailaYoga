package com.dod.sport.domain.po;

/**
 * Created by Administrator on 2017/11/10.
 * 订单pojo
 */
public class Order {

    private String  id ;
    private String orderSerialId ;     //订单编号(201711091806000000001)
    private String userId;             //用户id
    private String totalPrice ;        //商品总价
    private String orderStatus;        //订单状态:1未付款,2已付款,3取消交易
    private String memberId;           //会员id
    private String storeId;            //门店id
    private String createTime;         //创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderSerialId() {
        return orderSerialId;
    }

    public void setOrderSerialId(String orderSerialId) {
        this.orderSerialId = orderSerialId;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }


    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}
