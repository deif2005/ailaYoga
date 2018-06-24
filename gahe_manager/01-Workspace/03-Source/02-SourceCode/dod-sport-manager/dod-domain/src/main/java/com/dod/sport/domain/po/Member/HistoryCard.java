package com.dod.sport.domain.po.Member;

import java.io.Serializable;

/**
 * Created by defi on 2017-11-07.
 * 新增历史卡数据
 */
public class HistoryCard implements Serializable{

    private String id;            //会员卡关系id
    private String membcardId;    //会员卡编号
    private String membcardType;  //卡类型
    private String validityType;  //是否开启有效期
    private String validityTime;  //有效期时间
    private String storeId;       //门店id
    private String times;         //剩余次数
    private String cardStatus;    //状态:1.正常,2.停卡,3.删除,4.请假

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMembcardId() {
        return membcardId;
    }

    public void setMembcardId(String membcardId) {
        this.membcardId = membcardId;
    }

    public String getMembcardType() {
        return membcardType;
    }

    public void setMembcardType(String membcardType) {
        this.membcardType = membcardType;
    }

    public String getValidityType() {
        return validityType;
    }

    public void setValidityType(String validityType) {
        this.validityType = validityType;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getValidityTime() {
        return validityTime;
    }

    public void setValidityTime(String validityTime) {
        this.validityTime = validityTime;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }
}
