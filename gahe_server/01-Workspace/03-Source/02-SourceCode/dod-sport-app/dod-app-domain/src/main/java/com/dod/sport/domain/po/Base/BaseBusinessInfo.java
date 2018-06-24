package com.dod.sport.domain.po.Base;

import com.dod.sport.domain.po.Base.StoreInfo;

import java.util.List;

/**
 *商家基础信息
 * Created by Administrator on 2017/8/17.
 */
public class BaseBusinessInfo  {
    private String id;
    private String businessSerialId;    //商家编号
    private String businessName;       //商家名
    private List<StoreInfo>storeInfoList;

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public List<StoreInfo> getStoreInfoList() {
        return storeInfoList;
    }

    public void setStoreInfoList(List<StoreInfo> storeInfoList) {
        this.storeInfoList = storeInfoList;
    }

    public String getBusinessSerialId() {
        return businessSerialId;
    }

    public void setBusinessSerialId(String businessSerialId) {
        this.businessSerialId = businessSerialId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

}
