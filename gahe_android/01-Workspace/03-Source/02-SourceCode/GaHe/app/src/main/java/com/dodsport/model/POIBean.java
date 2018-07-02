package com.dodsport.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/23.
 * 搜索定位返回的对象
 */

public class POIBean implements Serializable{

    //Log.i(" ", "POI 的行政区划代码和名称=" + poiResult.getPois().get(i).getAdCode()+","+poiResult.getPois().get(i).getAdName());
    //Log.i("TAG_MAIN", "POI的所在商圈=" + poiResult.getPois().get(i).getBusinessArea());
    //Log.i("TAG_MAIN", "POI的城市编码与名称=" + poiResult.getPois().get(i).getCityCode()+","+poiResult.getPois().get(i).getCityName());
    //Log.i("TAG_MAIN", "POI 的经纬度=" + poiResult.getPois().get(i).getLatLonPoint());
    //Log.i("TAG_MAIN", "POI的地址=" + poiResult.getPois().get(i).getSnippet());
    //Log.i("TAG_MAIN", "POI的名称=" + poiResult.getPois().get(i).getTitle());

    private String AdName;
    private String BusinessArea;
    private String CityName;
    private double latitude;
    private double longitude;
    private String Snippet;
    private String itle;
    private String location;


    public String getAdName() {
        return AdName;
    }

    public void setAdName(String adName) {
        AdName = adName;
    }

    public String getBusinessArea() {
        return BusinessArea;
    }

    public void setBusinessArea(String businessArea) {
        BusinessArea = businessArea;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
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

    public String getSnippet() {
        return Snippet;
    }

    public void setSnippet(String snippet) {
        Snippet = snippet;
    }

    public String getItle() {
        return itle;
    }

    public void setItle(String itle) {
        this.itle = itle;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public POIBean() {
    }

    @Override
    public String toString() {
        return "POIBean{" +
                "AdName='" + AdName + '\'' +
                ", BusinessArea='" + BusinessArea + '\'' +
                ", CityName='" + CityName + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", Snippet='" + Snippet + '\'' +
                ", itle='" + itle + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
