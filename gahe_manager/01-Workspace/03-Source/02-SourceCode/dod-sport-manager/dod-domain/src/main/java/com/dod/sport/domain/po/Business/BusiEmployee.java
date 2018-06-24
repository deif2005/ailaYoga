package com.dod.sport.domain.po.Business;

import com.dod.sport.domain.po.Base.BaseBusinessInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/8/17.
 */
public class BusiEmployee extends BaseEmployee {

    private String businessName;    //商家名称
    private String businessAddress; //商家地址
    private String businessId;      //商家id
    private String storeId;         //门店编号
    private String storeName;       //门店名称
    private String storeAddress;    //门店地址
    private String lasttimes;        //迟到次数
    private String leavetimes;       //早退次数
    private String failurePunch;     //未打卡次数
    private List<BaseBusinessInfo> businessList; //商家基本信息

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getLasttimes() {
        return lasttimes;
    }

    public void setLasttimes(String lasttimes) {
        this.lasttimes = lasttimes;
    }

    public String getLeavetimes() {
        return leavetimes;
    }

    public void setLeavetimes(String leavetimes) {
        this.leavetimes = leavetimes;
    }

    public String getFailurePunch() {
        return failurePunch;
    }

    public void setFailurePunch(String failurePunch) {
        this.failurePunch = failurePunch;
    }

    public List<BaseBusinessInfo> getBusinessList() {
        return businessList;
    }

    public void setBusinessList(List<BaseBusinessInfo> businessList) {
        this.businessList = businessList;
    }
}
