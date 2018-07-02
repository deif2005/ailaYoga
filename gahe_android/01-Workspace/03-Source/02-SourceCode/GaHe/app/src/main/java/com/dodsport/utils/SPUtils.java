package com.dodsport.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.dodsport.model.UserDataBean;
import com.dodsport.model.WiFiMACBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 *
 * 共享参数管理类
 */

public class SPUtils {


    /**
     * 保存商家周围的WIFI集合
     *
     * */
    public static void setWiFiMACBeanList(Context context, List<WiFiMACBean> mWiFiMACBean){

        // 真正调用的时候
        //存储操作
        SharedPreferences sp = context.getSharedPreferences("WiFiMACBean",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        try {

            //将list集合转成字符串
            String listStr = SceneListStringUtils.SceneListString(mWiFiMACBean);

            //存储
            edit.putString("WiFiMACBeanListStr", listStr);

            edit.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    /**
     * 读取商家周围的WIFI集合
     *
     * */
    public static List<WiFiMACBean> getWiFiMACBeanList(Context context){
        List<WiFiMACBean> mWiFiMACBeanList = new ArrayList<>();

        //取值操作
        SharedPreferences sp = context.getSharedPreferences("WiFiMACBean",Context.MODE_PRIVATE);

        String liststr = sp.getString("WiFiMACBeanListStr", "");

        if (!TextUtils.isEmpty(liststr)) {
            try {
                mWiFiMACBeanList = SceneListStringUtils.StringSceneList(liststr);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return mWiFiMACBeanList;
    }


    /**
     * 保存签到的经纬度和wifimac地址
     *
     * */
    public static void setWiFiMACBean(Context context,WiFiMACBean wiFiMACBean) {

        SharedPreferences.Editor editor = context.getSharedPreferences("wiFiMACBean", Context.MODE_PRIVATE).edit();

        editor.putString("ssid",wiFiMACBean.getSSID());
        editor.putString("bssid",wiFiMACBean.getBSSID());
//        editor.putString("latitude",wiFiMACBean.getLatitude()+"");
//        editor.putString("longitude",wiFiMACBean.getLongitude()+"");

        editor.commit();
    }

    /**
     * 获取保存的经纬度和wifiMAC地址
     * */
    public static WiFiMACBean getWiFiMACBean(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("wiFiMACBean", Context.MODE_PRIVATE);

        String ssid = preferences.getString("ssid", "");
        String bssid = preferences.getString("bssid", "");
        String latitude = preferences.getString("latitude","");
        String longitude = preferences.getString("longitude","");

        WiFiMACBean wiFiMACBean = new WiFiMACBean();

        wiFiMACBean.setSSID(ssid);
        wiFiMACBean.setBSSID(bssid);
//        double latitudes = Double.valueOf(latitude+"");
//        wiFiMACBean.setLatitude(latitudes);
//        double longitudes = Double.valueOf(longitude+"");
//        wiFiMACBean.setLongitude(longitudes);

        return wiFiMACBean;
    }



    /**-----------------------------------用户Token-------------------------------------------*/

    /**
     * 保存用户Token
     * @param context
     * @param token
     */
    public static void saveToken(Context context,String token)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences("token", Context.MODE_PRIVATE).edit();
        editor.putString("token",token);
        editor.commit();

    }

    /**
     * 得到用户Token
     * @param context
     */
    public static String getToken(Context context)
    {
        SharedPreferences editor = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        return editor.getString("token","");

    }




    /**
     * 工作台（个人资料查询）
     *
     * */
    public static void setUserDataBean(Context context,UserDataBean datas){

        SharedPreferences.Editor editor = context.getSharedPreferences("userdata", Context.MODE_PRIVATE).edit();
        UserDataBean.DatasBean.ResponseEmployeeBean userDataBean = datas.getDatas().getResponseEmployee();

        editor.putString("businessAddress", userDataBean.getBusinessAddress());
        editor.putString("businessId", userDataBean.getBusinessId());
        editor.putString("businessName", userDataBean.getBusinessName());
        editor.putString("depId", userDataBean.getDepId());
        editor.putString("depName", userDataBean.getDepName());

        editor.putString("empRela", userDataBean.getEmpRela());
        editor.putString("empStatus", userDataBean.getEmpStatus());
        editor.putString("empType", userDataBean.getEmpType());
        editor.putString("employeeSerialId", userDataBean.getEmployeeSerialId());
        editor.putString("employeeName", userDataBean.getEmployeeName());

        editor.putString("id", userDataBean.getId());
        editor.putString("idCard", userDataBean.getIdCard());
        editor.putString("phoneNum", userDataBean.getPhoneNum());
        editor.putString("positionId", userDataBean.getPositionId());
        editor.putString("positionName",userDataBean.getPositionName());

        editor.putString("sex",userDataBean.getSex());
        editor.putString("status",userDataBean.getStatus());
        editor.putString("storeAddress",userDataBean.getStoreAddress());
        editor.putString("storeId",userDataBean.getStoreId());
        editor.putString("storeName", userDataBean.getStoreName());
        editor.putString("entryTime",userDataBean.getEntryDate());
        editor.putString("workDuration",userDataBean.getWorkDuration());

        editor.commit();




    }

    /**工作台（个人资料）*/
    public static UserDataBean.DatasBean.ResponseEmployeeBean getUserDataBean(Context context){
        SharedPreferences preferences = context.getSharedPreferences("userdata", Context.MODE_PRIVATE);


        String businessAddress = preferences.getString("businessAddress", "");
        String businessId = preferences.getString("businessId", "");
        String businessName = preferences.getString("businessName", "");
        String depId = preferences.getString("depId", "");
        String depName = preferences.getString("depName", "");

        String empRela = preferences.getString("empRela", "");
        String empStatus = preferences.getString("empStatus", "");
        String empType = preferences.getString("empType", "");
        String employeeSerialId = preferences.getString("employeeSerialId", "");
        String employeeName = preferences.getString("employeeName", "");

        String id = preferences.getString("id", "");
        String idCard = preferences.getString("idCard", "");
        String phoneNum = preferences.getString("phoneNum", "");
        String positionId = preferences.getString("positionId", "");
        String positionName = preferences.getString("positionName", "");


        String sex = preferences.getString("sex", "");
        String storeAddress = preferences.getString("storeAddress", "");
        String storeId = preferences.getString("storeId", "");
        String storeName = preferences.getString("storeName", "");
        String status = preferences.getString("Status", "");
        String entryTime = preferences.getString("entryTime","");
        String workDuration = preferences.getString("workDuration","");


        UserDataBean.DatasBean.ResponseEmployeeBean userDataBean = new UserDataBean.DatasBean.ResponseEmployeeBean();


        userDataBean.setBusinessAddress(businessAddress);
        userDataBean.setBusinessId(businessId);
        userDataBean.setBusinessName(businessName);
        userDataBean.setDepId(depId);
        userDataBean.setDepName(depName);

        userDataBean.setEmpRela(empRela);
        userDataBean.setEmpStatus(empStatus);
        userDataBean.setEmpType(empType);
        userDataBean.setEmployeeSerialId(employeeSerialId);
        userDataBean.setEmployeeName(employeeName);

        userDataBean.setId(id);
        userDataBean.setIdCard(idCard);
        userDataBean.setPhoneNum(phoneNum);
        userDataBean.setPositionId(positionId);
        userDataBean.setPositionName(positionName);

        userDataBean.setSex(sex);
        userDataBean.setStatus(status);
        userDataBean.setStoreAddress(storeAddress);
        userDataBean.setStoreId(storeId);
        userDataBean.setStoreName(storeName);
        userDataBean.setEntryDate(entryTime);
        userDataBean.setWorkDuration(workDuration);

        //Log.i("******", "共享参数-----》"+userDataBean.toString()+"");
        return userDataBean;
    }




    /**退出登录时 清除用户信息*/
    public static void deleteUserBean(Context context){


        //清除个人资料查询数据
        SharedPreferences.Editor preferen = context.getSharedPreferences("userdata", Context.MODE_PRIVATE).edit();
        preferen.clear();
        preferen.commit();


        //清除Token
//        SharedPreferences.Editor preferens = context.getSharedPreferences("token",context.MODE_PRIVATE).edit();
//        preferens.clear();
//        preferens.commit();
    }


}
