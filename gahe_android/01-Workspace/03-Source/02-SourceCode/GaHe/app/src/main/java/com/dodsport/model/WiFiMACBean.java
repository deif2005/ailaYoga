package com.dodsport.model;

/**
 * Created by Administrator on 2017/7/24.
 */

public class WiFiMACBean {

    private String SSID;    //WIFI名称
    private String BSSID;   //WIFIMAC地址
    //定位的经纬度
    private double latitude;
    private double longitude;

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public String getBSSID() {
        return BSSID;
    }

    public void setBSSID(String BSSID) {
        this.BSSID = BSSID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public WiFiMACBean() {
    }

    @Override
    public String toString() {
        return "WiFiMACBean{" +
                "SSID='" + SSID + '\'' +
                ", BSSID='" + BSSID + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }


}
