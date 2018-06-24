package com.dod.sport.domain.po.Base;

import java.io.Serializable;

/**
 * wifi
 * Created by Administrator on 2017/9/19.
 */
public class StoreWifi implements Serializable {
    private static final long serialVersionUID = -3371500164401273447L;
    private String id ;
    private String storeId;
    private String wifiId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getWifiId() {
        return wifiId;
    }

    public void setWifiId(String wifiId) {
        this.wifiId = wifiId;
    }
}
