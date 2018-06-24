package com.dod.sport.domain.po.Member;

/**
 * 会员卡门店关联
 * Created by Administrator on 2017/9/13.
 */
public class Memcardstorerelation {
    private String id;
    private String membcardId;    //会员卡id
    private String storeId;       //门店id

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

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}
