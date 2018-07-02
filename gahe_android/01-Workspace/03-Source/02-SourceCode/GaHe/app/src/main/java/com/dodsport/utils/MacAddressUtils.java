package com.dodsport.utils; /**
 * Created by Administrator on 2017/7/15.
 */

import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.dodsport.model.WiFiMACBean;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by cpxiao on 15/9/21.
 * 获取路由器MAC地址 工具类
 */
public class MacAddressUtils {

    List<ScanResult> mObjectList;
    /**
     * 获取手机的Mac地址，在Wifi未开启或者未连接的情况下也能获取手机Mac地址
     */
    public static String getMacAddress(Context context) {
        String macAddress = null;
        WifiInfo wifiInfo = getWifiInfo(context);
        if (wifiInfo != null) {
            macAddress = wifiInfo.getMacAddress();
        }
        return macAddress;
    }

    /**
     * 获取手机的Ip地址
     */
    public static String getIpAddress(Context context) {
        String IpAddress = null;
        WifiInfo wifiInfo = getWifiInfo(context);
        if (wifiInfo != null) {
            IpAddress = intToIpAddress(wifiInfo.getIpAddress());
        }
        return IpAddress;
    }

    /**
     * 获取WifiInfo
     */
    public static WifiInfo getWifiInfo(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = null;
        if (null != wifiManager) {
            info = wifiManager.getConnectionInfo();
        }
        return info;
    }


    public static long ipAddressToint(String ip) {
        String[] items = ip.split("\\.");
        return Long.valueOf(items[0]) << 24
                | Long.valueOf(items[1]) << 16
                | Long.valueOf(items[2]) << 8
                | Long.valueOf(items[3]);
    }

    public static String intToIpAddress(long ipInt) {
        StringBuffer sb = new StringBuffer();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }

    /**
     * 获取当前可连接Wifi列表
     */
    public static List<ScanResult> getAvailableNetworks(Context context) {
        List<ScanResult> list = new ArrayList<>();
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> wifiList = null;
        if (wifiManager != null) {
            wifiList = wifiManager.getScanResults();
//            WifiInfo info = wifiManager.getConnectionInfo();
//            if (wifiList !=null && info !=null){
//                for (int i = 0; i < wifiList.size(); i++) {
//                    ScanResult result = wifiList.get(i);
//                        list.add(result);
//                    if (info.getBSSID().equals(result.BSSID)) {
//                       // connectedWifiMacAddress = result.BSSID;
//
//                    }
//                }
//            }

            //String mIp = getLocalIpAddress();   //IP地址
            //String mMAC = getLocalMacAddress(mActivity); //手机的MAC地址
        }
        return wifiList;
    }

    /**
     * 获取已连接的Wifi路由器的Mac地址
     */
    public static String getConnectedWifiMacAddress(Context context) {
        String connectedWifiMacAddress = null;
        try {
            connectedWifiMacAddress = null;
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            List<ScanResult> wifiList;

            if (wifiManager != null) {
                wifiList = wifiManager.getScanResults();
                WifiInfo info = wifiManager.getConnectionInfo();
                if (wifiList != null && info != null) {
                    for (int i = 0; i < wifiList.size(); i++) {
                        ScanResult result = wifiList.get(i);
                        if (info.getBSSID().equals(result.BSSID)) {
                            connectedWifiMacAddress = result.BSSID;

                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connectedWifiMacAddress;
    }



    //获取MAC地址 和周围WiFi集合
    public static  List<WiFiMACBean> getWiFiMAC(Activity activity) {
        String ipAddress = MacAddressUtils.getIpAddress(activity);
        long l = MacAddressUtils.ipAddressToint(ipAddress);
        List<ScanResult> availableNetworks = MacAddressUtils.getAvailableNetworks(activity);

        if (availableNetworks.size() == 0 || availableNetworks == null){
            return null;
        }
        List<WiFiMACBean> mWiFiMACList = new ArrayList<>();
        for (ScanResult availableNetwork : availableNetworks) {
            WiFiMACBean mWiFiMAC = new WiFiMACBean();
            mWiFiMAC.setBSSID(availableNetwork.BSSID + "");
            mWiFiMAC.setSSID(availableNetwork.SSID + "");
            mWiFiMACList.add(mWiFiMAC);

        }
        return mWiFiMACList;

    }


    //获取IP
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            //Log.e("IP 地址为：", ex.toString());
        }
        return null;
    }

    //获取MAC
    public static String getLocalMacAddress(Activity activity) {
        WifiManager wifi = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

}