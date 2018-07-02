package com.dodsport.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Administrator on 2017/7/24.
 *
 * 权限管理
 */

public class AuthorityApplyForUtils {


    private static final int ACCESS_COARSE_LOCATION_REQUEST_CODE = 1;

    public static boolean getAAF(Context context){

        boolean mAuthority =false;

        /*获取当前系统的android版本号*/
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion >= 22) {
            //判断没有没申请定位权限
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                                android.Manifest.permission.ACCESS_FINE_LOCATION},
                        ACCESS_COARSE_LOCATION_REQUEST_CODE);
                mAuthority = true;
            } else {
                //已经申请过权限了
                mAuthority = true;
            }

        } else {
            //sdk小于23不用动态申请权限
            mAuthority = false;
        }

        return mAuthority;
    }
}
