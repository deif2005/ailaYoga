package com.dod.sport.util;


import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 根据地址转换成经纬度
 * Created by Administrator on 2017/8/9.
 */
public class AddressUtils {
    /**
     * 返回输入地址的经纬度坐标 key lng(经度),lat(纬度)
     */
    public static Map<String, String> getGeocoderLatitude(String address) {
        BufferedReader in = null;

        //HOohirfjDdMOZR9BZLR2qHUXKc4IaWGj
        //
        try {
            Map paramsMap = new LinkedHashMap<String, String>();
            paramsMap.put("address", address);
            paramsMap.put("output", "json");
            paramsMap.put("ak", "q9pFTadKwXvqkNXSpfjzdyILeL0VQoGM");
            String quest = SnCal.toQueryString(paramsMap);
            //72bef655dcaacde272eaadc80859dd32
            String sn=GetLatitude.result(paramsMap);
            URL tirc = new URL(
                    "http://api.map.baidu.com/geocoder/v2/?" + quest + "&sn=" + GetLatitude.result(paramsMap));
            in = new BufferedReader(new InputStreamReader(tirc.openStream(), "UTF-8"));
            String res;
            StringBuilder sb = new StringBuilder("");
            while ((res = in.readLine()) != null) {
                sb.append(res.trim());
            }
            String str = sb.toString();
            Map<String, String> map = null;
            if (StringUtils.isNotEmpty(str)) {
                int lngStart = str.indexOf("lng\":");
                int lngEnd = str.indexOf(",\"lat");
                int latEnd = str.indexOf("},\"precise");
                if (lngStart > 0 && lngEnd > 0 && latEnd > 0) {
                    String lng = str.substring(lngStart + 5, lngEnd);
                    String lat = str.substring(lngEnd + 7, latEnd);
                    map = new HashMap<String, String>();
                    map.put("lng", lng);
                    map.put("lat", lat);
                    return map;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String args[]) {
        try {
            //   Map<String, String> json = AddressUtils.getGeocoderLatitude("\n" + "深圳龙华汽车站");
            Map<String, String> json = AddressUtils.getGeocoderLatitude("深圳龙华汽车站");
            System.out.println("lng : " + json.get("lng"));
            System.out.println("lat : " + json.get("lat"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Map<String,String> getlnglat(String paht){
        Map<String, String> json = AddressUtils.getGeocoderLatitude(paht);
        return json;
    }
    private static final double EARTH_RADIUS = 6378137;
    private static double rad(double d)
    {
         return d * Math.PI / 180.0;
     }
     /**
         * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米
         * @param lng1
         * @param lat1
         * @param lng2
        * @param lat2
          * @return
         */
       public static double GetDistance(double lng1, double lat1, double lng2, double lat2)
       {
           double radLat1 = rad(lat1);
           double radLat2 = rad(lat2);
           double a = radLat1 - radLat2;
           double b = rad(lng1) - rad(lng2);
           double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
           s = s * EARTH_RADIUS;
           s = Math.round(s * 10000) / 10000;
          return s;
       }
}