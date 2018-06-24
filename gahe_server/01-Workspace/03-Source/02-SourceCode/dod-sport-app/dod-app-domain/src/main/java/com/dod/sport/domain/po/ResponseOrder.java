package com.dod.sport.domain.po;

/**
 * Created by Administrator on 2017/11/8.
 * 返回前端的订单pojo
 */
public class ResponseOrder {
    private String id;                       //变更记录id
    private String membercardRelationId;     //会员卡关系id
    private String times;                    //充值次数
    private String months;                   //充值月数
    private String nominalAmount;            //充值金额
    private String storeName;                //门店名称
    private String membcardName;             //会员卡名称
    private String membcardType;             //会员卡类型 1:次卡;2期限卡
    private String amountMoney;              //商品总金额
    private String createTime;               //订单提交时间
    private String orderSerialId;            //订单单号
    private String modifyTime;               //会员卡变更时间
    private String modifyType;               //变更类型:;3换卡;7开卡;9:请假10:充值
    private String newcardId;                //开卡卡种类id /换卡新卡id
    private String oldcardId;                //换卡旧卡id
    private String newcardName;              //开卡卡种类名称 /换卡新卡名称
    private String oldcardName;              //旧卡卡种类名称
    private String residueTimes;             //剩余次数/天数
    private String discountPrice;            //换卡时的折算价
    private String priceSpread;              //换卡时补的差价
    private String memberId;                 //会员id
    private String storeId;                  //门店id
    private String remark;                   //开卡备注
    private String validityType;             //次卡是否开启有效期:1:是;2否
    private String totalPrice;               //付款总金额
    private String orderStatus;              //订单状态:(1未付款,2已付款,3取消交易)
    private String saleType;                 //销售类型1:商品;2:店内服务;3:增值服务,9:其它
    private String userId;                   //用户id
    private String expenseSerialId;          //消费编号
    private String payTime;                  //支付时间
    private String payMode;                  //支付方式
    private String startTime;                //请假开始时间
    private String duration;                 //请假时长
    private String endTime;                  //请假结束时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMembercardRelationId() {
        return membercardRelationId;
    }

    public void setMembercardRelationId(String membercardRelationId) {
        this.membercardRelationId = membercardRelationId;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public String getNominalAmount() {
        return nominalAmount;
    }

    public void setNominalAmount(String nominalAmount) {
        this.nominalAmount = nominalAmount;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getMembcardName() {
        return membcardName;
    }

    public void setMembcardName(String membcardName) {
        this.membcardName = membcardName;
    }

    public String getMembcardType() {
        return membcardType;
    }

    public void setMembcardType(String membcardType) {
        this.membcardType = membcardType;
    }

    public String getAmountMoney() {
        return amountMoney;
    }

    public void setAmountMoney(String amountMoney) {
        this.amountMoney = amountMoney;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOrderSerialId() {
        return orderSerialId;
    }

    public void setOrderSerialId(String orderSerialId) {
        this.orderSerialId = orderSerialId;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyType() {
        return modifyType;
    }

    public void setModifyType(String modifyType) {
        this.modifyType = modifyType;
    }

    public String getNewcardId() {
        return newcardId;
    }

    public void setNewcardId(String newcardId) {
        this.newcardId = newcardId;
    }

    public String getOldcardId() {
        return oldcardId;
    }

    public void setOldcardId(String oldcardId) {
        this.oldcardId = oldcardId;
    }

    public String getNewcardName() {
        return newcardName;
    }

    public void setNewcardName(String newcardName) {
        this.newcardName = newcardName;
    }

    public String getOldcardName() {
        return oldcardName;
    }

    public void setOldcardName(String oldcardName) {
        this.oldcardName = oldcardName;
    }

    public String getResidueTimes() {
        return residueTimes;
    }

    public void setResidueTimes(String residueTimes) {
        this.residueTimes = residueTimes;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getPriceSpread() {
        return priceSpread;
    }

    public void setPriceSpread(String priceSpread) {
        this.priceSpread = priceSpread;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getValidityType() {
        return validityType;
    }

    public void setValidityType(String validityType) {
        this.validityType = validityType;
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

    public String getSaleType() {
        return saleType;
    }

    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getExpenseSerialId() {
        return expenseSerialId;
    }

    public void setExpenseSerialId(String expenseSerialId) {
        this.expenseSerialId = expenseSerialId;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
